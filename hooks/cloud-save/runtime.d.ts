// Frida provides console at runtime; @types/frida-gum covers the Gum APIs.
declare const console: {
    log(...values: unknown[]): void;
};
