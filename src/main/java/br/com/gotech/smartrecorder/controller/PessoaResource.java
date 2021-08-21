package br.com.gotech.smartrecorder.controller;

import br.com.gotech.smartrecorder.entity.PessoaEntity;
import br.com.gotech.smartrecorder.entity.business.BusinessPessoaAutenticada;
import br.com.gotech.smartrecorder.repository.PessoaRepository;
import br.com.gotech.smartrecorder.repository.PessoaRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    PessoaRepositoryImpl pessoaRepositoryImpl;

    @GetMapping
    public List<PessoaEntity> listar(){
        return pessoaRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public PessoaEntity buscar(@PathVariable Long codigo){
        return pessoaRepository.findById(codigo).get();
    }

    @PostMapping("/login")
    public BusinessPessoaAutenticada login(@RequestBody PessoaEntity pessoaEntity) {

        BusinessPessoaAutenticada pessoaAutenticada = pessoaRepositoryImpl.infosByEmailAndPassword(pessoaEntity.getEmail(), pessoaEntity.getPassword());

        if(pessoaAutenticada == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário ou senha inválidos");
        }

        return pessoaAutenticada;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PessoaEntity cadastrar(@RequestBody PessoaEntity pessoa){
        return  pessoaRepository.save(pessoa);
    }

    @PutMapping("/{id}")
    public PessoaEntity atualizar(@RequestBody PessoaEntity pessoa, @PathVariable Long id){
        pessoa.setCdPessoa(id);
        return pessoaRepository.save(pessoa);
    }

    @DeleteMapping("/{codigo}")
    public void remover(@PathVariable Long codigo) {
        pessoaRepository.deleteById(codigo);
    }

}
