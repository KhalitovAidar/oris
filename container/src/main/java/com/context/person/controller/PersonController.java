package com.context.person.controller;

import com.context.annotations.Controller;
import com.context.annotations.GetRequest;

@Controller
public class PersonController {
    @GetRequest(path = "/")
    public void doGetSomething() {
        System.out.println("GetMethod called!");
    }

    @GetRequest(path = "/")
    public void doPostSomething() {
        System.out.println("PostMethod called!");
    }
}
