package be.vdab.luigi.controllers;

import be.vdab.luigi.Services.EuroService;
import be.vdab.luigi.Services.PizzaService;
import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.forms.VanTotPrijsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pizzas")
public class PizzaController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EuroService euroService;
    private final PizzaService pizzaService;


    public PizzaController(EuroService euroService, PizzaService pizzaService) {       this.euroService = euroService;
        this.pizzaService = pizzaService;
    }



    /*private final Pizza[] pizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false),
            new Pizza(4,"Quattro Stagione", BigDecimal.valueOf(5),false),
            new Pizza(4,"Margherita Mini", BigDecimal.valueOf(3),false),
            new Pizza(5,"Spicy Pizza", BigDecimal.valueOf(5), true),
            new Pizza(6,"Hawai",BigDecimal.valueOf(4),false),
            new Pizza(7,"Vegeteriano", BigDecimal.valueOf(4),false),
            new Pizza(8,"Vegetariano Picante",BigDecimal.valueOf(4),true)
    };*/

    @GetMapping
    public ModelAndView pizzas() {
        return new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id) {
        var modelAndView = new ModelAndView("pizza");
        pizzaService.findById(id).ifPresent(pizza -> {
            modelAndView.addObject(pizza);
            modelAndView.addObject("inDollar",euroService.naarDollar(pizza.getPrijs()));
        });
        return modelAndView;
    }


    @GetMapping("prijzen")
    public ModelAndView prijzen() {
        return new ModelAndView("prijzen", "prijzen", pizzaService.findUniekePrijzen());
    }

    @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
        return new ModelAndView("prijzen","pizzas", pizzaService.findByPrijs(prijs))
                .addObject("prijzen", pizzaService.findUniekePrijzen());
    }
    @GetMapping("vantotprijs/form")
    public ModelAndView vanTotPrijsForm() {
        return new ModelAndView("vantotprijs")
                .addObject(new VanTotPrijsForm(BigDecimal.ONE, BigDecimal.TEN));
    }
    @GetMapping("vantotprijs")
    public ModelAndView vanTotPrijs(VanTotPrijsForm form, Errors errors) {
        var modelAndView = new ModelAndView("vantotprijs");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("pizzas",
                pizzaService.findByPrijsBetween(form.getVan(), form.getTot()));
    }
    @GetMapping("toevoegen/form")
    public ModelAndView toevoegenForm() {
        return new ModelAndView("toevoegen").addObject(new Pizza(0,"",null,false));
    }
    @PostMapping
    public ModelAndView toevoegen(@Valid Pizza pizza, Errors errors){
        if (errors.hasErrors()){
            return new ModelAndView("toevoegen");
        }
        pizzaService.create(pizza);
        return new ModelAndView("pizzas","pizzas",pizzaService.findAll());
    }
}
