# Student Management REST API — Java + Tomcat

JAX-RS (Jersey) RESTful service with a **service layer**, **uniform response envelope**, **CORS filter**, and **custom exception mapping**.

---

## Build & Deploy

```bash
mvn clean package
# → copy target/student-api.war to Tomcat/webapps/
```

Base URL: `http://localhost:8080/student-api/api`

---

## Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/students` | List all students |
| GET | `/api/students?major=Physics` | Filter by major |
| GET | `/api/students/{id}` | Get one student |
| GET | `/api/students/search?q=alice` | Search by name/email |
| POST | `/api/students` | Create student |
| PUT | `/api/students/{id}` | Full update |
| PATCH | `/api/students/{id}` | Partial update |
| DELETE | `/api/students/{id}` | Delete student |

---

## Response Envelope

All responses are wrapped:
```json
{
  "success": true,
  "message": "Student found",
  "data": { ... }
}
```

---

## Example Requests

### Create a student
```http
POST /api/students
Content-Type: application/json

{
  "firstName": "Eve",
  "lastName": "Davis",
  "email": "eve@uni.edu",
  "major": "Biology",
  "gpa": 3.7,
  "year": 2
}
```

### Partial update (PATCH)
```http
PATCH /api/students/1
Content-Type: application/json

{ "gpa": 3.95 }
```

### Search
```
GET /api/students/search?q=alice
```

### Filter by major
```
GET /api/students?major=Computer+Science
```

---

## Project Structure

```
student-api/
├── pom.xml
└── src/main/java/com/example/student/
    ├── model/
    │   ├── Student.java          # Data model
    │   └── ApiResponse.java      # Response envelope
    ├── service/
    │   └── StudentService.java   # Business logic + in-memory store
    ├── resource/
    │   └── StudentResource.java  # REST endpoints
    ├── exception/
    │   ├── StudentNotFoundException.java
    │   └── StudentNotFoundMapper.java  # JAX-RS exception mapper
    └── filter/
        └── CorsFilter.java       # CORS headers
```
