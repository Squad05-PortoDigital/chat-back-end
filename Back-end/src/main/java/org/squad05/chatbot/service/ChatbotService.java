package org.squad05.chatbot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.squad05.chatbot.DTOs.ChatbotProcessoDTO;
import org.squad05.chatbot.models.ChatbotProcesso;
import org.squad05.chatbot.models.Funcionario;
import org.squad05.chatbot.repositories.ChatbotRepository;

@Service
public class ChatbotService {

    @Autowired
    private ChatbotRepository chatbotRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //Configurações da funão de e-mail
    @Value("${email.host}")
    private String host;

    @Value("${email.user}")
    private String user;

    @Value("${email.password}")
    private String password;

    @Value("${email.port}")
    private String port;

    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de arquivos.");
        }
    }

    //Mapeamento para evitar repetição de código!
    private void mapearDTOParaProcesso(ChatbotProcesso processo, ChatbotProcessoDTO processoDTO) {
        processo.setTipo_processo(processoDTO.getTipo_processo());
        processo.setData_solicitacao(processoDTO.getData_solicitacao());
        processo.setStatus(processoDTO.getStatus());
        processo.setDescricao(processoDTO.getDescricao());
        processo.setUrgencia(processo.getUrgencia());
        processo.setId_destinatario(processoDTO.getId_destinatario());
        processo.setCaminho_arquivo(processoDTO.getCaminho_arquivo());
    }

    //Criar um processo
    public ChatbotProcesso criarProcesso(ChatbotProcessoDTO chatbotProcessoDTO) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(chatbotProcessoDTO.getId_funcionario());

        ChatbotProcesso chatbotProcesso = new ChatbotProcesso();
        chatbotProcesso.setId_funcionario(funcionario);

        mapearDTOParaProcesso(chatbotProcesso, chatbotProcessoDTO);

        return chatbotRepository.save(chatbotProcesso);
    }

    //Atualizar um processo
    public ChatbotProcesso atualizarProcesso(Long id, ChatbotProcessoDTO dadosAtualziados) {
        ChatbotProcesso processo = buscarProcessoPorId(id);

        mapearDTOParaProcesso(processo,dadosAtualziados);

        return chatbotRepository.save(processo);
    }

    //Buscar processo por ID
    public ChatbotProcesso buscarProcessoPorId(Long id) {
        return chatbotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));
    }

    //Deletar um processo
    public void deletarProcesso(Long id) {
        ChatbotProcesso processo = buscarProcessoPorId(id);
        chatbotRepository.delete(processo);
    }

    //Listar todos os processos
    public List<ChatbotProcesso> listarTodosProcessos() {
        return chatbotRepository.findAll();
    }

    //Upload de arquvios
    public String enviarArquivo(MultipartFile file, Long processoId) throws Exception {

        //Cria o diretório se não existir
        Files.createDirectories(Paths.get(uploadDir));
        //Salvando o arquivo no sistema
        Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        ChatbotProcesso processo = buscarProcessoPorId(processoId);
        processo.setCaminho_arquivo(filePath.toString());
        chatbotRepository.save(processo);

        return "Arquivo enviado com sucesso: " + filePath.toString();
    }

    //Envio de e-mail
    public void enviarEmail(String to, String subject, String body) throws Exception {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.starttls.enable", "true");

        // Criação da sessão de e-mail
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            // Criação da mensagem de e-mail
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // Envio da mensagem
            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
