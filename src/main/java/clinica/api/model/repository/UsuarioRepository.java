package clinica.api.model.repository;

import clinica.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLoginAndSenha(String login, String senha);
}
