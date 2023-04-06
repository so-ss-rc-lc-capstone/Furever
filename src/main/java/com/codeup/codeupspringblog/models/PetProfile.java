package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="petProfiles")
public class PetProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length=50)
    private String name;
    @Column(nullable = false)
    private Date dateOfBirth;
    @Column(nullable = true)
    private String image;
    @Column(nullable = true, columnDefinition = "Enum('F', 'M')")
    private String gender;


    public PetProfile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
