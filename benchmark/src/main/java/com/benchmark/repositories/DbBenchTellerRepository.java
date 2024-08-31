package com.benchmark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.benchmark.entities.*;

@Repository
public interface DbBenchTellerRepository extends JpaRepository<DbBenchTellers, Integer> {

    // You can add custom query methods here if needed
    // For example:
    // List<DbBenchTellers> findByBid(int bid);
    // Optional<DbBenchTellers> findByTidAndBid(int tid, int bid);

}
