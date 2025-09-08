package com.example.trabalho.controller;

import com.example.trabalho.model.Tarefa;
import com.example.trabalho.repository.TarefaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    private final TarefaRepository repository;

    public TarefaController(TarefaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @GetMapping
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        return repository.findById(id)
                .map(t -> {
                    t.setNome(tarefa.getNome());
                    t.setDataEntrega(tarefa.getDataEntrega());
                    t.setResponsavel(tarefa.getResponsavel());
                    return ResponseEntity.ok(repository.save(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return repository.findById(id)
                .map(t -> {
                    repository.delete(t);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
