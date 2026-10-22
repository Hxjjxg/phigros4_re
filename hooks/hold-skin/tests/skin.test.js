'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const vm = require('node:vm');
const C = require('../src/core');
const profile = require('../native-profile.json');

function fixture(controls = {}) {
    let next=1, decodes=0, failDecode=-1, missing=null, corrupt=null, rejectSetter=false;
    const objects=[], handles=new Map(), hooks=new Map(), intervals=new Set(), immediates=[], writes=[], nativeCalls=[], opened=[];
    class P {
        constructor(data={}, offset=0, base) { this.data=data; this.offset=offset; this.base=base||this; this.id=base?base.id:next++; }
        isNull() { return this.id===0; }
        equals(p) { return this.id===p.id && this.offset===p.offset; }
        toString() { return '0x'+(this.id*4096+this.offset).toString(16); }
        add(n) { return new P(this.data,this.offset+n,this.base); }
        writeByteArray(bytes) { this.data.bytes=Buffer.from(bytes); return this; }
        readS32() { return this.data.fields.type; }
    }
    class Code {
        constructor(name,offset=0) { this.name=name; this.offset=offset; }
        add(n) { return new Code(this.name,this.offset+n); }
        toString() { return this.name+(this.offset?'+'+this.offset:''); }
        readByteArray() {
            const hex=this.offset?profile.sites.find(s=>s.offset===this.offset).signature:profile.entrySignature;
            const bytes=Buffer.from(hex,'hex');
            if(controls.corruptSite===this.offset) bytes[0]^=1;
            return bytes;
        }
    }
    const nil=new P(); nil.id=0;
    const p=data=>{const v=new P({alive:true,fields:{},...data});objects.push(v);return v;};
    const array=items=>p({items});
    const sprite=name=>p({name});
    const normal=[sprite('head'),sprite('body'),sprite('shared-tail')];
    const multi=[sprite('multi-head'),sprite('multi-body'),normal[2]];
    const prefabArray=array(normal.slice());
    const line=p({fields:{HoldHL0:multi[0],HoldHL1:multi[1],chordSupport:true,notesAbove:array([]),notesBelow:array([])}});
    const dimensions=Object.fromEntries(C.slots.map(slot=>[slot,slot.startsWith('body_')?[20,512]:[400,120]]));
    Object.assign(dimensions,controls.dimensions||{});
    const retain=(o,flag)=>{assert.equal(flag,0,'Frida native bool requires integer 0');const h=next++;handles.set(h,o);return h;};
    const r={
        a:{gchandle_new:retain,gchandle_new_weakref:retain,gchandle_get_target:h=>handles.get(h)||nil,
            gchandle_free:h=>handles.delete(h),object_new:k=>p({klass:k}),
            array_new:(k,n)=>array(Array(n).fill(nil)),object_get_class:o=>({equals:k=>o.data.klass===k})},
        scope:fn=>fn(),pin:o=>o,game:n=>n,unity:n=>n,klass:(a,ns,n)=>n,
        method(k,name,argc,signature) {
            if(k==='JudgeLineControl'&&name==='Start')throw Error('Must not hook chart Start for preparation');
            if(k==='HoldControl'&&name==='NoteMove')throw Error('No per-frame hold hook');
            if(k==='Texture2D'&&name==='.ctor')assert.equal(Array.from(signature).join(','),'System.Int32,System.Int32,UnityEngine.TextureFormat,System.Boolean');
            return {k,name,argc,readPointer:()=>new Code(k+'.'+name)};
        },
        offset:(k,n)=>({noteImages:0x50,_holdSpriteRenderer:0xa0,HoldHL0:0x48}[n]||0),
        field:(o,k,n)=>o.data.fields[n]||nil,
        put:(o,k,n,v)=>{o.data.fields[n]=v;writes.push([o,n,v]);},
        item:(o,i)=>{assert.ok(i>=0&&i<o.data.items.length);return o.data.items[i];},
        setItem:(o,i,v)=>{assert.ok(i>=0&&i<o.data.items.length);o.data.items[i]=v;},
        int:n=>{assert.ok(Number.isInteger(n));return n;},floats:values=>p({values}),str:s=>s,bool:b=>b,number:n=>n,
        alive:o=>!o.isNull()&&o.data.alive,destroy:o=>{o.data.alive=false;},
        listItemRaw:(list,i)=>list.data.items[i],
        call(o,name,args=[]) {
            nativeCalls.push(name);
            if(name==='get_width')return o.data.width;
            if(name==='get_height')return o.data.height;
            if(name==='set_name'){o.data.name=args[0];return nil;}
            if(name==='set_sprite'){
                if(rejectSetter){rejectSetter=false;throw Error('setter failure');}
                o.data.current=args[0];return nil;
            }
            if(name==='get_sprite')return o.data.current;
            if(['get_rect','get_pivot','get_pixelsPerUnit','ReadPixels','Apply','get_Count','get_Item'].includes(name))
                throw Error('Unexpected expensive/obsolete call: '+name);
            return nil;
        },
        invoke(m,o,args=[]) {
            nativeCalls.push(m.name);
            if(m.name==='.ctor'){o.data.width=args[0];o.data.height=args[1];return nil;}
            if(m.name==='LoadImage'){
                const header=args[1].data.bytes;
                args[0].data.width=header.readUInt32BE(16);args[0].data.height=header.readUInt32BE(20);
                return decodes++!==failDecode;
            }
            if(m.name==='Create')return p({texture:args[0],geometry:{width:args[1].data.values[2],height:args[1].data.values[3],
                pivot:args[2].data.values,ppu:args[3].data.values[0]}});
            throw Error('Unexpected managed invocation: '+m.name);
        }
    };
    const context={PhiHoldCore:C,PhiHoldProfile:profile,PHI_HOLD_SKIN_OPTIONS:controls.options,createPhiHoldRuntime:()=>r,
        NULL:nil,rpc:{exports:{}},console:{log(){}},
        Process:{findModuleByName:()=>true,findRangeByAddress:()=>({protection:'r-x'})},
        File:class {
            constructor(path){this.path=path;opened.push(path);if(path===missing)throw Error('missing image');}
            readBytes(){
                if(this.path===corrupt)return Buffer.from('bad PNG');
                const config=C.config(controls.options),slot=C.slots.find(s=>this.path===config.directory+'/'+config.files[s]);
                assert.ok(slot,'Unexpected image path: '+this.path);
                const b=Buffer.alloc(33);Buffer.from([137,80,78,71,13,10,26,10]).copy(b);
                b.writeUInt32BE(13,8);b.write('IHDR',12);b.writeUInt32BE(dimensions[slot][0],16);b.writeUInt32BE(dimensions[slot][1],20);
                return b;
            }
            close(){}
        },
        Interceptor:{attach(address,cb){
            if(address.offset)assert.equal(typeof cb,'function','Interior PCs must use instruction probes');
            const key=String(address);hooks.set(key,cb);return {detach:()=>hooks.delete(key)};
        }},
        setInterval:fn=>{intervals.add(fn);return fn;},clearInterval:fn=>intervals.delete(fn),
        setImmediate:fn=>immediates.push(fn),setTimeout,clearTimeout};
    vm.runInNewContext(fs.readFileSync(require.resolve('../src/skin.js'),'utf8'),context);
    for(const fn of Array.from(intervals))fn();
    function flush(){while(immediates.length)immediates.shift()();}
    function menuFrame(){hooks.get('SongSelector.Update').onLeave();flush();}
    function gameFrame(){hooks.get('LevelInformation.Update').onLeave();flush();}
    function warm(){for(let i=0;i<12&&!['ready','failed'].includes(context.PhiHoldSkin.status().phase);i++)menuFrame();}
    function create({highlight=false,above=true,renderers=false,type=3,threadId=7,nested,foreignPair=false,wrongLine=false}={}){
        const entry=hooks.get('JudgeLineControl.CreateNote'),entrySp=p({}),ctx={threadId,context:{sp:entrySp}};
        const list=line.data.fields[above?'notesAbove':'notesBelow'];
        const index=list.data.items.length;list.data.items.push(p({fields:{type}}));
        entry.onEnter.call(ctx,[line,{toInt32:()=>index},{toInt32:()=>above?1:0}]);
        const originals=(highlight&&line.data.fields.chordSupport?multi:normal).slice();
        if(foreignPair){originals[0]=sprite('foreign-head');originals[1]=sprite('foreign-body');}
        const original=array(originals),hold=p({klass:'HoldControl',fields:{judgeLine:line,noteImages:original}});
        if(renderers)['_holdHeadSpriteRenderer','_holdSpriteRenderer','_holdEndSpriteRenderer1'].forEach((key,i)=>hold.data.fields[key]=p({current:originals[i]}));
        const sites=profile.sites.filter(s=>s.name.endsWith(above?'above':'below'));
        const cpu={sp:entrySp.add(-profile.stackFrameSize),x19:wrongLine?p({}):line,[sites[0].holdRegister]:hold};
        if(type===3&&highlight&&line.data.fields.chordSupport)
            hooks.get('JudgeLineControl.CreateNote+'+sites[0].offset).call({threadId,context:cpu});
        if(nested)nested();
        if(type===3)hooks.get('JudgeLineControl.CreateNote+'+sites[1].offset).call({threadId,context:cpu});
        hold.data.original=original;
        return hold;
    }
    return {api:context.PhiHoldSkin,hooks,handles,objects,writes,nativeCalls,opened,menuFrame,gameFrame,warm,create,line,normal,multi,prefabArray,
        decodes:()=>decodes,failDecode:n=>failDecode=n,missing:path=>missing=path,corrupt:path=>corrupt=path,rejectSetter:()=>rejectSetter=true};
}
const names=hold=>hold.data.fields.noteImages.data.items.map(s=>s.data.name);

test('one image per menu frame; chart callbacks never load/decode assets',()=>{
    const f=fixture();assert.equal(f.api.status().phase,'waiting-menu');
    f.create();f.gameFrame();assert.equal(f.opened.length,0);assert.equal(f.decodes(),0);
    f.menuFrame();assert.equal(f.opened.length,6);assert.equal(f.decodes(),0);
    for(let i=1;i<=6;i++){f.menuFrame();assert.equal(f.decodes(),i);}
    assert.equal(f.api.status().phase,'ready');assert.equal(f.api.status().prepared,6);
    assert.equal(f.api.status().spriteSets,2);
    const before=f.nativeCalls.length;f.create({highlight:true});
    assert.equal(f.decodes(),6);assert.equal(f.opened.length,6);
    assert.deepEqual(f.nativeCalls.slice(before),[]);
    assert.equal(f.hooks.has('JudgeLineControl.Start'),false);assert.equal(f.hooks.has('HoldControl.NoteMove'),false);
    assert.equal(f.hooks.get('JudgeLineControl.CreateNote').onLeave,undefined);
});
test('native decisions select all three sprites on both sides and preserve shared native sources',async()=>{
    const f=fixture();f.warm();
    const single=f.create(),multi=f.create({highlight:true,above:false,renderers:true});
    assert.deepEqual(names(single),['PhiHoldSkin/head_single','PhiHoldSkin/body_single','PhiHoldSkin/tail_single']);
    assert.deepEqual(names(multi),['PhiHoldSkin/head_multi','PhiHoldSkin/body_multi','PhiHoldSkin/tail_multi']);
    assert.equal(multi.data.fields._holdEndSpriteRenderer1.data.current.data.name,'PhiHoldSkin/tail_multi');
    assert.equal(f.prefabArray.data.items[2],f.normal[2]);assert.equal(f.line.data.fields.HoldHL0,f.multi[0]);
    const done=f.api.dispose();f.gameFrame();await done;
    assert.equal(single.data.fields.noteImages,single.data.original);
    assert.equal(multi.data.fields._holdEndSpriteRenderer1.data.current,f.normal[2]);
    assert.equal(f.handles.size,0);assert.equal(f.hooks.size,0);
});
test('native branch result is independent of head/body sprite identities',()=>{
    const f=fixture();f.warm();
    const hold=f.create({highlight:true,foreignPair:true});
    assert.equal(names(hold)[2],'PhiHoldSkin/tail_multi');assert.equal(f.api.status().classifier,'native-branch');
    assert.equal(f.api.status().skipped,0);
});
test('prepared immutable arrays are shared per variant with one array binding per hold',()=>{
    const f=fixture();f.warm();const first=f.create(),second=f.create();
    assert.equal(first.data.fields.noteImages,second.data.fields.noteImages);
    assert.equal(f.writes.filter(([o,n])=>(o===first||o===second)&&n==='noteImages').length,2);
    assert.equal(first.data.original.data.items[2],f.normal[2]);
});
test('art keeps natural aspect, thin body and native length basis without GPU resampling',()=>{
    const f=fixture();f.warm();const hold=f.create({highlight:true}),items=hold.data.fields.noteImages.data.items;
    for(let i=0;i<3;i++){
        const g=items[i].data.geometry,ref=profile.geometry[i+3];
        assert.equal(g.width/g.height,i===1?20/512:400/120);
        if(i===1){assert.equal(g.height/g.ppu,ref.height/ref.ppu);assert.ok(g.width/g.ppu<ref.width/ref.ppu);}
        else assert.ok(Math.abs(g.width/g.ppu-ref.width/ref.ppu)<1e-12);
        assert.equal(items[i].data.texture.data.width,g.width);assert.equal(items[i].data.texture.data.height,g.height);
    }
    assert.equal(f.nativeCalls.includes('Blit'),false);assert.equal(f.nativeCalls.includes('ReadPixels'),false);
});
test('nested creation on one thread retains each native decision',()=>{
    const f=fixture();f.warm();let inner;
    const outer=f.create({highlight:true,nested:()=>{inner=f.create();}});
    assert.equal(names(outer)[2],'PhiHoldSkin/tail_multi');assert.equal(names(inner)[2],'PhiHoldSkin/tail_single');
    assert.equal(f.api.status().activeCalls,0);
});
test('interleaved native calls on different threads cannot leak highlight decisions',()=>{
    const f=fixture();f.warm();let inner;
    const outer=f.create({nested:()=>{inner=f.create({highlight:true,threadId:11});}});
    assert.equal(names(outer)[2],'PhiHoldSkin/tail_single');assert.equal(names(inner)[2],'PhiHoldSkin/tail_multi');
    assert.equal(f.api.status().activeCalls,0);
});
test('highlight disabled and non-hold notes follow native execution',()=>{
    const f=fixture();f.warm();f.line.data.fields.chordSupport=false;
    assert.equal(names(f.create({highlight:true,above:false}))[2],'PhiHoldSkin/tail_single');
    const tap=f.create({type:1});assert.equal(tap.data.fields.noteImages,tap.data.original);
});
test('changed native instructions fail closed before installing any hooks',()=>{
    const f=fixture({corruptSite:profile.sites[0].offset});
    assert.equal(f.api.status().phase,'failed');assert.match(f.api.status().error,/Unsupported native hold code/);
    assert.equal(f.hooks.size,0);assert.equal(f.opened.length,0);assert.equal(f.handles.size,0);
});
test('native line/context mismatch leaves original sprites untouched',()=>{
    const f=fixture();f.warm();const hold=f.create({wrongLine:true});
    assert.equal(hold.data.fields.noteImages,hold.data.original);assert.equal(f.api.status().skipped,1);
    assert.match(f.api.status().error,/context mismatch/);
});
test('partial image decode rolls back assets and retry warms safely in menus',()=>{
    const f=fixture();f.failDecode(4);f.warm();
    assert.equal(f.api.status().phase,'failed');assert.equal(f.api.status().assets,0);assert.equal(f.handles.size,0);
    const untouched=f.create({highlight:true});assert.equal(untouched.data.fields.noteImages,untouched.data.original);
    f.failDecode(-1);f.api.retry();f.warm();assert.equal(f.api.status().phase,'ready');
    assert.equal(names(f.create({highlight:true}))[2],'PhiHoldSkin/tail_multi');
});
test('missing or corrupt file reports exact path and allocates no Unity assets',()=>{
    for(const corrupt of [false,true]){
        const f=fixture(),path='/local/data/tmp/tail_multi.png';
        if(corrupt)f.corrupt(path);else f.missing(path);
        f.warm();assert.equal(f.decodes(),0);assert.equal(f.api.status().assets,0);
        assert.equal(f.api.status().phase,'failed');assert.ok(f.api.status().error.includes(path));
    }
});
test('shipped mapping loads head_single spelling',()=>{
    const files=require('../files.example.json'),f=fixture({options:{directory:'/data/local/tmp',files}});
    f.missing('/data/local/tmp/head_sigle.png');f.warm();assert.equal(f.api.status().phase,'ready');
});
test('renderer setter failure restores original array and cleanup releases handles',async()=>{
    const f=fixture();f.warm();f.rejectSetter();const hold=f.create({highlight:true,renderers:true});
    assert.equal(hold.data.fields.noteImages,hold.data.original);assert.equal(f.api.status().skipped,1);
    const done=f.api.dispose();f.gameFrame();await done;assert.equal(f.handles.size,0);
});
test('menu pruning releases destroyed hold records without gameplay scans',()=>{
    const f=fixture();f.warm();const hold=f.create();hold.data.alive=false;
    assert.equal(f.api.status().tracked,1);f.gameFrame();assert.equal(f.api.status().tracked,1);
    f.menuFrame();assert.equal(f.api.status().tracked,0);
});
