'use strict';
(function (root) {
    const slots = ['head_single', 'body_single', 'tail_single', 'head_multi', 'body_multi', 'tail_multi'];
    function config(input = {}) {
        const directory = input.directory || '/local/data/tmp';
        if (!directory.startsWith('/') || directory.includes('\0')) throw Error('Expected an absolute Android image directory');
        const files = Object.fromEntries(slots.map(key => [key, key + '.png']));
        Object.assign(files, input.files || {});
        for (const key of slots) {
            if (typeof files[key] !== 'string' || !files[key] || /[\/\\\0]/.test(files[key])) throw Error('Invalid image filename: ' + key);
        }
        return {directory: directory.replace(/\/+$/, ''), files};
    }
    // Classify BEFORE replacing any references. Both native HL slots must agree.
    function classify(chordSupport, current, normal, highlighted) {
        if (!chordSupport) return 'single';
        const match = pair => pair.every((value, i) => value != null && value === current[i]);
        const single = match(normal), multi = match(highlighted);
        if (single === multi) throw Error('Ambiguous native hold sprites; refusing to guess the tail variant');
        return multi ? 'multi' : 'single';
    }
    function geometry(rect, pivot, ppu) {
        const [w, h] = rect, [x, y] = pivot;
        if (![w, h, x, y, ppu].every(Number.isFinite) || w <= 0 || h <= 0 || ppu <= 0 ||
            w > 4096 || h > 4096 || Math.ceil(w) * Math.ceil(h) > 8388608) {
            throw Error('Unsupported native sprite geometry: rect=' + rect.map(String).join('x') +
                '; pivot=' + pivot.map(String).join(',') + '; ppu=' + String(ppu));
        }
        // Rect stores floats: the APK's multi head/body heights are fractional.
        // Preserve extents and pivot; only texture allocation may round up.
        return {width: w, height: h, pivot: [x / w, y / h], ppu};
    }
    function pngSize(bytes) {
        if (bytes.length < 33 || [137,80,78,71,13,10,26,10].some((v,i) => bytes[i] !== v) ||
            String.fromCharCode(...bytes.slice(12,16)) !== 'IHDR') throw Error('Expected a PNG image');
        const v = new DataView(bytes.buffer, bytes.byteOffset, bytes.byteLength);
        const width = v.getUint32(16), height = v.getUint32(20);
        if (!width || !height || width > 4096 || height > 4096 || width * height > 8388608) throw Error('PNG exceeds image size limit');
        return {width, height};
    }
    const api = {slots, config, classify, geometry, pngSize};
    root.PhiHoldCore = api;
    if (typeof module !== 'undefined') module.exports = api;
})(globalThis);
