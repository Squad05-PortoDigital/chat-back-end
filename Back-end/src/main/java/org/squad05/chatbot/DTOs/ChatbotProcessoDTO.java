package org.squad05.chatbot.DTOs;

import java.time.LocalDate;

public class ChatbotProcessoDTO {
    private Long id_funcionario;
    private String tipo_processo;
    private LocalDate data_solicitacao;
    private LocalDate data_ocorrencia;
    private String status = "Pendente";
    private String descricao;
    private String urgencia;
    private Long id_destinatario;
    //Opcionais
    private String hora_extra;
    private LocalDate inicio_ferias;
    private LocalDate fim_ferias;
    private String beneficio;
    private String nome_documento;
    private String nome_arquivo;

    //Getters e Setters
    public Long getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Long id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getTipo_processo() {
        return tipo_processo;
    }

    public void setTipo_processo(String tipo_processo) {
        this.tipo_processo = tipo_processo;
    }

    public LocalDate getData_solicitacao() {
        return data_solicitacao;
    }

    public void setData_solicitacao(LocalDate data_solicitacao) {
        this.data_solicitacao = data_solicitacao;
    }

    public LocalDate getData_ocorrencia() {
        return data_ocorrencia;
    }

    public void setData_ocorrencia(LocalDate data_ocorrencia) {
        this.data_ocorrencia = data_ocorrencia;
    }

    public String getHora_extra() {
        return hora_extra;
    }

    public void setHora_extra(String hora_extra) {
        this.hora_extra = hora_extra;
    }

    public LocalDate getInicio_ferias() {
        return inicio_ferias;
    }

    public void setInicio_ferias(LocalDate inicio_ferias) {
        this.inicio_ferias = inicio_ferias;
    }

    public LocalDate getFim_ferias() {
        return fim_ferias;
    }

    public void setFim_ferias(LocalDate fim_ferias) {
        this.fim_ferias = fim_ferias;
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public String getNome_documento() {
        return nome_documento;
    }

    public void setNome_documento(String nome_documento) {
        this.nome_documento = nome_documento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

    public Long getId_destinatario() {
        return id_destinatario;
    }

    public void setId_destinatario(Long id_destinatario) {
        this.id_destinatario = id_destinatario;
    }

    public String getNome_arquivo() {
        return nome_arquivo;
    }

    public void setNome_arquivo(String caminho_arquivo) {
        this.nome_arquivo = caminho_arquivo;
    }
}
