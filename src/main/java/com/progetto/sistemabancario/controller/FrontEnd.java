package com.progetto.sistemabancario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class FrontEnd {
    @GetMapping
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/index.html");
        return modelAndView;
    }
    
    @GetMapping("/transfer")
    public ModelAndView getTransfer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/transfer.html");
        return modelAndView;
    }

    
    @GetMapping("/create")
    public ModelAndView getCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/newAccount.html");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView getDelete() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/delAccount.html");
        return modelAndView;
    }
    @GetMapping("/depWith")
    public ModelAndView getDepWith() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/depositWithdraw.html");
        return modelAndView;
    }
}
