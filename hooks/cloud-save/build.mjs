import { build } from "esbuild";
import { fileURLToPath } from "node:url";

// Resolve inputs from this directory, independent of the caller's working directory.
await build({
    absWorkingDir: fileURLToPath(new URL(".", import.meta.url)),
    entryPoints: ["cloud_sync_version_bypass_bridge_RVA.ts"],
    outfile: "cloud_sync_version_bypass_bridge_RVA.js",
    bundle: true,
    format: "iife",
    platform: "neutral",
    target: "es2020",
    sourcemap: false,
    logLevel: "info",
});
