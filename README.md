# ToDO List

## Prerequisites

- Java 21
- MySQL

## Setup

### 1. Configure MySQL

Make sure you have a MySQL instance running and create a database for the application. You don't need to add any tables - the application will generate the schema for you.


### 2. Set Environment Variables

Open the Command Prompt and set the environment variables for MySQL credentials. Replace `database_name`, `db_user` and `db_password` with your actual database name, username and password:

Windows (cmd.exe)
```
set DB_NAME=database_name
set DB_USERNAME=db_user
set DB_PASSWORD=db_password
```
Windows (PowerShell)
```
$env:DB_NAME = "database_name"
$env:DB_USERNAME = "db_user"
$env:DB_PASSWORD = "db_password"
```
Unix
```
export DB_NAME=database_name
export DB_USERNAME=db_user
export DB_PASSWORD=db_password
```

### 3. Run the Application

Navigate to the project directory and run the following command:
```
mvn spring-boot:run
```
If you would prefer to create a JAR and run it:
```
mvn clean package
java -jar target/ToDoList-0.0.1-SNAPSHOT.jar
```
For both options, you can use the Maven Wrapper instead: call `mvnw` or `./mvnw` (depending on your OS) instead of `mvn`.
