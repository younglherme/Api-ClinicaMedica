# Configuração de Email - API Clínica

## Visão Geral
Este documento explica como configurar e usar o sistema de notificações por email da API Clínica.

## Configuração Inicial

### 1. Configuração do Gmail (Recomendado)

1. **Habilite a verificação em duas etapas** na sua conta Google
2. **Gere uma senha de app**:
   - Acesse: https://myaccount.google.com/apppasswords
   - Selecione "Mail" e "Outro (nome personalizado)"
   - Digite "API Clínica" como nome
   - Copie a senha gerada (16 caracteres)

3. **Atualize o application.properties**:
```properties
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app-gerada
```

### 2. Outros Provedores de Email

#### Outlook/Hotmail
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=seu-email@outlook.com
spring.mail.password=sua-senha
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

#### Yahoo
```properties
spring.mail.host=smtp.mail.yahoo.com
spring.mail.port=587
spring.mail.username=seu-email@yahoo.com
spring.mail.password=sua-senha
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Funcionalidades Disponíveis

### 1. Emails Automáticos
- **Confirmação de Agendamento**: Enviado automaticamente quando uma consulta é agendada
- **Cancelamento**: Enviado quando uma consulta é cancelada
- **Lembretes Automáticos**: Enviados diariamente às 9:00 para consultas do dia seguinte

### 2. Endpoints de Notificação

#### Testar Email
```http
POST /notificacoes/teste-email?destinatario=teste@email.com
```

#### Enviar Lembrete Específico
```http
POST /notificacoes/lembrete/{idConsulta}
```

#### Enviar Email Simples
```http
POST /notificacoes/email-simples?destinatario=email@exemplo.com&assunto=Assunto&corpo=Corpo do email
```

#### Lembretes Automáticos (Manual)
```http
POST /notificacoes/lembretes-automaticos
```

### 3. Templates de Email

Os templates estão localizados em `src/main/resources/templates/`:
- `email-agendamento.html` - Confirmação de agendamento
- `email-cancelamento.html` - Cancelamento de consulta
- `email-lembrete.html` - Lembrete de consulta

## Configuração de Agendamento

### Lembretes Diários
- **Horário**: Todos os dias às 9:00
- **Função**: Envia lembretes para consultas do dia seguinte

### Lembretes Semanais
- **Horário**: Segundas-feiras às 8:00
- **Função**: Envia lembretes para consultas da semana

## Solução de Problemas

### Erro: "Authentication failed"
- Verifique se a senha de app está correta
- Confirme se a verificação em duas etapas está ativada
- Teste com um email simples primeiro

### Erro: "Connection refused"
- Verifique as configurações de host e porta
- Confirme se o firewall não está bloqueando a conexão

### Emails não chegam
- Verifique a pasta de spam
- Confirme se o endereço de email está correto
- Teste com o endpoint de teste primeiro

## Testando a Configuração

1. **Inicie a aplicação**
2. **Teste com email simples**:
```bash
curl -X POST "http://localhost:8080/notificacoes/teste-email?destinatario=seu-email@exemplo.com"
```

3. **Verifique os logs** para confirmar o envio

## Segurança

- **Nunca** commite senhas no código
- Use variáveis de ambiente para configurações sensíveis
- Considere usar um serviço de email dedicado para produção

## Variáveis de Ambiente (Recomendado)

```bash
# application.properties
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
```

```bash
# .env ou variáveis do sistema
EMAIL_USERNAME=seu-email@gmail.com
EMAIL_PASSWORD=sua-senha-de-app
```
