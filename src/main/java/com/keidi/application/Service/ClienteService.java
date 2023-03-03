package com.keidi.application.Service;

import com.keidi.application.Entity.Cliente;
import com.keidi.application.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Cliente> obterPorId(Long id) {
        return repository.findById(id);
    }

    public void salvar(Cliente cliente) {
        repository.save(cliente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

}