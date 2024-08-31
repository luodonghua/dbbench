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
@Table(name = "dbbench_accounts")
public class DbBenchAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid", nullable = false)
    private int aid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid", referencedColumnName = "bid")
    private DbBenchBranches branch;

    @Column(name = "abalance")
    private int abalance;

    @Column(name = "filler", length = 84)
    private String filler;

    // Constructors
    public DbBenchAccounts() {}

    public DbBenchAccounts(int aid, DbBenchBranches b, int abalance, String filler) {
        this.aid = aid;
        this.branch = b;
        this.abalance = abalance;
        this.filler = filler;
    }

    // Getters and Setters
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public DbBenchBranches getBranch() {
        return branch;
    }

    public void setBranch(DbBenchBranches branch) {
        this.branch = branch;
    }

    public int getAbalance() {
        return abalance;
    }

    public void setAbalance(int abalance) {
        this.abalance = abalance;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    // You might want to add toString, equals, and hashCode methods here
}
