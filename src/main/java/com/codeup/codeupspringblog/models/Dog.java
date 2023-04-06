package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;

@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11)", nullable = false)
    private Long id;

    @Column(columnDefinition = "TINYINT(3)",length = 3, nullable = false, unique = true)
    private int age;

    @Column(columnDefinition = "VARCHAR(200)",nullable = false)
    private String name;

    @Column(columnDefinition = "CHAR(2) DEFAULT'XX'", length = 2)
    private String resideState;

    public Dog(){

    }

    public Dog(Long id, int age, String name, String resideState) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.resideState = resideState;
    }

    public Dog(int age, String name, String resideState) {
        this.age = age;
        this.name = name;
        this.resideState = resideState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResideState() {
        return resideState;
    }

    public void setResideState(String resideState) {
        this.resideState = resideState;
    }
}
