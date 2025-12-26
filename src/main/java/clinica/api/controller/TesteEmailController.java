package clinica.api.controller;

import clinica.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("teste-email")
public class TesteEmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> statusEmail() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "Sistema de email configurado");
        status.put("timestamp", LocalDateTime.now());
        status.put("endpoints_disponiveis", new String[]{
            "GET /teste-email/status - Status do sistema",
            "POST /teste-email/simples - Enviar email simples",
            "POST /teste-email/template - Testar template HTML"
        });
        return ResponseEntity.ok(status);
    }

    @PostMapping("/simples")
    public ResponseEntity<Map<String, Object>> testarEmailSimples(@RequestParam String destinatario) {
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            String assunto = "Teste de Email - API Clínica";
            String corpo = "Este é um email de teste enviado em: " + LocalDateTime.now() + "\n\n" +
                         "Se você recebeu este email, o sistema está funcionando corretamente!";
            
            emailService.enviarEmailSimples(destinatario, assunto, corpo);
            
            resultado.put("sucesso", true);
            resultado.put("mensagem", "Email de teste enviado com sucesso!");
            resultado.put("destinatario", destinatario);
            resultado.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            resultado.put("sucesso", false);
            resultado.put("erro", e.getMessage());
            resultado.put("timestamp", LocalDateTime.now());
        }
        
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/template")
    public ResponseEntity<Map<String, Object>> testarTemplate(@RequestParam String destinatario) {
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            // Criar dados fictícios para teste
            // Nota: Este é apenas um teste básico - em produção, você usaria dados reais
            String assunto = "Teste de Template - API Clínica";
            String corpo = "<html><body>" +
                         "<h1>Teste de Template HTML</h1>" +
                         "<p>Este é um teste do sistema de templates de email.</p>" +
                         "<p>Enviado em: " + LocalDateTime.now() + "</p>" +
                         "</body></html>";
            
            // Para este teste, vamos usar o método simples com HTML
            emailService.enviarEmailSimples(destinatario, assunto, corpo);
            
            resultado.put("sucesso", true);
            resultado.put("mensagem", "Template de teste enviado!");
            resultado.put("destinatario", destinatario);
            resultado.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            resultado.put("sucesso", false);
            resultado.put("erro", e.getMessage());
            resultado.put("timestamp", LocalDateTime.now());
        }
        
        return ResponseEntity.ok(resultado);
    }
}
