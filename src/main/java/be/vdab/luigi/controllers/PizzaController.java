package be.vdab.luigi.controllers;

import be.vdab.luigi.Services.EuroService;
import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pizzas")
public class PizzaController {
    private final EuroService euroService;
    private final Pizza[] pizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false),
            new Pizza(4,"Quattro Stagione", BigDecimal.valueOf(5),false),
            new Pizza(4,"Margherita Mini", BigDecimal.valueOf(3),false),
            new Pizza(5,"Spicy Pizza", BigDecimal.valueOf(5), true),
            new Pizza(6,"Hawai",BigDecimal.valueOf(4),false),
            new Pizza(7,"Vegeteriano", BigDecimal.valueOf(4),false),
            new Pizza(8,"Vegetariano Picante",BigDecimal.valueOf(4),true)
    };

    public PizzaController(EuroService euroService) {
        this.euroService = euroService;
    }

    @GetMapping
    public ModelAndView pizzas() {
        return new ModelAndView("pizzas", "pizzas", pizzas);
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id) {
        var modelAndView = new ModelAndView("pizza");
        Arrays.stream(pizzas).filter(pizza -> pizza.getId() == id).findFirst()
                .ifPresent(pizza -> {
                    modelAndView.addObject("pizza", pizza);
                    try {
                        modelAndView.addObject(
                                "inDollar", euroService.naarDollar(pizza.getPrijs()));
                    } catch (KoersClientException ex) {
// Hier komt later code om de exception te verwerken.
                    }
                });
        return modelAndView;
    }

    private List<BigDecimal> uniekePrijzen() {
        return Arrays.stream(pizzas).map(pizza -> pizza.getPrijs())
                .distinct().sorted().collect(Collectors.toList());
    }
    @GetMapping("prijzen")
    public ModelAndView prijzen() {
        return new ModelAndView("prijzen", "prijzen", uniekePrijzen());
    }

    private List<Pizza> pizzasMetPrijs(BigDecimal prijs) {
        return Arrays.stream(pizzas)
                .filter(pizza -> pizza.getPrijs().compareTo(prijs) == 0)
                .collect(Collectors.toList());
    }
    @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
        var modelAndView = new ModelAndView("prijzen","pizzas",pizzasMetPrijs(prijs)).
                addObject("prijzen", uniekePrijzen());
        return modelAndView;
    }
}
