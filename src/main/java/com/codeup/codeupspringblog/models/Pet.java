package com.codeup.codeupspringblog.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.codeup.codeupspringblog.models.Breed;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length=50)
    private String petname;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(nullable = true)
    private LocalDate dateOfBirth;

    @Column(nullable = true)
    private String image;
    @Column(nullable = true, columnDefinition = "Enum('F', 'M')")
    private String gender;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id") //
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "breed_id", nullable = true) //
    private Breed breed;

//    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "pet")
//    private List<Breed> breeds = new ArrayList<>();


    public Pet() {
    }

    public Pet(String petname, LocalDate dateOfBirth, String image, String gender, User user, Breed breed) {
        this.petname = petname;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.gender = gender;
        this.user = user;
        this.breed = breed;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

//    public List<Breed> getBreeds() {
//        return breeds;
//    }
//
//    public void setBreeds(List<Breed> breeds) {
//        this.breeds = breeds;
//    }
}

