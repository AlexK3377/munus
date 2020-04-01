package ua.kiev.prog.bank.entity;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Account from;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Account to;

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "state")
    private TransactionState state;

    public Transaction(Account from,
                       Account to,
                       String currency,
                       Double amount) {
        this.from = from;
        this.to = to;
        this.currency = currency;
        this.amount = amount;
        this.state = TransactionState.IN_PROGRESS;
    }

    public void success() {
        this.state = TransactionState.SUCCESS;
    }

    public void failed() {
        this.state = TransactionState.FAILED;
    }

    public TransactionState state() {
        return this.state;
    }
}
