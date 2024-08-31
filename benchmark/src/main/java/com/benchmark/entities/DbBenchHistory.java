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
import java.time.LocalDateTime;


@Entity
@Table(name = "dbbench_history")
public class DbBenchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    private DbBenchTellers teller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid", referencedColumnName = "bid")
    private DbBenchBranches branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aid", referencedColumnName = "aid")
    private DbBenchAccounts account;

    @Column(name = "delta")
    private int delta;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "filler", length = 22)
    private String filler;

    // Constructors
    public DbBenchHistory() {}

    public DbBenchHistory(DbBenchTellers t, DbBenchBranches b, DbBenchAccounts a, int delta, LocalDateTime mtime, String filler) {
        this.teller = t;
        this.branch = b;
        this.account = a;
        this.delta = delta;
        this.mtime = mtime;
        this.filler = filler;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DbBenchTellers getTeller() {
        return teller;
    }

    public void setTeller(DbBenchTellers teller) {
        this.teller = teller;
    }
    public DbBenchBranches getBranch() {
        return branch;
    }

    public void setBranch(DbBenchBranches branch) {
        this.branch = branch;
    }

    public DbBenchAccounts getAccount() {
        return account;
    }

    public void setAccount(DbBenchAccounts account) {
        this.account = account;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public LocalDateTime getMtime() {
        return mtime;
    }

    public void setMtime(LocalDateTime mtime) {
        this.mtime = mtime;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }
    // toString, equals, and hashCode methods would go here
}
