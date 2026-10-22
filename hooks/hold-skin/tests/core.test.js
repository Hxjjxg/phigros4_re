'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const C = require('../src/core');
const nativeSprites = require('./fixtures/native-hold-sprites.json').sprites;

test('actual APK hold rectangles retain fractional extents, pivots and PPU', () => {
    for (const s of nativeSprites) {
        const g = C.geometry([s.width, s.height], [s.pivot[0] * s.width, s.pivot[1] * s.height], s.ppu);
        assert.equal(g.width, s.width); assert.equal(g.height, s.height);
        assert.equal(g.ppu, s.ppu); assert.deepEqual(g.pivot, s.pivot);
        assert.equal(g.height / g.ppu, s.height / s.ppu);
    }
});

test('native profile geometry matches the static APK evidence', () => {
    const profile = require('../native-profile.json');
    profile.geometry.forEach((g, i) => {
        const {width,height,pivot,ppu} = nativeSprites[i];
        assert.deepEqual(g, {width,height,pivot,ppu});
    });
    assert.deepEqual(profile.sites.map(s=>s.offset), [0x8e4,0x918,0x105c,0x1090]);
});
test('thin body keeps its original aspect and native hold length basis', () => {
    const reference = require('../native-profile.json').geometry[1];
    const g = C.artGeometry({width:20,height:512}, reference, 'body_single');
    assert.equal(g.width, 20); assert.equal(g.height, 512);
    assert.equal(g.height / g.ppu, reference.height / reference.ppu);
    assert.equal((g.width / g.ppu) / (g.height / g.ppu), 20 / 512);
    assert.ok(g.width / g.ppu < reference.width / reference.ppu);
});
test('caps retain their artwork aspect with a uniform scale and native anchors', () => {
    const references = require('../native-profile.json').geometry;
    for (const i of [0,2,3,5]) {
        const slot = C.slots[i], reference = references[i];
        const g = C.artGeometry({width:400,height:120}, reference, slot);
        assert.equal(g.width / g.height, 400 / 120);
        assert.ok(Math.abs(g.width / g.ppu - reference.width / reference.ppu) < 1e-12);
        assert.deepEqual(g.pivot, reference.pivot);
    }
});
test('geometry retains off-centre body origin and native endpoint extents', () => {
    const g = C.geometry([300,1900], [150,0], 100);
    assert.deepEqual(g, {width:300, height:1900, pivot:[0.5,0], ppu:100});
    assert.equal(g.height / g.ppu, 19);
    assert.deepEqual(C.geometry([64,32], [32,32], 100).pivot, [0.5,1]);
    assert.deepEqual(C.geometry([64,32], [32,-4], 100).pivot, [0.5,-0.125]);
    assert.throws(() => C.geometry([0,32], [0,0], 100));
    assert.throws(() => C.geometry([64,32], [0,0], NaN));
});
test('explicit path mapping supports the supplied head_sigle spelling without mixing directories', () => {
    assert.equal(C.config().directory, '/local/data/tmp');
    const c = C.config({directory:'/data/local/tmp/', files:{head_single:'head_sigle.png', tail_multi:'hold_tail_multi.png'}});
    assert.equal(c.directory, '/data/local/tmp');
    assert.equal(c.files.head_single, 'head_sigle.png');
    assert.equal(c.files.tail_multi, 'hold_tail_multi.png');
    assert.throws(() => C.config({directory:'relative'}));
    assert.throws(() => C.config({files:{head_single:'../head.png'}}));
    assert.throws(() => C.config({files:{head_single:'head\0.png'}}));
});
test('PNG dimension validation handles buffer offsets and rejects invalid/oversized inputs', () => {
    const b = Buffer.alloc(35); const p = b.subarray(2);
    Buffer.from([137,80,78,71,13,10,26,10]).copy(p);
    p.writeUInt32BE(13,8); p.write('IHDR',12); p.writeUInt32BE(128,16); p.writeUInt32BE(2048,20);
    assert.deepEqual(C.pngSize(p), {width:128,height:2048});
    p.writeUInt32BE(0,16); assert.throws(() => C.pngSize(p), /size limit/);
    p.writeUInt32BE(8192,16); assert.throws(() => C.pngSize(p), /size limit/);
    assert.throws(() => C.pngSize(Buffer.from('not a PNG')), /PNG/);
});
