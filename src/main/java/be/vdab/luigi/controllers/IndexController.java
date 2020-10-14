package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import be.vdab.luigi.domain.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/")
class IndexController {
    private final AtomicInteger aantalKerenBekeken = new AtomicInteger();
    @GetMapping
    public ModelAndView index(){
        var morgenOfMiddag = LocalTime.now().getHour() < 12 ? "morgen" : "middag";
        var modelAndView = new ModelAndView("index","moment",morgenOfMiddag);
        modelAndView.addObject("zaakvoerder",new Persoon("Luigi", "Peperone", 7, true, LocalDate.of(1966, 1, 31),
        new Adres("Grote markt", "3", 9700, "Oudenaarde")));
        modelAndView.addObject("aantalKeerBekeken", aantalKerenBekeken.incrementAndGet());
        return modelAndView;
    }
}
