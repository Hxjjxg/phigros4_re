"""Local integration capture, separate from the user's server. Ctrl+C to stop."""
import asyncio
import json
from pathlib import Path
import websockets

TARGET = Path(__file__).resolve().parents[3] / 'work/judgment/received.jsonl'
TARGET.parent.mkdir(parents=True, exist_ok=True)

async def capture(socket):
    print('CONNECTED', flush=True)
    async for message in socket:
        event = json.loads(message)
        with TARGET.open('a', encoding='utf-8') as out:
            out.write(json.dumps(event, ensure_ascii=False) + '\n')
        print(json.dumps(event), flush=True)

async def main():
    async with websockets.serve(capture, '127.0.0.1', 18765):
        print('Capture server 127.0.0.1:18765', flush=True)
        await asyncio.Future()

if __name__ == '__main__':
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        pass
