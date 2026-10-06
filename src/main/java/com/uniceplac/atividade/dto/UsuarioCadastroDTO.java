package com.uniceplac.atividade.dto;

// Só carrega o que o cliente pode enviar no cadastro.
// Sem "id" e sem "administrador": esses campos não existem aqui.
public record UsuarioCadastroDTO(String nome, String email, String senha) {
}
