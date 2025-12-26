package clinica.api.controller;

import clinica.api.model.Usuario;
import clinica.api.model.dto.CadastroUsuarioDTO;
import clinica.api.model.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    //@CrossOrigin(origins = "*")
    @PostMapping("/valida-login")
    public ResponseEntity validaLogin(@RequestBody @Valid Usuario usuario) {
        var usuarioBusca = usuarioRepository.findByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
        if (usuarioBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(usuarioBusca);
        }
    }

    @PostMapping
    @Transactional
    public void cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDTO dados) {
        usuarioRepository.save(new Usuario(dados));
    }


}
