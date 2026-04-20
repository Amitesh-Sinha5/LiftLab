# LiftLab

LiftLab is an integrated fitness tracking web application built with Java Spring Boot.
It helps users plan workouts, manage exercises, set fitness goals, and track physical progress from one interface.

The project combines a Thymeleaf-based UI with a Goal REST API, plus utility tools like BMI and One Rep Max calculators and Excel export for progress data.

## Problem It Solves

Many fitness tools split workout planning, progress tracking, and goal management across multiple apps.
LiftLab provides an end-to-end workflow in one system:

- Workout planning and scheduling
- Real-time workout session tracking
- Body measurement and progress monitoring
- Goal tracking with completion/progress updates
- Health and performance calculators

## Core Features

### 1. Workout Management

- Create, view, update, and delete workouts
- Add exercises to workouts with configurable sets and reps
- Schedule workouts for a future date/time
- Start live workout sessions and mark sets complete in real time
- Log start time, end time, and total workout duration

### 2. Exercise Management

- Create, edit, delete, and browse exercises
- Exercises are sorted by type in the list view
- Uses Builder pattern to create exercises from form input

### 3. Progress Tracking

- Record body measurements (date, weight, height, chest, waist, biceps)
- BMI is computed at runtime from weight and height (not persisted)
- Dashboard combines body measurements with recent workout history
- Edit/delete measurement entries
- Export body measurements to `.xlsx`

### 4. Goal Management

- Create and manage goals with type, description, target date, reminder date
- Track progress percentage
- Mark goals complete
- Auto status logic supports `active`, `completed`, and `overdue`
- Includes both Thymeleaf UI and JSON REST endpoints

### 5. BMI Calculator

- Calculates BMI using height and weight input
- Applies age-aware classification using a Chain of Responsibility:
- Under 18: paediatric advisory message
- 65 and over: healthy upper bound is 27.0
- 18-64: standard WHO adult thresholds

### 6. One Rep Max Calculator

- Computes estimated one-rep max using Epley formula
- Input: lifted weight and reps
- Output: estimated 1RM value

## Tech Stack

- Java 17
- Spring Boot 3.4.4
- Spring MVC + Thymeleaf
- Spring Data JPA + Hibernate
- MySQL (runtime)
- Apache POI (Excel export)
- Maven Wrapper (`mvnw` / `mvnw.cmd`)
- JUnit 5 (Spring Boot test starter)

## Project Structure

```text
LiftLab/
|- setup-run.md
|- README.md
|- workoutapp/
   |- pom.xml
   |- mvnw / mvnw.cmd
   |- src/main/java/com/workoutapp/workoutapp/
   |  |- controller/
   |  |- model/
   |  |- repository/
   |  |- service/
   |  |- command/
   |  |- handler/
   |- src/main/resources/
      |- application.properties
      |- templates/
```

## Architecture Overview

- `controller`: HTTP request handling (UI and API endpoints)
- `model`: JPA entities and form/domain models
- `repository`: Data access with Spring Data JPA
- `service`: Orchestration layer (Facade for complex operations)
- `handler`: BMI classification chain logic
- `command`: Export abstraction and Excel export implementation

## Design Patterns Used

- Builder Pattern: exercise object creation
- Facade Pattern: workout deletion with dependent logs/completed sets
- Command Pattern: Excel export operation encapsulation
- Chain of Responsibility: BMI classification by age category

Note:
The requirements mention observer-based notifications for goal completion. In the current implementation, goals are completed directly in controller logic without dedicated observer classes/events.

## Data Model Summary

- `Workout`: workout definition, scheduled time, exercise sets, logs
- `Exercise`: reusable exercise master data
- `ExerciseSet`: joins workout + exercise with sets/reps
- `WorkoutLog`: session log with start/end/duration and completed sets
- `CompletedSet`: per-set completion status during tracking
- `WorkoutSchedule`: scheduled workout date/time entries
- `BodyMeasurement`: progress metrics and runtime BMI computation
- `Goal`: target tracking with progress and status logic

## Main UI Routes

- `/` home
- `/workouts` workout list and management
- `/workouts/scheduled` upcoming schedule view
- `/workouts/start/{workoutId}` start live tracking
- `/workouts/logs` completed workout logs
- `/exercises` exercise management
- `/progress` progress dashboard
- `/progress/export` Excel export
- `/goals` goals UI
- `/bmi` BMI calculator
- `/onerepmax` One Rep Max calculator

## Goal REST API (JSON)

Base path: `/goals/api`

- `GET /goals/api` list all goals
- `GET /goals/api/{id}` get goal by id
- `POST /goals/api` create goal
- `PUT /goals/api/{id}` update full goal
- `PATCH /goals/api/{id}/progress?progress=VALUE` update progress
- `PATCH /goals/api/{id}/complete` mark as complete
- `DELETE /goals/api/{id}` delete goal

Example create payload:

```json
{
  "goalType": "Weight Loss",
  "description": "Lose 4 kg in 8 weeks",
  "progress": 25.0,
  "completed": false,
  "targetDate": "2026-06-30",
  "reminderDate": "2026-05-15"
}
```

## Formula Reference

- BMI:

```text
BMI = weight(kg) / (height(m) * height(m))
```

- One Rep Max (Epley):

```text
1RM = weight * (1 + reps / 30)
```

## Prerequisites

- Java 17+
- MySQL 8+
- Git (optional, for cloning)

## Database Configuration

Current application config uses:

- URL: `jdbc:mysql://localhost:3306/liftlab`
- Username: `root`
- Password: `root`
- Port: `8081`

Create database:

```sql
CREATE DATABASE IF NOT EXISTS liftlab;
```

If needed, edit credentials in `workoutapp/src/main/resources/application.properties`.

## Run Locally

From repository root:

```bash
cd workoutapp
```

macOS/Linux:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Application URL:

```text
http://localhost:8081
```

## Build and Test

From `workoutapp`:

```bash
./mvnw clean package
./mvnw test
```

Windows:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd test
```

## Common Troubleshooting

- `Permission denied` on `mvnw`: run `chmod +x workoutapp/mvnw`
- MySQL auth error: verify username/password in `application.properties`
- Unknown database: create `liftlab` database in MySQL
- Port conflict on 8081: change `server.port` in `application.properties`
- Java version error: verify JDK 17+ is active

## Future Enhancements

- Authentication and multi-user profile support
- Dedicated observer/event notifications for goal completion
- More analytics and trend visualizations
- API documentation (OpenAPI/Swagger)
- Dockerization and CI workflow

## License

No license file is currently provided in this repository.
