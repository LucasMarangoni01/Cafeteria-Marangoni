package senai499.com.br.Cafe_Marangoni.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senai499.com.br.Cafe_Marangoni.model.Usuario;
import senai499.com.br.Cafe_Marangoni.repository.UsuarioRepository;

import java.util.Locale;
import java.util.Map;

@Controller
public class AuthController {

    public record CadastroReq(String nome, String username, String email, String senha) {}

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioRepository usuarios, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.encoder = encoder;
    }

    @PostMapping("/api/usuarios")
    @ResponseBody
    public ResponseEntity<?> cadastrar(@RequestBody CadastroReq req) {
        if (req == null || vazio(req.nome()) || vazio(req.username()) || vazio(req.email()) || vazio(req.senha())) {
            return erro(400, "Preencha todos os campos.");
        }
        String username = req.username().trim().toLowerCase(Locale.ROOT);
        String email = req.email().trim().toLowerCase(Locale.ROOT);
        if (!username.matches("^[a-z0-9._-]{3,30}$")) {
            return erro(400, "Usuário: 3 a 30 caracteres (letras, números, ponto, hífen ou sublinhado).");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return erro(400, "Informe um e-mail válido.");
        }
        if (req.senha().length() < 8 || req.senha().length() > 72) {
            return erro(400, "A senha deve ter de 8 a 72 caracteres.");
        }
        if (usuarios.existsByUsername(username)) return erro(409, "Este nome de usuário já está em uso.");
        if (usuarios.existsByEmail(email)) return erro(409, "Este e-mail já está cadastrado.");

        Usuario u = new Usuario();
        u.setNome(req.nome().trim());
        u.setUsername(username);
        u.setEmail(email);
        u.setSenhaHash(encoder.encode(req.senha()));
        u.setPerfil("CLIENTE");
        usuarios.save(u);
        return ResponseEntity.status(201).body(Map.of("id", u.getId(), "username", u.getUsername()));
    }

    @PostMapping("/login")
    public String entrar(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        Usuario u = usuarios.findByUsername(username.trim().toLowerCase(Locale.ROOT)).orElse(null);
        if (u == null || !encoder.matches(password, u.getSenhaHash())) {
            return "redirect:/login?erro";
        }
        HttpSession session = request.getSession();
        request.changeSessionId(); // evita fixação de sessão
        session.setAttribute("usuarioId", u.getId());
        session.setAttribute("perfil", u.getPerfil());
        return "redirect:" + ("ADMIN".equals(u.getPerfil()) ? "/admin" : "/pedidos");
    }

    @PostMapping("/logout")
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/api/me")
    @ResponseBody
    public ResponseEntity<?> eu(HttpSession session) {
        Long id = (Long) session.getAttribute("usuarioId");
        if (id == null) return erro(401, "Não autenticado.");
        return usuarios.findById(id)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of("nome", u.getNome(), "perfil", u.getPerfil())))
                .orElseGet(() -> erro(401, "Não autenticado."));
    }

    private static boolean vazio(String s) { return s == null || s.isBlank(); }

    private static ResponseEntity<?> erro(int status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("mensagem", mensagem));
    }
}