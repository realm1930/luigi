package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("pizzas")
public class PizzaController {
    private final String [] pizzas = {"Prosciutto", "Margherita", "Calzone"};
    @GetMapping
    public ModelAndView pizzas(){
        return new ModelAndView("pizzas","pizzas",pizzas);
    }
}
