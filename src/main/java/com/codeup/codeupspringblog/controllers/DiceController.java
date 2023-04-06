package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiceController {
    @GetMapping("/roll-dice")
    public String postIndex(Model model){
//        int randNum = (int) ((Math.random()* 6-1)+1);
//        model.addAttribute("ranNum",randNum);
        return "rollDice2"; //return a view that allows user to guess
    }


//    @PostMapping("/roll-dice")
//    public String getNum(@RequestParam int guess ){
//        int randNum1 = (int) ((Math.random()* 6-1)+1);
//        int randNum2 = (int) ((Math.random()* 6-1)+1);
//
////        model.addAttribute("ranNum",randNum);
//        System.out.println("Youe guessed: " + guess);
//
//        if(randNum1 == guess){
//            System.out.println("Correct!");
//
//        }else{
//            System.out.println("Incorrect.");
//        }
//        return  "rollDice";
//    }



    @GetMapping("/roll-dice/{n}")
    public String getNum2(@PathVariable int n, Model model ){  //return a view that user guess
        int randNum = (int) ((Math.random()* 6-1)+1);

        model.addAttribute("guess", n);
        model.addAttribute("roll", randNum);


        return  "roll-guess";
    }
}
