### **dbbench**, database benchmarking tool for RDBMS

**dbbench** is based on Spring JPA and supposes to be database agnostic benchmarking too.

1. Clone the git repository

```bash
git clone https://github.com/luodonghua/dbbench.git
```

2. Navigate to `benchmark` directory

```bash
cd dbbench/benchmark
```

3. Compile this project

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

2024-08-31T14:16:04.097Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : Starting DbBench using Java 17.0.12 with PID 5885 (/home/ec2-user/environment/dbbench/benchmark/target/classes started by ec2-user in /home/ec2-user/environment/dbbench/benchmark)
2024-08-31T14:16:04.100Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : No active profile set, falling back to 1 default profile: "default"
2024-08-31T14:16:04.474Z  INFO 5885 --- [dbbench] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-08-31T14:16:04.522Z  INFO 5885 --- [dbbench] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 37 ms. Found 4 JPA repository interfaces.
2024-08-31T14:16:05.077Z  INFO 5885 --- [dbbench] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2024-08-31T14:16:05.179Z  INFO 5885 --- [dbbench] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.4.Final
2024-08-31T14:16:05.221Z  INFO 5885 --- [dbbench] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2024-08-31T14:16:05.533Z  INFO 5885 --- [dbbench] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2024-08-31T14:16:05.563Z  INFO 5885 --- [dbbench] [           main] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Starting...
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
2024-08-31T14:16:06.153Z  INFO 5885 --- [dbbench] [           main] com.zaxxer.hikari.pool.HikariPool        : SpringBootJPAHikariCP - Added connection oracle.jdbc.driver.T4CConnection@78da899f
2024-08-31T14:16:06.154Z  INFO 5885 --- [dbbench] [           main] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Start completed.
2024-08-31T14:16:06.980Z  INFO 5885 --- [dbbench] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2024-08-31T14:16:07.147Z  INFO 5885 --- [dbbench] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-08-31T14:16:07.414Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : Started DbBench in 3.675 seconds (process running for 3.997)
2024-08-31T14:21:07.623Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : 'tpc-b' Load Test Results:
2024-08-31T14:21:07.624Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : Total Transactions: 610125
2024-08-31T14:21:07.624Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : Elapsed Time: 300.014 seconds
2024-08-31T14:21:07.625Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : Transactions per Second: 2033.655
2024-08-31T14:21:07.625Z  INFO 5885 --- [dbbench] [           main] com.benchmark.services.DbBench           : Data initialization skipped.
2024-08-31T14:21:07.628Z  INFO 5885 --- [dbbench] [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2024-08-31T14:21:07.629Z  INFO 5885 --- [dbbench] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Shutdown initiated...
2024-08-31T14:21:07.646Z  INFO 5885 --- [dbbench] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : SpringBootJPAHikariCP - Shutdown completed.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  05:06 min
[INFO] Finished at: 2024-08-31T14:21:07Z
[INFO] ------------------------------------------------------------------------
```
