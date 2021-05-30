package com.example.config;

import com.example.domain.Book;
import com.example.listener.JobCompletionNotificationListener;
import com.example.model.BookModel;
import com.example.processor.BookProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.utils.Constants.BOOK_READER;
import static com.example.utils.Constants.CHUNK_SIZE;
import static com.example.utils.Constants.IMPORT_USER_JOB;
import static com.example.utils.Constants.INPUT_FILE;
import static com.example.utils.Constants.STEP;
import static com.example.utils.Constants.WRITE_SQL;
import static com.example.utils.Constants.getFieldNames;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Autowired
  public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public BookProcessor bookProcessor() {
    return new BookProcessor();
  }

  @Bean
  public JobCompletionNotificationListener listener(JdbcTemplate jdbcTemplate) {
    return new JobCompletionNotificationListener(jdbcTemplate);
  }

  @Bean
  public FlatFileItemReader<BookModel> reader() {
    return new FlatFileItemReaderBuilder<BookModel>()
        .name(BOOK_READER)
        .resource(new ClassPathResource(INPUT_FILE))
        .delimited()
        .names(getFieldNames())
        .fieldSetMapper(getFieldSetMapper())
        .build();
  }

  @Bean
  public JdbcBatchItemWriter<Book> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Book>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .sql(WRITE_SQL)
        .dataSource(dataSource)
        .build();
  }

  @Bean
  public Step step(
      JdbcBatchItemWriter<Book> writer, BookProcessor bookProcessor, FlatFileItemReader<BookModel> reader) {
    return stepBuilderFactory
        .get(STEP)
        .<BookModel, Book>chunk(CHUNK_SIZE)
        .reader(reader)
        .processor(bookProcessor)
        .writer(writer)
        .build();
  }

  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step step) {
    return jobBuilderFactory
        .get(IMPORT_USER_JOB)
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step)
        .end()
        .build();
  }

  private FieldSetMapper<BookModel> getFieldSetMapper() {
    BeanWrapperFieldSetMapper<BookModel> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(BookModel.class);
    return fieldSetMapper;
  }
}
