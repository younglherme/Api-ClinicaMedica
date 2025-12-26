package clinica.api.model;

import clinica.api.model.dto.AtualizacaoMedicoDTO;
import clinica.api.model.dto.CadastroMedicoDTO;
import clinica.api.model.enums.Especialidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    private Long idusuario;

    public Medico(CadastroMedicoDTO dados, Long idUsuario) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.idusuario = idUsuario;
        this.telefone = dados.telefone();
    }

    public void atualizarInformacoes(AtualizacaoMedicoDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }

        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

        if(dados.especialidade() != null){
            this.especialidade = dados.especialidade();
        }

        if (dados.telefone() != null) {
            this.nome = dados.nome();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
