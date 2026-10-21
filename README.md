# wackyquacky

This document is specific to the MVP we are building.

The planned app analyses a users chess.com games and identifies weaknesses in their openings. — e.g. *"in the Caro-Kann
Advance you lose most after 5.Be2, where you play ...Nc6 instead of ...c5"*.

## Architecture

```
             ┌──────────┐   POST /analyses     ┌──────────┐
  client ──▶│   api    │── writes job row ──▶│          │
             │ (Spring) │◀── reads results ───│ Postgres │
             └──────────┘                      │          │
                                               │          │
            ┌──────────┐ ── polls jobs ──────▶│          │
            │  worker  │ ── writes results ──▶│          │
            │ (Spring) │                       └──────────┘
            └────┬─────┘
                 ├──▶ chess.com public API (games)
                 └──▶ Stockfish (child process, UCI)
```

| Module   | Responsibility                                                                                                           |
|----------|--------------------------------------------------------------------------------------------------------------------------|
| `core`   | Domain types, opening classification, mistake detection, aggregation, DB entities and repositories. Shared by both apps. |
| `api`    | API Endpoints only, doesn't touch any of the heavy lifting.                                                              |
| `worker` | Fetches games and analyses.                                                                                              |

### Design decisions

- Modular Monolith with two deployables. CPU heavy workload can be scaled
- Postgres is used for the job queue instead of something like Redis as this allows us to save and queue a job in the same transaction.
- We can implement caching for evaluations of positions as openings are often repeated saving on processing.
- Using win probabilities vs centipawns gives each mistake contextualised importance
- We will count an opening as book moves + 5 to ensure we are not counting middle game errors as opening errors. We will analyse up to maximum 20 moves.

## Running locally

_TODO_
