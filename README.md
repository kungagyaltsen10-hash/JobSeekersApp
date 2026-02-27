# AI Job Auto Apply Frontend

Production-ready React frontend for an AI-powered job auto-apply platform.

## Tech Stack
- React + Vite
- TailwindCSS
- Axios
- React Router
- Context API (Auth state)
- JWT token management

## Features
- Signup/Login with JWT persistence
- Protected routes with auto redirect
- Dashboard with profile summary and analytics cards
- Resume upload with drag-drop style area and status indicator
- Location preferences (multi-select cities, remote/relocation toggles, state)
- Job matches with weighted score breakdown and confidence indicator
- Applications tracker with city/status filters
- Service layer for API integrations
- Mobile responsive SaaS-style UI

## Project Structure

```bash
src/
  components/
    common/
    layout/
    ui/
  constants/
  context/
  pages/
    auth/
    dashboard/
  routes/
  services/
  utils/
```

## Environment Variables
Copy `.env.example` to `.env`:

```bash
VITE_API_BASE_URL=http://localhost:8000/api
```

## Run Locally

```bash
npm install
npm run dev
```

## Build

```bash
npm run build
npm run preview
```

## API Notes
Services are in `src/services`. Endpoints expected:
- `POST /auth/signup`
- `POST /auth/login`
- `GET /auth/me`
- `GET /dashboard/summary`
- `POST /profile/resume`
- `PUT /profile/location-preferences`
- `GET /jobs/matches`
- `GET /jobs/applications`

Mock fallback data is included for dashboard/matches/applications so UI remains usable before backend completion.

## Docker
Build and run with Docker:

```bash
docker build -t ai-job-auto-apply-frontend .
docker run -p 8080:80 ai-job-auto-apply-frontend
```
