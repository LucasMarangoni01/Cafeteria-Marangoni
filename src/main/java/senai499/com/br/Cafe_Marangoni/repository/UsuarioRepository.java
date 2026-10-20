package senai499.com.br.Cafe_Marangoni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.Cafe_Marangoni.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}