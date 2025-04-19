# Spring Batch Example (Spring Boot 2.7)

This project demonstrates a simple Spring Batch application using Spring Boot 2.7. It reads data from a CSV file, processes it (converts names to uppercase), and writes it to an H2 database.

## Technologies Used
- Spring Boot 2.7.18
- Spring Batch 4.3.9
- H2 Database
- Java 11

## Project Structure
- `BatchProcessingApplication.java`: Main application class
- `Person.java`: Model class for the data
- `PersonItemProcessor.java`: Processes the data by converting names to uppercase
- `BatchConfiguration.java`: Configures the batch job
- `JobCompletionNotificationListener.java`: Logs job execution results
- `sample-data.csv`: Sample input data

## How to Run
1. Clone the repository
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Features
- CSV file reading
- Data processing (name conversion to uppercase)
- H2 database storage
- Job execution monitoring
- Batch processing statistics

## Database Schema
- Application table: `people`
- Spring Batch metadata tables (automatically created)
  - BATCH_JOB_INSTANCE
  - BATCH_JOB_EXECUTION
  - BATCH_JOB_EXECUTION_PARAMS
  - BATCH_STEP_EXECUTION
  - BATCH_JOB_EXECUTION_CONTEXT
  - BATCH_STEP_EXECUTION_CONTEXT

## Configuration
See `application.properties` for database and batch configuration settings.
