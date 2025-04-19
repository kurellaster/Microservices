package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.repository.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;
    private final JobExplorer jobExplorer;

    @Autowired
    public JobCompletionNotificationListener(@NonNull JdbcTemplate jdbcTemplate, @NonNull JobExplorer jobExplorer) {
        this.jdbcTemplate = jdbcTemplate;
        this.jobExplorer = jobExplorer;
    }

    @Override
    public void afterJob(@NonNull JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            try {
                // Get job execution statistics
                JobInstance jobInstance = jobExecution.getJobInstance();
                String jobName = jobInstance.getJobName();
                int totalExecutions = jobExplorer.getJobExecutions(jobInstance).size();
                long totalJobInstances = jobExplorer.getJobInstanceCount(jobName);

                log.info("Job Name: {}", jobName);
                log.info("Job Instance ID: {}", jobInstance.getInstanceId());
                log.info("Job Execution ID: {}", jobExecution.getId());
                log.info("Total executions of this instance: {}", totalExecutions);
                log.info("Total instances of this job: {}", totalJobInstances);
                log.info("Start Time: {}", jobExecution.getStartTime());
                log.info("End Time: {}", jobExecution.getEndTime());
                log.info("Status: {}", jobExecution.getStatus());
            } catch (NoSuchJobException e) {
                log.error("Error getting job statistics: {}", e.getMessage());
            }

            // Verify processed data
            jdbcTemplate.query("SELECT first_name, last_name FROM people",
                (rs, row) -> new Person(rs.getString(1), rs.getString(2))
            ).forEach(person -> log.info("Found <" + person + "> in the database."));
        }
    }
}
