package com.example.Sprint.config;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.model.Product;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {
    private final BatchProcessor processor;
    @Bean
    public FlatFileItemReader<ProductDto> reader(){
        return new FlatFileItemReaderBuilder<ProductDto>()
                .name("Reader")
                .linesToSkip(1)
                .resource(new ClassPathResource("products.csv"))
                .delimited()
                .names("name","price","categoryName")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductDto>(){{
                    setTargetType(ProductDto.class);
                }})
                .build();
    }
    @Bean
    public JpaItemWriter<Product> writer(EntityManagerFactory managerFactory){
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(managerFactory);
        return writer;
    }
    @Bean
    public Step step(JobRepository jobRepository , PlatformTransactionManager manager
            , FlatFileItemReader<ProductDto> reader , JpaItemWriter<Product> writer){
        return new StepBuilder("step1",jobRepository)
                .<ProductDto ,Product>chunk(5,manager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository , Step step){
        return new JobBuilder("job1" , jobRepository)
                .start(step)
                .build();
    }

}
