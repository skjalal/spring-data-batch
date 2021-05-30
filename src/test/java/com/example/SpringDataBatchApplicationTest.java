package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {"spring.profiles.active=test"})
class SpringDataBatchApplicationTest {

  @Value("${spring.profiles.active}")
  String activeProfile;

  @Test
  void contextLoad() {
    assertTrue(activeProfile.equalsIgnoreCase("test"));
  }
}
