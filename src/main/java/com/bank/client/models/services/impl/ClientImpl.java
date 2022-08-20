package com.bank.client.models.services.impl;

import com.bank.client.models.dao.ClientDao;
import com.bank.client.models.documents.Client;
import com.bank.client.models.services.IClientService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientImpl implements IClientService
{
    @Autowired
    private ClientDao dao;

    @Override
    public Mono<List<Client>> FindAll() {
        return dao.findAll().collectList();
    }

    @Override
    public Mono<Client> Find(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Client> Create(Client client) {
        client.clear();
        client.setClientDataId(new ObjectId().toString());
        client.setDateRegister(LocalDateTime.now());
        return dao.save(client);
    }

    @Override
    public Mono<Client> Update(String id, Client client) {
        return dao.existsById(id).flatMap(check ->
        {
            if (check)
            {
                client.clear();
                client.setDateUpdate(LocalDateTime.now());
                return dao.save(client);
            }
            else
                return Mono.empty();

        });
    }

    @Override
    public Mono<Object> Delete(String id) {
        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.deleteById(id).then(Mono.just(true));
            else
                return Mono.empty();
        });
    }
}
