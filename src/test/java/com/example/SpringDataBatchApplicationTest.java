package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {"spring.profiles.active=test"})
class SpringDataBatchApplicationTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Test
  void contextLoad() {
    Integer count = jdbcTemplate.queryForObject("select count(*) from book", Integer.class);
    assertNotNull(count);
    assertTrue(count > 0);
  }
}
