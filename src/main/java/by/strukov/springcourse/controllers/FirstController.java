package by.strukov.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname) {
        System.out.println("Hello, " + name + surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage() {
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public void calcPage(@RequestParam(value = "a") Double a,
                         @RequestParam(value = "b") Double b,
                         @RequestParam(value = "action") String action,
                         Model model) {
        switch (action) {
            case "addition" -> model.addAttribute("result", a + b);
            case "subtraction" -> model.addAttribute("result", a - b);
            case "multiplication" -> model.addAttribute("result", a * b);
            case "division" -> model.addAttribute("result", a / b);
            case null, default -> model.addAttribute("result", null);
        }
    }
}
