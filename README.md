# Songtiment

A full-stack AI-driven social platform that combines music and sentiment analysis to deliver personalized song recommendations. Built by a team of 6.

## Tech Stack

### Frontend
- **Framework**: Vue.js
- **Data Visualization**: D3.js

### Backend
- **Framework**: Spring Boot (Java)
- **API**: RESTful API

### AI / ML
- **LLM Integration**: LangChain
- **External APIs**: YouTube Data API

### Infrastructure
- **Cloud**: AWS

## Features

- User sentiment analysis linked to personalized music recommendations
- LLM-powered chatbot for interactive music discovery (LangChain + YouTube API)
- Real-time data processing via REST API endpoints
- Interactive D3.js dashboards for music analytics and sentiment trends
- Full-stack architecture coordinating frontend, backend, and ML workstreams

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Maven

### Backend (Spring Boot)

```bash
cd backend
mvn install
mvn spring-boot:run
```

### Frontend (Vue.js)

```bash
cd frontend
npm install
npm run dev
```

## Environment Variables

### Backend
```
OPENAI_API_KEY=your_openai_key
YOUTUBE_API_KEY=your_youtube_key
```

### Frontend
```
VITE_API_BASE_URL=http://localhost:8080
```
