package com.benchmark.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;


@Entity
@Table(name = "dbbench_tellers")
public class DbBenchTellers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid", nullable = false)
    private int tid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid", referencedColumnName = "bid")
    private DbBenchBranches branch;

    @Column(name = "tbalance")
    private int tbalance;

    @Column(name = "filler", length = 84)
    private String filler;

    // Constructors
    public DbBenchTellers() {}

    public DbBenchTellers(int tid, DbBenchBranches b, int tbalance, String filler) {
        this.tid = tid;
        this.branch = b;
        this.tbalance = tbalance;
        this.filler = filler;
    }

    // Getters and setters
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public DbBenchBranches getBranch() {
        return branch;
    }

    public void setBranch(DbBenchBranches branch) {
        this.branch = branch;
    }


    public int getTbalance() {
        return tbalance;
    }

    public void setTbalance(int tbalance) {
        this.tbalance = tbalance;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    // You might want to add toString, equals, and hashCode methods here
}
