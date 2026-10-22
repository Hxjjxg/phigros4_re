'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const vm = require('node:vm');
const C = require('../src/core');

function fixture(options, nativeGeometry) {
    let next = 1, failDecode = -1, decodes = 0, missing = null, rejectSetter = false;
    const objects = [], handles = new Map(), hooks = new Map(), intervals = new Set(), writes = [], blits = [];
    class P {
        constructor(data = {}, offset = 0, base) { this.data = data; this.offset = offset; this.base = base || this; this.id = base ? base.id : next++; }
        isNull() { return this.id === 0; }
        equals(p) { return this.id === p.id && this.offset === p.offset; }
        toString() { return '0x' + this.id.toString(16); }
        add(n) { return new P(this.data, this.offset+n, this.base); }
        readU8() { return this.data.fields.chordSupport ? 1 : 0; }
        readS32() { return this.data.fields.type; }
        readFloat() { return this.data.values[this.offset / 4]; }
        writeByteArray() { return this; }
    }
    const nil = new P(); nil.id = 0;
    const p = data => { const v = new P({alive:true, fields:{}, ...data}); objects.push(v); return v; };
    const array = items => p({items});
    const sprite = (name, w=64, h=32, py=16) => p({name, geometry:{w,h,px:w/2,py,ppu:100}});
    const normal = [sprite('head'),sprite('body',64,512,0),sprite('shared-tail')];
    const multi = [sprite('multi-head'),sprite('multi-body',64,512,0),normal[2]];
    if (nativeGeometry) [...normal, ...multi].forEach((s, i) => {
        const g = nativeGeometry[i];
        s.data.geometry = {w:g.width, h:g.height, px:g.pivot[0]*g.width, py:g.pivot[1]*g.height, ppu:g.ppu};
    });
    const prefabArray = array(normal.slice()), prefab = p({fields:{noteImages:prefabArray}});
    const holds = array([]), notesAbove = array([]), notesBelow = array([]), manager = p({fields:{holdControls:holds}});
    const line = p({fields:{Hold:prefab, HoldHL0:multi[0], HoldHL1:multi[1], _noteUpdateManager:manager, notesAbove, notesBelow, chordSupport:true}});
    const retain = (o, pinned) => { assert.equal(pinned, 0, 'Frida native bool argument must be integer 0'); const h=next++; handles.set(h,o); return h; };
    const r = {
        a:{gchandle_new:retain,gchandle_new_weakref:retain,gchandle_get_target:h=>handles.get(h)||nil,
            gchandle_free:h=>handles.delete(h), object_unbox:o=>o, object_new:()=>p({}), array_new:()=>array([])},
        scope:fn=>fn(), pin:o=>o, game:n=>n, unity:n=>n, klass:(a,ns,n)=>n,
        method:(k,name,argc,signature)=>{
            if(k==='SettingItemsManager'&&name==='Update')throw Error('No SettingItemsManager.Update in 4.0.1');
            if(k==='Texture2D'&&name==='.ctor')assert.equal(Array.from(signature).join(','),'System.Int32,System.Int32,UnityEngine.TextureFormat,System.Boolean');
            return {k,name,argc,signature,readPointer:()=>k+'.'+name};
        },
        offset:(k,n)=>({noteImages:0x50,_holdSpriteRenderer:0xa0,HoldHL0:0x48}[n] || 0),
        field:(o,k,n)=>o.data.fields[n]||nil,
        put:(o,k,n,v)=>{o.data.fields[n]=v; writes.push([o,n,v]);},
        item:(o,i)=>{if(!o.data.items || i>=o.data.items.length)throw Error('array bounds');return o.data.items[i];},
        setItem:(o,i,v)=>{o.data.items[i]=v;},
        int:n=>{assert.ok(Number.isInteger(n), 'Native integer argument must not truncate a fractional rect');return n;}, floats:values=>p({values}), str:s=>s, bool:b=>b, number:n=>n,
        alive:o=>!o.isNull()&&o.data.alive,
        destroy:o=>{o.data.alive=false;}, component:o=>o,
        listCount:o=>o.data.items.length, listItem:(o,i)=>o.data.items[i],
        call(o,name,args=[]) {
            if (name==='get_rect') return p({values:[0,0,o.data.geometry.w,o.data.geometry.h]});
            if (name==='get_pivot') return p({values:[o.data.geometry.px,o.data.geometry.py]});
            if (name==='get_pixelsPerUnit') return p({values:[o.data.geometry.ppu]});
            if (name==='get_width') return o.data.width;
            if (name==='get_height') return o.data.height;
            if (name==='set_name') {o.data.name=args[0]; return nil;}
            if (name==='set_sprite') {
                if (rejectSetter) { rejectSetter=false; throw Error('setter failure'); }
                o.data.current=args[0]; return nil;
            }
            if (name==='get_sprite') return o.data.current;
            return nil;
        },
        invoke(m,o,args=[]) {
            if(m.name==='.ctor') {o.data.width=args[0];o.data.height=args[1];return nil;}
            if(m.name==='LoadImage') {args[0].data.width=64;args[0].data.height=32; return decodes++ !== failDecode;}
            if(m.name==='Clone') return array(o.data.items.slice());
            if(m.name==='GetTemporary') return p({});
            if(m.name==='get_active') return nil;
            if(m.name==='Blit') {blits.push({signature:m.signature, args});return nil;}
            if(m.name==='Create') return p({texture:args[0],geometry:{w:args[1].data.values[2],h:args[1].data.values[3],px:args[2].data.values[0],py:args[2].data.values[1],ppu:args[3].data.values[0]}});
            return nil;
        }
    };
    const header=Buffer.alloc(33); Buffer.from([137,80,78,71,13,10,26,10]).copy(header);
    header.writeUInt32BE(13,8); header.write('IHDR',12); header.writeUInt32BE(64,16); header.writeUInt32BE(32,20);
    const context = {PHI_HOLD_SKIN_OPTIONS:options,PhiHoldCore:C,createPhiHoldRuntime:()=>r,NULL:nil,rpc:{exports:{}},console:{log(){}},
        Process:{findModuleByName:()=>true,findRangeByAddress:()=>({protection:'r-x'})},
        File:class {constructor(path){if(path===missing)throw Error('missing image');} readBytes(){return header;} close(){}},
        Interceptor:{attach(address,cb){hooks.set(address,cb);return {detach:()=>hooks.delete(address)};}},
        setInterval:fn=>{intervals.add(fn);return fn;},clearInterval:fn=>intervals.delete(fn),
        setTimeout,clearTimeout,setImmediate};
    vm.runInNewContext(fs.readFileSync(require.resolve('../src/skin.js'),'utf8'),context);
    for(const fn of intervals)fn();
    function preload(){const cb=hooks.get('JudgeLineControl.Start'),ctx={};cb.onEnter.call(ctx,[line]);cb.onLeave.call(ctx);}
    function create({highlight=false,above=true,renderers=false,type=3}={}) {
        const note=p({fields:{type}}), list=above?notesAbove:notesBelow; list.data.items.push(note);
        const cb=hooks.get('JudgeLineControl.CreateNote'),ctx={};
        cb.onEnter.call(ctx,[line,{toInt32:()=>list.data.items.length-1},{toInt32:()=>above?1:0}]);
        const originals = highlight ? multi : normal;
        const hold=p({fields:{noteInfor:note,judgeLine:line,noteImages:array(originals.slice())}});
        if(renderers)['_holdHeadSpriteRenderer','_holdSpriteRenderer','_holdEndSpriteRenderer1'].forEach((key,i)=>hold.data.fields[key]=p({current:originals[i]}));
        if(type===3)holds.data.items.push(hold);
        cb.onLeave.call(ctx);
        return hold;
    }
    return {api:context.PhiHoldSkin, hooks, handles, objects, writes, blits, preload, create, prefabArray, line, normal, multi,
        failDecode:n=>failDecode=n, missing:path=>missing=path,rejectSetter:()=>rejectSetter=true,
        pump:()=>hooks.get('LevelInformation.Update').onLeave(),
        decodes:()=>decodes};
}
test('both directions select six sprites, isolate shared tail, and preserve native classification sources', async () => {
    const f=fixture(); f.preload();
    const single=f.create(), multi=f.create({highlight:true,above:false,renderers:true});
    assert.deepEqual(single.data.fields.noteImages.data.items.map(p=>p.data.name), ['PhiHoldSkin/head_single','PhiHoldSkin/body_single','PhiHoldSkin/tail_single']);
    assert.deepEqual(multi.data.fields.noteImages.data.items.map(p=>p.data.name), ['PhiHoldSkin/head_multi','PhiHoldSkin/body_multi','PhiHoldSkin/tail_multi']);
    assert.equal(multi.data.fields._holdEndSpriteRenderer1.data.current.data.name, 'PhiHoldSkin/tail_multi');
    assert.equal(f.prefabArray.data.items[2],f.normal[2]);
    assert.equal(f.line.data.fields.HoldHL0,f.multi[0]);
    assert.equal(f.decodes(),6);
    assert.equal(f.api.status().single,1); assert.equal(f.api.status().multi,1);
    const done=f.api.dispose(); f.pump(); await done;
    assert.equal(single.data.fields.noteImages.data.items[2],f.normal[2]);
    assert.equal(multi.data.fields._holdEndSpriteRenderer1.data.current,f.normal[2]);
    assert.equal(f.handles.size,0); assert.equal(f.hooks.size,0);
});
test('partial image decode rolls back all assets and can retry without leaving partial skins', () => {
    const f=fixture(); f.failDecode(4); f.preload();
    assert.equal(f.api.status().phase,'failed'); assert.equal(f.api.status().assets,0);
    assert.equal(f.handles.size,0);
    const untouched=f.create({highlight:true});
    assert.equal(untouched.data.fields.noteImages.data.items[2],f.normal[2]);
    f.failDecode(-1); f.api.retry(); f.preload();
    const hold=f.create({highlight:true});
    assert.equal(hold.data.fields.noteImages.data.items[2].data.name,'PhiHoldSkin/tail_multi');
});
test('missing sixth file causes no Unity allocation or note changes', () => {
    const f=fixture(); f.missing('/local/data/tmp/tail_multi.png'); f.preload();
    assert.equal(f.decodes(),0); assert.equal(f.api.status().assets,0);
    assert.equal(f.api.status().phase,'failed');
    assert.ok(f.api.status().error.includes('/local/data/tmp/tail_multi.png'));
    assert.match(f.api.status().error, /missing image/);
});
test('shipped mapping loads the observed head_single filename rather than the nonexistent typo', () => {
    const files = JSON.parse(fs.readFileSync(require.resolve('../files.example.json'), 'utf8'));
    const f = fixture({directory:'/data/local/tmp', files});
    f.missing('/data/local/tmp/head_sigle.png');
    f.preload();
    assert.equal(f.api.status().phase, 'loaded');
    assert.equal(f.api.status().files.head_single, 'head_single.png');
    assert.equal(f.decodes(), 6);
});
test('actual fractional native sprites use integer buffers while retaining exact head/body/tail geometry', () => {
    const native = require('./fixtures/native-hold-sprites.json').sprites;
    const f = fixture(undefined, native); f.preload();
    const hold = f.create({highlight:true, above:false, renderers:true});
    const sprites = hold.data.fields.noteImages.data.items;
    assert.equal(f.api.status().error, null);
    assert.equal(f.api.status().multi, 1);
    for (let i=0; i<3; i++) {
        const original = native[i+3], s = sprites[i].data;
        assert.equal(s.geometry.w, original.width);
        assert.equal(s.geometry.h, original.height);
        assert.equal(s.geometry.ppu, original.ppu);
        assert.equal(s.geometry.py, original.pivot[1]);
        assert.equal(s.texture.data.width, Math.ceil(original.width));
        assert.equal(s.texture.data.height, Math.ceil(original.height));
    }
    assert.equal(f.blits.length, 6);
    f.blits.forEach(call => {
        // This build retains the two-argument Blit; the scale/offset overload is stripped.
        assert.equal(call.args.length, 2);
        assert.equal(call.signature, 'UnityEngine.Texture');
    });
});
test('invalid template reports native values and slot before changing a hold', () => {
    const f=fixture(); f.normal[0].data.geometry.w=NaN; f.preload();
    assert.match(f.api.status().error, /head_single/);
    assert.match(f.api.status().error, /NaN/);
    assert.equal(f.api.status().tracked, 0);
    assert.equal(f.api.status().spriteSets, 0);
});
test('existing renderer setter failure restores native array and records cleanup state', async () => {
    const f=fixture(); f.preload(); f.rejectSetter(); const hold=f.create({highlight:true,renderers:true});
    assert.equal(hold.data.fields.noteImages.data.items[2],f.normal[2]);
    assert.equal(f.api.status().skipped,1);
    const done=f.api.dispose();f.pump();await done;assert.equal(f.handles.size,0);
});
test('highlight-off and non-hold notes are handled without changing native timing logic', () => {
    const f=fixture(); f.preload(); f.line.data.fields.chordSupport=false;
    const hold=f.create({above:false}); assert.equal(hold.data.fields.noteImages.data.items[2].data.name,'PhiHoldSkin/tail_single');
    const tap=f.create({type:1});assert.equal(tap.data.fields.noteImages.data.items[0],f.normal[0]);
});
