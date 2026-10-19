package senai499.com.br.Cafe_Marangoni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/index.html"}) public String home() { return "index"; }
    @GetMapping({"/produtos", "/produtos.html"}) public String products() { return "produtos"; }
    @GetMapping({"/pedidos", "/pedidos.html"}) public String orders() { return "pedidos"; }
    @GetMapping({"/login", "/login.html"}) public String login() { return "login"; }
    @GetMapping({"/cadastro", "/cadastro.html"}) public String register() { return "cadastro"; }
    @GetMapping({"/admin", "/admin.html"}) public String admin() { return "admin"; }
}
