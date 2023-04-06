package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Product;
import com.codeup.codeupspringblog.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ProductController {


    // Dependency Injection
    private ProductRepository productsDao;

    public ProductController(ProductRepository productsDao) {
        this.productsDao = productsDao;
    }


    //    @GetMapping("/products/create")
//    public String postIndex(){
//        return "products";
//    }
//
//
//
//    @PostMapping("/products/create")
//    public String createPost(@RequestParam String name , @RequestParam int price){
//        System.out.println(name);
//        System.out.println(price);
////        return "<p>Product: "+name+"</p><p>Price: " +price+"</p>";
//        return "redirect:/";
//    }

    @GetMapping("/products/create")
    public String postIndex(){
        return "products/create";
    }



    @PostMapping("/products/create")
    public String createPost(@RequestParam String name , @RequestParam int price){
        System.out.println(name);
        System.out.println(price);
        Product product = new Product(name,price);
        productsDao.save(product);
        return "redirect:/products/posts";
//        return "<p>Product: "+name+"</p><p>Price: " +price+"</p>";
    }


    @GetMapping("/products/posts")
    public String getProductIndexPage(Model model){
//        List<Product> products = new ArrayList<>(Arrays.asList(
//            new Product(1L, "mouse",800 ),
//            new Product(2L, "computer",50000 ),
//            new Product(3L, "house",3000000 )
//
//        ));
        List<Product> products = productsDao.findAll();


        model.addAttribute("products", products);
//        List<Product> filteredProductsList = products
//                .stream()
//                .filter(product -> product.getPriceInCents()<1000)
//                .collect(Collectors.toList());
//        model.addAttribute("products", filteredProductsList);


        return "products/posts";
    }

    @GetMapping("product/delete/{n}")
    public String deleteProduct(@PathVariable long n){
        productsDao.deleteById(n);
        return "redirect:/products/posts";
    }


    @GetMapping("product/create")
    public String createProduct(){
        Product product = new Product("Cat",60000);
        productsDao.save(product);
        return "redirect:/products";
    }


    @GetMapping("/products/{id}")
    public String findProductById(@PathVariable long id){
        Optional<Product> product = productsDao.findById(id);
        if(product.isPresent()){
            System.out.println(product.get());
        }
        return "redirect:/products";
    }


}
