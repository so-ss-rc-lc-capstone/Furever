package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



    /* I want to be able to...
    * create, remove, filter product
    * */

}
