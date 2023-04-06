package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;

@Entity
@Table(name = "products") // changing name
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incrementing ID
    private Long id;


    @Column(name = "product_name", nullable = false, length = 100)
    private String name;

    @Column()
    private int costInCents;

    public Product( Long id,String name, int costInCents) {
        this.id = id;
        this.name = name;
        this.costInCents = costInCents;
    }

    public Product( String name, int costInCents) {
        this.name = name;
        this.costInCents = costInCents;
    }

    public Product(){
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(int costInCents) {
        this.costInCents = costInCents;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", costInCents=" + costInCents +
                '}';
    }
}
