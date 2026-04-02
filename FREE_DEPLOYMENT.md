# Free deployment plan

## Recommended free stack
- Frontend: Vercel free tier
- Backend: Render free web service
- Database: Neon free PostgreSQL

## Why this fits the project
- The frontend is a Vite React app and can be deployed as a static site.
- The backend is a Spring Boot API and can run as a web service.
- PostgreSQL is already used in the app and works well on Neon.

## What is already ready in the codebase
- Frontend API URL is configurable through `VITE_API_BASE_URL`.
- Backend CORS is configurable through `APP_CORS_ALLOWED_ORIGINS`.
- Backend database URL, username, and password are already environment-based.

## Deployment steps

### 1) Create the database on Neon
- Create a free Neon project.
- Create a PostgreSQL database.
- Copy the connection string.
- Use it as `SPRING_DATASOURCE_URL` in the backend.

### 2) Deploy the backend on Render
- Create a new Web Service from the backend repository.
- Use the backend folder as the root directory.
- Build command: `./mvnw -DskipTests package`
- Start command: `java -jar target/*.jar`
- Set environment variables:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `SPRING_JPA_HIBERNATE_DDL_AUTO=update`
  - `SPRING_SQL_INIT_MODE=always`
  - `APP_CORS_ALLOWED_ORIGINS=<your Vercel URL>`

### 3) Deploy the frontend on Vercel
- Import the frontend folder as a Vite project.
- Set environment variable:
  - `VITE_API_BASE_URL=<your Render backend URL>/api`
- Deploy.

### 4) Test the app
- Open the frontend URL.
- Check that menu data loads.
- Add items to cart.
- Place an order.
- Confirm the backend can talk to Neon.

## Notes
- Keep the backend CORS origin updated with the final frontend URL.
- If data reset happens on free tiers, re-run initialization or seed data as needed.
- Free tiers may sleep when idle, so the first request can be slower.
