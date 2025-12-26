package clinica.api.model;

import clinica.api.model.dto.AtualizacaoPacienteDTO;
import clinica.api.model.dto.CadastroPacienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String cpf;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    private String telefone;


    public Paciente(CadastroPacienteDTO dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
        this.telefone = dados.telefone();
    }

    public void atualizarInformacoes(AtualizacaoPacienteDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

        if (dados.cpf() != null || !dados.cpf().isEmpty() || !dados.cpf().isBlank()){
            this.cpf = dados.cpf();
        }
        if (dados.email() != null || !dados.email().isEmpty() || !dados.email().isBlank()){
            this.email = dados.email();
        }

        if (dados.telefone() != null || !dados.telefone().isEmpty() || !dados.telefone().isBlank()){
            this.telefone = dados.telefone();
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
