### **dbbench**, database benchmarking tool for RDBMS

**dbbench** is inspired by `pgbench`, written using Java and Spring JPA, serves the purpose as a database agnostic benchmarking tool.

1. Clone the git repository

```bash
git clone https://github.com/luodonghua/dbbench.git
```

2. Navigate to `benchmark` directory

```bash
cd dbbench/benchmark
```

3. Compile this project using Maven

```bash
mvn compile
```

4. Edit file `src/main/resources/application.properties`, key parameters are:
  - dbbench.execution-mode=init-data
  - dbbench.scale-factor=10
  - spring.datasource.url=
  - spring.datasource.username
  - spring.datasource.password

5. Generate test dataset

```bash
mvn spring-boot:run
```

6. Once test data generated, edit file `src/main/resources/application.properties`, key parameters are:

- dbbench.execution-mode=tpc-b
- dbbench.scale-factor=10
- dbbench.load-test.threads=32
- dbbench.load-test.txn-per-thread=100000
- dbbench.load-test.max-duration=300
- spring.datasource.hikari.maximumPoolSize=32

7. Execute the load benmarking

```bash
mvn spring-boot:run
```

8. Obtain the benmarking output

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

2024-09-01T09:18:49.810+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Starting DbBench using Java 17.0.11 with PID 7482 (/Users/donghua/dbbench/benchmark/target/classes started by donghua in /Users/donghua/dbbench/benchmark)
2024-09-01T09:18:49.814+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : No active profile set, falling back to 1 default profile: "default"
2024-09-01T09:18:50.446+08:00  INFO 7482 --- [dbbench] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-09-01T09:18:50.538+08:00  INFO 7482 --- [dbbench] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 79 ms. Found 4 JPA repository interfaces.
2024-09-01T09:18:51.417+08:00  INFO 7482 --- [dbbench] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2024-09-01T09:18:51.507+08:00  INFO 7482 --- [dbbench] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.4.Final
2024-09-01T09:18:51.571+08:00  INFO 7482 --- [dbbench] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2024-09-01T09:18:51.969+08:00  INFO 7482 --- [dbbench] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2024-09-01T09:18:52.013+08:00  INFO 7482 --- [dbbench] [           main] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Starting...
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
2024-09-01T09:18:52.498+08:00  INFO 7482 --- [dbbench] [           main] com.zaxxer.hikari.pool.HikariPool        : SpringBootJPAHikariCP - Added connection org.postgresql.jdbc.PgConnection@6588b715
2024-09-01T09:18:52.500+08:00  INFO 7482 --- [dbbench] [           main] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Start completed.
2024-09-01T09:18:53.832+08:00  INFO 7482 --- [dbbench] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2024-09-01T09:18:53.973+08:00  INFO 7482 --- [dbbench] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-09-01T09:18:54.604+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Started DbBench in 5.424 seconds (process running for 5.913)
2024-09-01T09:18:54.611+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Data initialization skipped.
2024-09-01T09:19:05.016+08:00  INFO 7482 --- [dbbench] [pool-3-thread-1] com.benchmark.services.DbBench           : Transactions per Second (last 10s): 192.08
2024-09-01T09:19:15.014+08:00  INFO 7482 --- [dbbench] [pool-3-thread-1] com.benchmark.services.DbBench           : Transactions per Second (last 10s): 209.38
2024-09-01T09:19:25.014+08:00  INFO 7482 --- [dbbench] [pool-3-thread-1] com.benchmark.services.DbBench           : Transactions per Second (last 10s): 218.40
2024-09-01T09:19:35.014+08:00  INFO 7482 --- [dbbench] [pool-3-thread-1] com.benchmark.services.DbBench           : Transactions per Second (last 10s): 231.20
2024-09-01T09:19:45.014+08:00  INFO 7482 --- [dbbench] [pool-3-thread-1] com.benchmark.services.DbBench           : Transactions per Second (last 10s): 258.80
2024-09-01T09:19:55.014+08:00  INFO 7482 --- [dbbench] [pool-3-thread-1] com.benchmark.services.DbBench           : Transactions per Second (last 10s): 285.93
2024-09-01T09:19:55.035+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Final 'tpc-b' Load Test Results:
2024-09-01T09:19:55.036+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Total Transactions: 13968
2024-09-01T09:19:55.038+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Total Elapsed Time: 60.023 seconds
2024-09-01T09:19:55.039+08:00  INFO 7482 --- [dbbench] [           main] com.benchmark.services.DbBench           : Overall Transactions per Second: 232.71
2024-09-01T09:19:55.048+08:00  INFO 7482 --- [dbbench] [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2024-09-01T09:19:55.052+08:00  INFO 7482 --- [dbbench] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Shutdown initiated...
2024-09-01T09:19:55.071+08:00  INFO 7482 --- [dbbench] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Shutdown completed.

```
