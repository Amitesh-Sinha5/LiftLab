# LiftLab — Setup & Run Guide

## System Requirements

| Dependency | Minimum Version | Purpose |
|---|---|---|
| Java (JDK) | 17 | Compile and run the application |
| Maven | 3.9 | Build tool (or use the included `mvnw` wrapper) |
| MySQL | 8.0 | Primary database |
| Git | any | Clone the repository (optional) |

---

## 1. Check What's Already Installed

Run these in your terminal to see what versions you have:

```bash
java -version
javac -version
mvn -version
mysql --version
git --version
```

---

## 2. Install Missing Dependencies

### Java 17 (JDK)

**Windows (winget):**
```bash
winget install --id EclipseAdoptium.Temurin.17.JDK
```

**Windows (manual):**
Download from https://adoptium.net and run the installer.
After install, verify:
```bash
java -version
# expected: openjdk version "17.x.x"
```

---

### Maven 3.9+

> Skip this if you plan to use the included `mvnw` / `mvnw.cmd` wrapper — it downloads Maven automatically on first run.

**Windows (winget):**
```bash
winget install --id Apache.Maven
```

**Windows (manual):**
1. Download the binary zip from https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Maven`
3. Add `C:\Program Files\Maven\bin` to your `PATH` environment variable

Verify:
```bash
mvn -version
# expected: Apache Maven 3.9.x
```

---

### MySQL 8.0+

**Windows (winget):**
```bash
winget install --id Oracle.MySQL
```

**Windows (manual):**
Download MySQL Installer from https://dev.mysql.com/downloads/installer/ and run it.

Verify:
```bash
mysql --version
# expected: mysql  Ver 8.x.x
```

---

## 3. Database Setup

Start the MySQL service if it isn't already running:

```bash
# Windows (run as Administrator)
net start MySQL80
```

Log in to MySQL and create the database:

```bash
mysql -u root -p
```

Then inside the MySQL shell:

```sql
CREATE DATABASE IF NOT EXISTS liftlab;
EXIT;
```

> The default credentials in `application.properties` are:
> - Host: `localhost:3306`
> - Database: `liftlab`
> - Username: `root`
> - Password: `1234`
>
> If your MySQL root password is different, update it in:
> `LiftLab-miniproject-master/workoutapp/src/main/resources/application.properties`
> ```properties
> spring.datasource.password=YOUR_PASSWORD
> ```

---

## 4. Run the Application

Navigate to the `workoutapp` folder:

```powershell
cd LiftLab-miniproject-master/workoutapp
```

> If you are already inside the inner `LiftLab-miniproject-master/` folder (where `team-split.md` and other project docs live), run `cd workoutapp` instead.

### ✅ Quick Start (Tested & Working)

Open **PowerShell** in the `LiftLab-miniproject-master/workoutapp` directory and run:

```powershell
.\mvnw.cmd spring-boot:run
```

This will:
1. Download Maven automatically (first run only)
2. Compile the project
3. Start the Spring Boot application on port 8081

**Wait for the message:**
```
Started WorkoutappApplication in X.XX seconds (process running for X.XX)
```

Then open your browser to: `http://localhost:8081`

---

### Alternative Options

### Option B — Using System Maven

If you have Maven installed globally, you can run:

```bash
mvn spring-boot:run
```

### Option C — Run the compiled JAR

Build the JAR first:
```powershell
.\mvnw.cmd package -DskipTests       # Windows PowerShell
```

Then run it:
```bash
java -jar target/workoutapp-0.0.1-SNAPSHOT.jar
```

---

## 5. Access the App

Once you see this message in the terminal:

```
Started WorkoutappApplication in 18.07 seconds (process running for 19.22)
```

**The application is ready!** Open your browser and navigate to:

```
http://localhost:8081
```

You'll see the LiftLab home page with options to:
- Create and manage workouts
- Track exercises and progress
- Set fitness goals
- View body measurements and health metrics

---

## Troubleshooting

| Problem | Fix |
|---|---|
| `mvnw.cmd : The term 'mvnw.cmd' is not recognized` | You're using PowerShell. Use `.\mvnw.cmd spring-boot:run` (with `.\` prefix) instead of just `mvnw.cmd`. |
| `Cannot start maven from wrapper` | The `.mvn/wrapper/maven-wrapper.properties` file was missing — it is now included in the repo. Pull latest. |
| `Access denied for user 'root'` | Update `spring.datasource.password` in `application.properties` to match your MySQL password. |
| `Unknown database 'liftlab'` | Run `CREATE DATABASE liftlab;` in the MySQL shell (see Step 3). |
| `Port 8081 already in use` | Change `server.port` in `application.properties` to another port (e.g. `8082`). |
| `java.lang.UnsupportedClassVersionError` | You are running the app with a Java version older than 17. Install JDK 17+ and make sure `JAVA_HOME` points to it. |

---

## Quick-Start Checklist

- [ ] Java 17+ installed (`java -version`)
- [ ] MySQL 8.0+ installed and running
- [ ] `liftlab` database created
- [ ] Password in `application.properties` matches your MySQL setup
- [ ] Inside the `LiftLab-miniproject-master/workoutapp/` directory when running commands
- [ ] App running at `http://localhost:8081`
