package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {


//    @GetMapping("/hello")
//    @ResponseBody
//    public String returnHelloWorld(){
//
//            return "Hello Zenith";
//
//    }

    @GetMapping("/hello/{fname}/{lname}")
    @ResponseBody
    public String greetName(@PathVariable String fname,@PathVariable String lname){
        return "Hello, " + fname +" "+ lname +"!";
    }


    @GetMapping("/upper")
    @ResponseBody
    public String returnHelloWorldUpper(@RequestParam(defaultValue = "true") boolean uppercase){
        if(uppercase){
            return "HELLO ZENITH!";
            //http://localhost:8080/upper?uppercase=true OR
            //http://localhost:8080/upper
        }else{
            return "Hello Zenith";
            //http://localhost:8080/upper?uppercase=false
        }
    }


//    @GetMapping("/ads/{id}")
//    @ResponseBody
//    public String returnID(@PathVariable String name){
//        return "Hello, " + name +"!";
//    }





    @GetMapping("/justin")
    @ResponseBody
    public String returnHelloJustin(){
        return "<h1>Hello Justin!!!<h1>";
    }

}
