package com.darkoum.darkoum.service.implementations;

import com.darkoum.darkoum.dtos.request.ClientDtoRequest;
import com.darkoum.darkoum.dtos.response.ClientDtoResponse;
import com.darkoum.darkoum.model.Client;
import com.darkoum.darkoum.model.User;
import com.darkoum.darkoum.repository.ClientRepository;
import com.darkoum.darkoum.repository.UserRepository;
import com.darkoum.darkoum.service.interfaces.ClientServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService implements ClientServiceInterface {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public ClientDtoResponse createClient(ClientDtoRequest clientDtoRequest) {

        Client client = new Client();
        client.setName(clientDtoRequest.getName());
        client.setEmail(clientDtoRequest.getEmail());
        client.setPhoneNumber(clientDtoRequest.getPhoneNumber());
        client.setAddress(clientDtoRequest.getAddress());
        client.setCin(clientDtoRequest.getCin());
        client.setCodeClient(clientDtoRequest.getCodeClient());
        client.setDesignation(clientDtoRequest.getDesignation());
        client.setIce(clientDtoRequest.getIce());
        client.setRc(clientDtoRequest.getRc());
        Client savedClient = clientRepository.save(client);
        return mapToDto(savedClient);
    }
    @Override
    public ClientDtoResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return mapToDto(client);
    }

    @Override
    public Page<ClientDtoResponse> getAllClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  clientRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Override
    public Page<ClientDtoResponse> searchClientsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToDto);
    }

    @Override
    @Transactional
    public ClientDtoResponse updateClient(Long id, ClientDtoRequest clientDtoRequest) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setName(clientDtoRequest.getName());
        client.setEmail(clientDtoRequest.getEmail());
        client.setPhoneNumber(clientDtoRequest.getPhoneNumber());
        client.setAddress(clientDtoRequest.getAddress());
        client.setCin(clientDtoRequest.getCin());
        client.setCodeClient(clientDtoRequest.getCodeClient());
        client.setDesignation(clientDtoRequest.getDesignation());
        client.setIce(clientDtoRequest.getIce());
        client.setRc(clientDtoRequest.getRc());


        Client updatedClient =   clientRepository.save(client);
        return mapToDto(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        clientRepository.delete(client);
    }
    private ClientDtoResponse mapToDto(Client client) {
        ClientDtoResponse dto = new ClientDtoResponse();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setAddress(client.getAddress());
        dto.setCin(client.getCin());
        dto.setCodeClient(client.getCodeClient());
        dto.setDesignation(client.getDesignation());
        dto.setIce(client.getIce());
        dto.setRc(client.getRc());
       
        return dto;
    }
    @Override
    public List<String> getAllClientNames() {
        return clientRepository.findAllClientNames();
    }
}