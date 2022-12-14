package com.epam.thread;

import com.epam.service.TemperatureQueryService;
import com.epam.service.Testfuture;
import com.sun.java.accessibility.util.EventID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static java.lang.System.out;

@SpringBootTest
@WebAppConfiguration
@ComponentScan
@RunWith(SpringRunner.class)
public class CompleteTest {

    @Autowired
    private com.epam.service.Testfuture testfuture;
    @Autowired
    private com.epam.service.CompleteTest completeTest;
    @Test
    public void test() throws ExecutionException, InterruptedException {

        completeTest.test();
      //testfuture.test();
    }
}
