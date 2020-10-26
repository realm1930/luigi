package be.vdab.luigi.controllers;

import be.vdab.luigi.Services.PizzaService;
import be.vdab.luigi.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller @RequestMapping("mandje")
public class MandjeController {
    private final Mandje mandje;
    private final PizzaService pizzaService;

    public MandjeController(Mandje mandje, PizzaService pizzaService) {
        this.mandje = mandje;
        this.pizzaService = pizzaService;
    }
    @PostMapping("{id}")
    public String voegToe(@PathVariable long id){
        mandje.voegToe(id);
        return "redirect:/mandje";
    }
    @GetMapping
    public ModelAndView toonMandje(){
        return new ModelAndView("mandje","pizzas",pizzaService.findByIds(mandje.getIds()));
    }
    @PostMapping("leeg")
    public String maakLeeg(){
        mandje.maakLeeg();
        return "redirect:/mandje";
    }
}
