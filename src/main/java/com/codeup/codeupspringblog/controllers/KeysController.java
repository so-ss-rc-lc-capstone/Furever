package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.config.KeysConfig;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeysController {
    @Autowired
    private KeysConfig keys;

    @GetMapping(value="/keys.js", produces = "application/javascript")
    public String getKeys(){
        String theKeys = String.format("""
                keys = {
                        filestack: "%s",
                        mapbox: "%s",
                }
                """, keys.fileStack, keys.mapbox);
        return theKeys;

    }
}
