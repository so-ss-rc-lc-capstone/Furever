package com.codeup.codeupspringblog.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HelloController {

    static class Car{
         String make;
         String model;
         int year;


//        public void Cars(String make,String model,int year){
//            this.make = make;
//            this.model = model;
//            this.year = year;
//        }

//        Data owner1 = new Data(make, model, year);


    }

    @GetMapping("/howdy")
    public String sayHowdyPage(Model model){
        model.addAttribute("name", "Alex");
        return "howdy";
    }


    @GetMapping("/matrix")
    public String returnMatrixPage(){
        return "matrix";
    }


    @GetMapping("/about")
    public String returnAboutPage(){
        return "about";
    }

}
