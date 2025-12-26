package clinica.api.controller;

import clinica.api.model.*;
import clinica.api.model.dto.AtualizacaoMedicoDTO;
import clinica.api.model.dto.CadastroMedicoDTO;
import clinica.api.model.dto.ListagemMedicoDTO;
import clinica.api.model.repository.MedicoRepository;
import clinica.api.model.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("medicos")
public class MedicoController {


    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarMedico(@RequestBody @Valid CadastroMedicoDTO dados) {
        if (dados == null) {
            log.info("Dados de médico estão nulos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados de médico estão nulos");
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(dados.login());
        usuario.setSenha(dados.senha());
        usuario.setMedico(true);
        Usuario usuarioInserido = usuarioRepository.save(usuario);

        medicoRepository.save(new Medico(dados,usuarioInserido.getId()));
        return ResponseEntity.ok("Médico cadastrado com sucesso!");
    }

    @GetMapping
    public Page<ListagemMedicoDTO> listarMedico(@PageableDefault(size = 30, sort = {"nome"}) Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(ListagemMedicoDTO::new);
    }

    @PutMapping
    @Transactional
    public void atualizarMedico(@RequestBody @Valid AtualizacaoMedicoDTO dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        medicoRepository.save(medico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> excluirMedico(@PathVariable Long id) {
        try {
            var medico = medicoRepository.getReferenceById(id);
            medico.excluir();
            medicoRepository.save(medico);
            return ResponseEntity.ok().body("Médico excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o médico.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable Long id) {
        try {
            var medico = medicoRepository.findById(id);
            if (medico.isPresent()) {
                return ResponseEntity.ok().body(medico.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Medico> getMedicoByNome(@PathVariable String nome) {
        try {
            // Busca um médico pelo nome
            Optional<Medico> medico = medicoRepository.findByNome(nome);

            // Verifica se o médico foi encontrado
            if (medico.isPresent()) {
                return ResponseEntity.ok().body(medico.get());  // Retorna o médico encontrado
            } else {
                return ResponseEntity.notFound().build();  // Retorna 404 se o médico não for encontrado
            }
        } catch (Exception e) {
            // Se ocorrer algum erro, retorna 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/crm/{crm}")
    public ResponseEntity<Medico> getMedicoByCrm(@PathVariable String crm) {
        try {
            Optional<Medico> medico = medicoRepository.findByCrm(crm);

            if (medico.isPresent()) {
                return ResponseEntity.ok().body(medico.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
