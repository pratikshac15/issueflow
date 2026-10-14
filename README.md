# IssueFlow

A full-stack issue tracker built with **Java 21, Spring Boot, REST APIs, React, TypeScript, Vite, H2, JUnit, and GitHub Actions**.

> Portfolio/demo application. The repository does not claim production users, deployments, or measured efficiency gains.

## Features
- Create, edit, delete, and search issues
- Track status (OPEN, IN_PROGRESS, RESOLVED) and priority (LOW, MEDIUM, HIGH)
- Filter by status and search title/description
- Dashboard counts, loading/error states, and form validation
- REST endpoints with validation and consistent error responses
- In-memory H2 database with seed data; automated backend tests and frontend build CI

## Requirements
- Java 21 and Maven 3.9+
- Node.js 20.19+ or 22.12+ and npm

## Run locally

**Terminal 1 — API**
```bash
cd backend
mvn spring-boot:run
```
API: http://localhost:8080/api/issues

**Terminal 2 — web app**
```bash
cd frontend
npm install
npm run dev
```
UI: http://localhost:5173 (Vite proxies `/api` to port 8080).

The database is in-memory; changes reset when the backend restarts. Seed records are loaded at startup.

## API
| Method | Path | Purpose |
|---|---|---|
| GET | `/api/issues` | List issues; optional `status` and `q` filters |
| GET | `/api/issues/{id}` | Get one issue |
| POST | `/api/issues` | Create issue |
| PUT | `/api/issues/{id}` | Update issue |
| DELETE | `/api/issues/{id}` | Delete issue (204) |
| GET | `/api/health` | Simple health endpoint |

Example:
```bash
curl -X POST http://localhost:8080/api/issues \
  -H 'Content-Type: application/json' \
  -d '{"title":"Review release notes","description":"Verify changelog","assignee":"Alex","priority":"HIGH","status":"OPEN"}'
```

Fields: `title` (required, 3–120 chars), `description` (max 2000), `assignee` (max 100), `priority` (`LOW`, `MEDIUM`, `HIGH`), `status` (`OPEN`, `IN_PROGRESS`, `RESOLVED`).

## Tests and checks
```bash
cd backend && mvn test
cd frontend && npm install && npm run typecheck && npm run build
```

GitHub Actions runs these checks on pushes and pull requests.

## Repo layout
```text
issueflow/
  backend/      Spring Boot API, persistence, validation, JUnit tests
  frontend/     React + TypeScript UI
  .github/      CI workflow
  README.md
```

## Ideas for next iteration
- Add authentication and role-based permissions
- Switch H2 to PostgreSQL and add database migrations
- Add pagination, sorting, audit history, and deployment
- Add Playwright end-to-end tests and measurable usage metrics

## Attribution
Original starter implementation created as a portfolio exercise. Review and customize before presenting it as your own work. Do not claim team adoption or real users without evidence.
