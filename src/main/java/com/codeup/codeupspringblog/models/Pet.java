package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length=50)
    private String petname;
    @Column(nullable = true)
    private Date dateOfBirth;
    @Column(nullable = true)
    private String image;
    @Column(nullable = true, columnDefinition = "Enum('F', 'M')")
    private String gender;


    public Pet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
