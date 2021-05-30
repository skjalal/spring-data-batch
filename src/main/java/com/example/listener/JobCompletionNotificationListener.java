package com.example.listener;

import com.example.dto.BookDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.example.utils.Constants.READ_SQL;

@Log4j2
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final String BOOK_ID = "book_id";
  private static final String TITLE = "title";
  private static final String AUTHORS = "authors";
  private final JdbcTemplate jdbcTemplate;

  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.debug("Batch Processing Initializing: {}", jobExecution.getStatus());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    Optional.of(jobExecution.getStatus()).filter(this::validate).ifPresent(this::verifyResult);
  }

  private boolean validate(BatchStatus batchStatus) {
    return batchStatus == BatchStatus.COMPLETED;
  }

  private void verifyResult(BatchStatus batchStatus) {
    log.debug("Batch processing completed!!!");
    jdbcTemplate.query(READ_SQL, this::rowMapper).forEach(this::reading);
  }

  private BookDto rowMapper(ResultSet rs, int row) throws SQLException {
    return BookDto.builder()
        .bookId(Long.parseLong(rs.getString(BOOK_ID)))
        .title(rs.getString(TITLE))
        .authors(rs.getString(AUTHORS))
        .build();
  }

  private void reading(BookDto bookDto) {
    log.debug(
        "Book ID: {} - Title: {} - Authors: {}",
        bookDto.getBookId(),
        bookDto.getTitle(),
        bookDto.getAuthors());
  }
}
