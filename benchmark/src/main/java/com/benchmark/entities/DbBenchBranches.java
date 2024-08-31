package com.benchmark.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "dbbench_branches")
public class DbBenchBranches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid", nullable = false)
    private int bid;

    @Column(name = "bbalance")
    private int bbalance;

    @Column(name = "filler", length = 88)
    private String filler;

    // Constructors
    public DbBenchBranches() {}

    public DbBenchBranches(int bid, int bbalance, String filler) {
        this.bid = bid;
        this.bbalance = bbalance;
        this.filler = filler;
    }

    // Getters and Setters
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getBbalance() {
        return bbalance;
    }

    public void setBbalance(int bbalance) {
        this.bbalance = bbalance;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    // You might want to add toString, equals, and hashCode methods here
}
