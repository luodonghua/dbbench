package com.benchmark.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import com.benchmark.entities.DbBenchBranches;
import com.benchmark.entities.DbBenchHistory;
import com.benchmark.entities.DbBenchTellers;
import com.benchmark.config.Config;
import com.benchmark.entities.DbBenchAccounts;
import com.benchmark.repositories.DbBenchBranchesRepository;
import com.benchmark.repositories.DbBenchTellerRepository;
import com.benchmark.repositories.DbBenchAccountsRepository;
import com.benchmark.repositories.DbBenchHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.benchmark.repositories")
@EntityScan(basePackages = "com.benchmark.entities")
@ComponentScan(basePackages = {"com.benchmark.services", "com.benchmark.config", "com.benchmark.repositories"})

public class DbBench {


    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(DbBench.class);

    private final Config config;
    private final DbBenchBranchesRepository branchRepo;
    private final DbBenchTellerRepository tellerRepo;
    private final DbBenchAccountsRepository accountRepo;
    private final DbBenchHistoryRepository historyRepo;
    
    

    @Autowired
    public DbBench(Config config, 
                   DbBenchBranchesRepository branchRepo, 
                   DbBenchTellerRepository tellerRepo, 
                   DbBenchAccountsRepository accountRepo,
                   DbBenchHistoryRepository historyRepo) {
        this.config = config;
        this.branchRepo = branchRepo;
        this.tellerRepo = tellerRepo;
        this.accountRepo = accountRepo;
        this.historyRepo = historyRepo;
    }

    @Autowired
    private DatabaseStatisticsUpdater statisticsUpdater;
    
    public static void main(String[] args) {
        SpringApplication.run(DbBench.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {

            long startTime = System.currentTimeMillis();
            int scaleFactor = config.getScaleFactor();

            if ("init-data".equalsIgnoreCase(config.getExecutionMode())) {
                logger.info("Initializing data with scale factor: " +  scaleFactor);

                // Insert scaled number of rows for dbbench_branches
                List<DbBenchBranches> branches = new ArrayList<>();
                for (int i = 1; i <= scaleFactor; i++) {
                    DbBenchBranches branch = new DbBenchBranches();
                    // branch.setBid(i);
                    branch.setBbalance(0);
                    branch.setFiller(String.format("%-" + 88 + "s", 'x').replace(' ', 'x'));
                    branches.add(branch);
                }
                branchRepo.saveAll(branches);
                logger.info("Inserted " + scaleFactor + " branches");

                // Insert scaled number of rows for dbbench_tellers
                List<DbBenchTellers> tellers = new ArrayList<>();
                for (int i = 1; i <= 10 * scaleFactor; i++) {
                    DbBenchTellers teller = new DbBenchTellers();
                    // teller.setTid(i);
                    teller.setBranch(branches.get((i - 1) % scaleFactor)); // Distribute tellers across branches
                    teller.setTbalance(0);
                    teller.setFiller(String.format("%-" + 84 + "s", 'x').replace(' ', 'x'));
                    tellers.add(teller);
                }
                tellerRepo.saveAll(tellers);
                logger.info("Inserted " + (10 * scaleFactor) + " tellers");

                // Insert scaled number of rows for dbbench_accounts
                List<DbBenchAccounts> accounts = new ArrayList<>();
                Random random = new Random();
                int totalAccounts = 100000 * scaleFactor;
                for (int i = 1; i <= totalAccounts; i++) {
                    DbBenchAccounts account = new DbBenchAccounts();
                    // account.setAid(i);
                    account.setBranch(branches.get((i - 1) % scaleFactor)); // Distribute accounts across branches
                    account.setAbalance(random.nextInt(100000)); // Random balance between 0 and 99999
                    account.setFiller(String.format("%-" + 84 + "s", 'x').replace(' ', 'x'));
                    accounts.add(account);
                    
                    if (i % 1000 == 0) {
                        accountRepo.saveAll(accounts);
                        accounts.clear();
                        logger.info("Processed {}/{} accounts\n", i, totalAccounts);
                    }
                }
                if (!accounts.isEmpty()) {
                    accountRepo.saveAll(accounts);
                }
                long endTime = System.currentTimeMillis();
                double elapsedTimeSeconds = (endTime - startTime) / 1000.0;
                logger.info("Data initialization completed in {} seconds.", elapsedTimeSeconds);
                
                // update statistics based on common used database engines
                statisticsUpdater.updateStatistics();

            } else {
                logger.info("Data initialization skipped.");
            }
        };
    }

    // Check whether table has data initialized
    private boolean isTableExistsAndNotEmpty (){
        long rowAccounts = accountRepo.count();
        long rowBreaches = branchRepo.count();
        long rowTellers = tellerRepo.count();
        if (rowAccounts > 0 && rowBreaches > 0 && rowTellers > 0)
            return true;
        else
            return false;
    }

    @Bean
    public CommandLineRunner runLoadTest(DbBench dbBench) {
    return args -> {
        // First, check data initialization 
        if (!isTableExistsAndNotEmpty()) {
            logger.error("Table is empty. Run data initialization first.");
            return;
        }
        // Then run the load test
        String executionMode = config.getExecutionMode();
        int numThreads = config.getLoadTestThreads();
        int transactionsPerThread = config.getLoadTestTxnPerThread();
        int runTimeSeconds = config.getMaxDuration();
        dbBench.runLoadTest(executionMode,numThreads, transactionsPerThread, runTimeSeconds);
    };
    }   

    public void runLoadTest(String executionMode, int numThreads, int transactionsPerThread, int runTimeSeconds) {
        

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger completedTransactions = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();

        // skip if current execution mode for data initiation
        if ("init-data".equalsIgnoreCase(config.getExecutionMode())) {
            return;
        }

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < transactionsPerThread; j++) {
                    if (System.currentTimeMillis() - startTime > runTimeSeconds * 1000) {
                        break;
                    }
                    if ("tpc-b".equalsIgnoreCase(executionMode)) {
                        runTransactionTPCB();
                    } else if ("simple-update".equalsIgnoreCase(executionMode)) {
                        runTransactionSimpleUpdate();
                    } else if ("select-only".equalsIgnoreCase(executionMode)) {
                        runTransactionSelectOnly();
                    } else {
                        logger.error("Invalid execution mode: " + executionMode);
                        return;
                    }
                    completedTransactions.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(runTimeSeconds + 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        double elapsedTimeSeconds = (endTime - startTime) / 1000.0;
        double tps = completedTransactions.get() / elapsedTimeSeconds;

        logger.info("'{}' Load Test Results:", executionMode);
        logger.info("Total Transactions: " + completedTransactions.get());
        logger.info("Elapsed Time: " + elapsedTimeSeconds + " seconds");
        logger.info("Transactions per Second: " + String.format("%.3f", tps));
    }

    @Transactional
    public void runTransactionTPCB() {
        int scaleFactor = config.getScaleFactor();
        int aid = random.nextInt(100000 * scaleFactor) + 1;
        int bid = random.nextInt(scaleFactor) + 1;
        int tid = random.nextInt(10 * scaleFactor) + 1;
        int delta = random.nextInt(10001) - 5000;

        DbBenchAccounts account = accountRepo.findById(aid).orElseThrow();
        account.setAbalance(account.getAbalance() + delta);
        accountRepo.save(account);

        DbBenchTellers teller = tellerRepo.findById(tid).orElseThrow();
        teller.setTbalance(teller.getTbalance() + delta);
        tellerRepo.save(teller);

        DbBenchBranches branch = branchRepo.findById(bid).orElseThrow();
        branch.setBbalance(branch.getBbalance() + delta);
        branchRepo.save(branch);

        DbBenchHistory history = new DbBenchHistory();
        history.setTeller(teller);
        history.setBranch(branch);
        history.setAccount(account);
        history.setDelta(delta);
        LocalDateTime currentTime = LocalDateTime.now();
        history.setMtime(currentTime);
        history.setFiller(String.format("%-" + 22 + "s", 'x').replace(' ', 'x'));
        historyRepo.save(history);
    }

    @Transactional
    public void runTransactionSimpleUpdate() {
        int scaleFactor = config.getScaleFactor();
        int aid = random.nextInt(100000 * scaleFactor) + 1;
        int bid = random.nextInt(scaleFactor) + 1;
        int tid = random.nextInt(10 * scaleFactor) + 1;
        int delta = random.nextInt(10001) - 5000;

        // Update pgbench_accounts
        DbBenchAccounts account = accountRepo.findById(aid).orElseThrow();
        account.setAbalance(account.getAbalance() + delta);
        accountRepo.save(account);

        // Select abalance (we're not using the result, but we're performing the select)
        accountRepo.findById(aid);

        DbBenchTellers teller = tellerRepo.findById(tid).orElseThrow();
        DbBenchBranches branch = branchRepo.findById(bid).orElseThrow();

        // Insert into pgbench_history
        LocalDateTime currentTime = LocalDateTime.now();
        DbBenchHistory history = new DbBenchHistory();
        history.setTeller(teller);
        history.setBranch(branch);
        history.setAccount(account);
        history.setDelta(delta);
        history.setMtime(currentTime);
        historyRepo.save(history);
    }

    @Transactional(readOnly = true)
    public void runTransactionSelectOnly() {
        int scaleFactor = config.getScaleFactor();
        int aid = random.nextInt(100000 * scaleFactor) + 1;

        // Select abalance from pgbench_accounts
        DbBenchAccounts account = accountRepo.findById(aid).orElse(null);
        
        // We're not using the result, but in a real scenario, you might want to do something with it
        if (account != null) {
            int abalance = account.getAbalance();
            // Optionally, you could log or process the balance
            // System.out.println("Account " + aid + " balance: " + abalance);
        }
    }

}
