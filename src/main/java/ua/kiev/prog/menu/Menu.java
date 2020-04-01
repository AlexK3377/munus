package ua.kiev.prog.menu;

import javax.persistence.*;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id_gen")
//    @SequenceGenerator(name="menu_id_gen", sequenceName = "menu_id_seq", allocationSize=1)
    private Integer id;

    @Column(name = "dishName")
    private String dishName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "discount")
    private Integer discount;

    public Menu(String dishName,
                Integer price,
                Integer weight,
                Integer discount) {
        this(null, dishName, price, weight, discount);
    }

    public Menu(Integer id,
                String dishName,
                Integer price,
                Integer weight,
                Integer discount) {
        this.id = id;
        this.dishName = dishName;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", dishName='" + dishName + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", discount=" + discount +
                '}';
    }

    public int price() {
        return price;
    }

    public double weight() {
        return weight;
    }
}
