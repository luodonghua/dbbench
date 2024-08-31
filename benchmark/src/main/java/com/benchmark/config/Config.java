package com.benchmark.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class Config {
    @Value("${dbbench.scale-factor}")
    private int scaleFactor;

    @Value("${dbbench.execution-mode}")
    private String executionMode;

    @Value("${dbbench.load-test.threads}")
    private int loadTestThreads;
    @Value("${dbbench.load-test.txn-per-thread}")
    private int loadTestTxnPerThread;
    @Value("${dbbench.load-test.max-duration}")
    private int maxDuration;

   
    // Add getters for your configuration properties
    public int getScaleFactor() {
        return scaleFactor;
    }
    public String getExecutionMode() {
        return executionMode;
    }
    public int getLoadTestThreads() {
        return loadTestThreads;
    }
    public int getLoadTestTxnPerThread() {
        return loadTestTxnPerThread;
    }
    public int getMaxDuration() {
        return maxDuration;
    }


    // Add other configuration properties as needed
}


