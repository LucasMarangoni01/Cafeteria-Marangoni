package senai499.com.br.Cafe_Marangoni.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/")         public String inicio()   { return "index"; }
    @GetMapping("/produtos") public String produtos() { return "produtos"; }
    @GetMapping("/pedidos")  public String pedidos()  { return "pedidos"; }
    @GetMapping("/login")    public String login()    { return "login"; }
    @GetMapping("/cadastro") public String cadastro() { return "cadastro"; }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("perfil")) ? "admin" : "redirect:/login";
    }
}