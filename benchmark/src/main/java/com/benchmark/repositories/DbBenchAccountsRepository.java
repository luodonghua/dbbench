package com.benchmark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.benchmark.entities.*;


@Repository
public interface DbBenchAccountsRepository extends JpaRepository<DbBenchAccounts, Integer> {

    // You can add custom query methods here if needed

}
