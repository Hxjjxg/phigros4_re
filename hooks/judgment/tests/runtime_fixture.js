// DEVELOPMENT ONLY. Never included by build.py. Isolated managed object, no scene/save calls.
rpc.exports.fixture = function () {
    const P = Phi, r = P.runtime, api = r.api;
    api.thread_attach(api.domain_get());
    const klass = r.game('ScoreControl');
    const listField = r.field(klass, '_noteCodes');
    const listClass = api.class_from_type(api.field_get_type(listField));
    const handles = [];
    function pin(p) { handles.push(api.gchandle_new(p, 0)); return p; }
    const self = pin(api.object_new(klass)), list = pin(api.object_new(listClass));
    const setField = new NativeFunction(Process.getModuleByName('libil2cpp.so').getExportByName('il2cpp_field_set_value'),
        'void', ['pointer', 'pointer', 'pointer']);
    const oldEnqueue = P.enqueue;
    P.enqueue = e => { e.synthetic = true; oldEnqueue(e); };
    try {
        r.invoke(r.method(listClass, '.ctor', 0), list);
        const ref = Memory.alloc(8); ref.writePointer(list); setField(self, listField, ref);
        const note = Memory.alloc(4); note.writeFloat(123);
        const timing = Memory.alloc(4); timing.writeFloat(0.12);
        const position = Memory.alloc(12); position.writeFloat(0); position.add(4).writeFloat(0); position.add(8).writeFloat(0);
        const hold = Memory.alloc(1); hold.writeU8(1); // Skips all Unity effect instantiation.
        const before = JSON.parse(JSON.stringify(P.stats.observed));
        r.invoke(r.method(klass, 'Perfect', 4), self, [note, timing, position, hold]);
        r.invoke(r.method(klass, 'Good', 4), self, [note, timing, position, hold]);
        r.invoke(r.method(klass, 'Bad', 2), self, [note, timing]);
        r.invoke(r.method(klass, 'Miss', 1), self, [note]);
        const counts = {};
        ['perfect', 'good', 'bad', 'miss'].forEach(n => {
            counts[n] = self.add(r.offset(klass, n)).readS32();
            if (counts[n] !== 1 || P.stats.observed[n] !== before[n] + 1)
                throw new Error('Fixture failed for ' + n);
        });
        return {passed: true, counts: counts, combo: self.add(r.offset(klass, '_combo')).readS32()};
    } finally { P.enqueue = oldEnqueue; handles.forEach(h => api.gchandle_free(h)); }
};
