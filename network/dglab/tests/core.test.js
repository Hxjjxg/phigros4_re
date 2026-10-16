const {test} = require('node:test');
const assert = require('node:assert/strict');
const {DglabCore, frames} = require('../core');
function setup() {
    let now = 1000;
    const sent = [], closed = [], alive = new Set(['one', 'two']);
    const core = new DglabCore({
        send: (id, text) => { sent.push({id, ...JSON.parse(text)}); return alive.has(id); },
        close: (id, code, reason) => closed.push({id, code, reason}), connected: id => alive.has(id)
    }, {targetId: 'target', clock: () => now, timeout: 100});
    const receive = data => core.receive('one', JSON.stringify({type: 'message', data}));
    const reply = (request, result) => receive({t: 'resp', reqId: request.data.reqId, result});
    const open = () => core.open('one', '/?tid=target');
    return {core, sent, closed, alive, receive, reply, open, time: n => { now = n; }};
}
test('official app handshake, targetId alias, single peer and no remote controller', () => {
    const h = setup(); h.open();
    assert.deepEqual(h.sent.slice(0, 2), [
        {id: 'one', type: 'hello', clientId: 'one'},
        {id: 'one', type: 'controller_attached', clientId: 'target'}]);
    assert.equal(h.sent[2].clientId, undefined);
    h.core.open('two', '/?targetId=target'); assert.equal(h.closed.at(-1).code, 4003);
    h.core.open('two', '/'); assert.equal(h.closed.at(-1).code, 4001);
    h.core.shutdown();
});
test('snapshot, recursive patches, removal, stale discovery and forged responses', async () => {
    const h = setup(); h.open(); const get = h.sent.at(-1);
    h.receive({t: 'ev', ev: 'devices.snapshot', devices: [{slotId: 's', name: 'new', props: {a: {x: 1, y: 2}}}]});
    h.reply(get, {devices: [{slotId: 'stale'}]});
    assert.equal(h.core.devices.has('stale'), false);
    h.receive({t: 'ev', ev: 'slots.patch', slots: [{slotId: 's', props: {a: {x: 3}}}]});
    assert.deepEqual(h.core.devices.get('s').props.a, {x: 3, y: 2});
    const p = h.core.request('ping'); const req = h.sent.at(-1);
    h.core.receive('two', JSON.stringify({type: 'message', data: {t: 'resp', reqId: req.data.reqId, result: 1}}));
    assert.equal(h.core.pending.size, 1);
    h.reply(req, 2); assert.equal(await p, 2);
    h.receive({t: 'ev', ev: 'devices.patch', removed: ['s']}); assert.equal(h.core.devices.size, 0);
    h.core.shutdown();
});
test('timeouts, late responses, reconnect identity and error responses', async () => {
    const h = setup(); h.open(); h.reply(h.sent.at(-1), {devices: []});
    const p = h.core.request('ping'); const req = h.sent.at(-1);
    h.time(1200); h.core.tick(); await assert.rejects(p, /timeout/);
    h.reply(req, 1); assert.equal(h.core.pending.size, 0);
    const p2 = h.core.request('ping'); h.core.disconnect('one'); await assert.rejects(p2, /disconnected/);
    h.core.open('two', '/?tid=target'); assert.equal(h.core.devices.size, 0);
    h.core.shutdown();
});
test('judgments produce concurrent finite tasks, repeated frames, cooldown, and no replay', async () => {
    const h = setup(); h.open(); h.reply(h.sent.at(-1), {devices: [{slotId: 's', type: 'COYOTE_030'}]});
    const config = {rules: {good: {intensity: 5, durationMs: 1000, waveform: 'test'}}, channel: 0, slotId: '', cooldownMs: 150};
    const event = {grade: 'good', seq: 1, timestamp: 1000};
    const waves = {test: ['0A0A0A0A00000000', '0A0A0A0A64646464']};
    assert.equal(h.core.judgment(event, config, waves), true);
    const requests = h.sent.slice(-2);
    assert.equal(requests[0].data.data.t, 4); assert.equal(requests[1].data.data.t, 0);
    assert.equal(requests[1].data.data.v.length, 10);
    assert.equal(requests[0].data.data.im, true);
    assert.equal(h.core.judgment(event, config, waves), false);
    h.time(1600); assert.equal(h.core.judgment(event, config, waves), false);
    requests.forEach(r => h.reply(r, {reason: 'replaced'}));
    await Promise.resolve(); assert.equal(h.core.pending.size, 0);
    h.core.shutdown();
});
test('validation rejects bad pulse/operation without transmission; clear response is tracked', async () => {
    const h = setup(); h.open(); h.reply(h.sent.at(-1), {devices: [{slotId: 's'}]});
    const n = h.sent.length;
    await assert.rejects(h.core.operate({s: 's', c: 0, t: 7, v: 50}), /intensity/);
    await assert.rejects(h.core.operate({s: 's', c: 0, t: 0, v: ['bad'], d: 1000}), /waveform/);
    await assert.rejects(h.core.operate({s: 's', c: 0, t: 4, v: 5, d: 0}), /duration/);
    assert.equal(h.sent.length, n);
    assert.deepEqual(frames([[1, 2, 3]], 2), [[1, 2, 3]]);
    const p = h.core.clear('s', 0); const r = h.sent.at(-1);
    assert.deepEqual(r.data.data, {s: 's', c: 0});
    h.reply(r, {cleared: true}); assert.deepEqual(await p, {cleared: true});
    h.core.shutdown();
});
