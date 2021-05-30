package com.example.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

@ExtendWith(MockitoExtension.class)
class JobCompletionNotificationListenerTest {

  @InjectMocks
  JobCompletionNotificationListener listener;

  @Mock
  JobExecution jobExecution;

  @Test
  void testAfterJobIfNotComplete() {
    Mockito.doReturn(BatchStatus.STOPPING).when(jobExecution).getStatus();
    listener.afterJob(jobExecution);
    Mockito.verify(jobExecution, Mockito.times(1)).getStatus();
  }
}
