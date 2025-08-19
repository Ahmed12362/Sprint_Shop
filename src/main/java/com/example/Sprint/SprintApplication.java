package com.example.Sprint;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class SprintApplication {
    private final Job job;
    private final JobLauncher jobLauncher;

    public static void main(String[] args) {
        SpringApplication.run(SprintApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return run -> {
            JobParameters parameter = new JobParametersBuilder()
                    .addLong("longTime" , System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job , parameter);
        };
    }

}