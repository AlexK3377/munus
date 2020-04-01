package ua.kiev.prog.bank.entity;

import javax.persistence.*;


@Entity
@Table(name = "currency_exchange")
public class CurrencyExchange {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "currency1")
    private String currency1;

    @Column(name = "currency2")
    private String currency2;

    @Column(name = "rate")
    private Double rate;

    public CurrencyExchange(String currency1, String currency2, Double rate) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.rate = rate;
    }

    public Double rate() {
        return this.rate;
    }
}
