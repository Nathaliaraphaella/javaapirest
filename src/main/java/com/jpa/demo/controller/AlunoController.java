package com.jpa.demo.controller;

import com.jpa.demo.repository.AlunoRepository;
import com.jpa.demo.dto.AlunoRecord;
import com.jpa.demo.model.Aluno;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno") // Define o endpoint da aplicação, um endereco utilizado para comunicação entre uma API e um sistema externo.
public class AlunoController {
    @Autowired // Injeção de dependêcia, permite que o Spring controle as instância da classe AlunoRepository
    private AlunoRepository alunoRepository;
    public AlunoController(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }

    @PostMapping // Método POST
    // ResponseEntity representa toda a resposta HTTP
    // @RequestBody mapeia o corpo HttpRequest para um objeto de transferência
    public ResponseEntity<Aluno> salvaAluno(@RequestBody AlunoRecord alunoRecord){
        Aluno alunoObj = new Aluno();
        
        alunoObj.setCpf(alunoRecord.cpf());
        alunoObj.setEmail(alunoRecord.email());
        alunoObj.setName(alunoRecord.name());
        alunoObj.setPassword(alunoRecord.password());
        
        alunoRepository.save(alunoObj); // invoca o método save para armazenar o objeto no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoObj); // Retorna o status da resposta HTTP após a compilação
    }
    
    @GetMapping
    public ResponseEntity<Aluno> buscarAluno(@RequestParam String cpf){
    	Aluno alunoObj = alunoRepository.getByCpf(cpf);
    	
    	return ResponseEntity.ok().body(alunoObj);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deletaAluno(@RequestParam String cpf){
    	Aluno alunoObj = alunoRepository.getByCpf(cpf);
    	
    	alunoRepository.deleteById(alunoObj.getId());
    	return ResponseEntity.ok().body("Aluno deletado com sucesso");
    }
}