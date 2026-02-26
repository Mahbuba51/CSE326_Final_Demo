# Commands Reference

## Docker

### Start

```bash
docker compose up --build      # first time or after code changes
docker compose up              # if nothing changed
```

### Stop

```bash
Ctrl+C                         # stop while logs are visible
docker compose down            # stop and remove containers
docker compose down -v         # stop and also wipe the database
```

### Check logs

```bash
docker compose logs backend
docker compose logs frontend
docker compose logs db
```

---

## Git

### Initial setup (one time)

```bash
git init
git remote add origin <your-repo-url>
```

### Add a `.gitignore` in root (one time)

```
backend/target/
frontend/node_modules/
frontend/dist/
```

### Daily workflow

```bash
git pull                        # get latest changes before starting
git add .
git commit -m "your message"
git push
```

---

## URLs (when running)

| Service  | URL                   |
| -------- | --------------------- |
| Frontend | http://localhost:4173 |
| Backend  | http://localhost:8080 |
| Database | localhost:5432        |
