# 🔧 Correção do Sistema de Email

## Problemas Identificados e Corrigidos

### 1. ❌ **Endereço "From" Ausente**
   - **Problema**: Os emails não tinham um endereço remetente configurado
   - **Solução**: Adicionado suporte para configuração via `application.properties`

### 2. ❌ **Credenciais de Placeholder**
   - **Problema**: `application.properties` tinha valores de exemplo
   - **Solução**: Configurado para usar variáveis de ambiente

## 📋 Configuração Necessária

### Passo 1: Configure suas Credenciais do Gmail

1. **Habilite a verificação em duas etapas** na sua conta Google
   - Acesse: https://myaccount.google.com/security

2. **Gere uma senha de app**:
   - Acesse: https://myaccount.google.com/apppasswords
   - Selecione "Mail" e "Outro (nome personalizado)"
   - Digite "API Clínica" como nome
   - Copie a senha gerada (16 caracteres sem espaços)

### Passo 2: Configure o application.properties

**Opção A: Variáveis de Ambiente (Recomendado)**

```properties
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
spring.mail.from=${EMAIL_FROM}
```

Defina as variáveis antes de iniciar a aplicação:
```bash
# Windows PowerShell
$env:EMAIL_USERNAME="seu-email@gmail.com"
$env:EMAIL_PASSWORD="senha-de-app-gerada"
$env:EMAIL_FROM="seu-email@gmail.com"

# Linux/Mac
export EMAIL_USERNAME="seu-email@gmail.com"
export EMAIL_PASSWORD="senha-de-app-gerada"
export EMAIL_FROM="seu-email@gmail.com"
```

**Opção B: Direto no application.properties**

Edite o arquivo `api/api/src/main/resources/application.properties`:

```properties
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.from=seu-email@gmail.com
```

⚠️ **ATENÇÃO**: Nunca commite suas credenciais no código!

## 🧪 Testando o Sistema

### Teste 1: Verificar Status
```bash
GET http://localhost:8080/teste-email/status
```

### Teste 2: Enviar Email Simples
```bash
POST http://localhost:8080/teste-email/simples?destinatario=seu-email@exemplo.com
```

### Teste 3: Testar Template HTML
```bash
POST http://localhost:8080/teste-email/template?destinatario=seu-email@exemplo.com
```

## 📝 Alterações Realizadas

### 1. `EmailService.java`
- ✅ Adicionado import de `MailConfig`
- ✅ Adicionado campo `@Autowired private MailConfig mailConfig`
- ✅ Adicionado `helper.setFrom()` em todos os métodos de envio
- ✅ Usa `mailConfig.getDefaultFromAddress()` para o endereço remetente

### 2. `MailConfig.java` (NOVO)
- ✅ Criada classe de configuração para gerenciar o endereço "From"
- ✅ Lê valor de `spring.mail.from` do `application.properties`
- ✅ Valor padrão: `noreply@clinica.com`

### 3. `application.properties`
- ✅ Adicionado suporte para variáveis de ambiente
- ✅ Adicionada propriedade `spring.mail.from`

## 🔍 Verificando se Está Funcionando

1. **Inicie a aplicação**
2. **Verifique os logs** - não deve haver erros relacionados ao JavaMailSender
3. **Teste o envio** usando o endpoint `/teste-email/simples`
4. **Verifique sua caixa de entrada** (e spam)

## ❗ Problemas Comuns

### Erro: "Authentication failed"
- Verifique se a senha de app está correta
- Confirme se a verificação em duas etapas está ativada
- Certifique-se de que não há espaços na senha de app

### Erro: "Could not connect to SMTP host"
- Verifique sua conexão com a internet
- Verifique se o firewall não está bloqueando a conexão
- Teste com outro provedor de email

### Erro: "Mail sending failed"
- Verifique se o endereço de email do destinatário é válido
- Verifique se você não excedeu o limite de emails do Gmail
- Verifique se o endereço "From" está configurado corretamente

## 🚀 Pronto para Produção

Após confirmar que os testes estão funcionando, você pode usar o sistema de notificações:

- ✅ Emails são enviados automaticamente ao agendar consultas
- ✅ Emails são enviados automaticamente ao cancelar consultas
- ✅ Lembretes automáticos são enviados diariamente (9:00 AM)
- ✅ Lembretes semanais são enviados (Segundas-feiras, 8:00 AM)

## 📞 Suporte

Se continuar tendo problemas, verifique:
1. Os logs da aplicação no console
2. A configuração do firewall
3. As credenciais do Gmail
4. A conexão com a internet

