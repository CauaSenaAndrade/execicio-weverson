package com.uniceplac.atividade.controller;

import com.uniceplac.atividade.dto.UsuarioCadastroDTO;
import com.uniceplac.atividade.model.Usuario;
import com.uniceplac.atividade.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<UsuarioResposta> criarUsuario(@RequestBody UsuarioCadastroDTO dados) {

        // Regra simples: não aceitar e-mail repetido
        if (repository.existsByEmail(dados.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // DTO -> Entidade: só nome, email e senha passam adiante
        Usuario novo = new Usuario(dados.nome(), dados.email(), dados.senha());
        Usuario salvo = repository.save(novo);

        // Entidade -> resposta, sem a senha
        UsuarioResposta resposta = new UsuarioResposta(salvo.getId(), salvo.getNome(), salvo.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    public record UsuarioResposta(Long id, String nome, String email) {
    }
}
