package ua.kiev.prog.bank.entity;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "balance")
    private Double balance;

    @ManyToOne
    private User user;

    public Account(String currency, User user) {
        this.currency = currency;
        this.balance = 0.0;
        this.user = user;
    }

    public String currency() {
        return currency;
    }

    public double replenish(double amount) {
        return balance += amount;
    }

    public double withdraw(double amount) {
        if (balance < amount) {
            throw new RuntimeException("Insufficient money");
        }
        return balance - amount;
    }

    public double balance() {
        return this.balance;
    }

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
