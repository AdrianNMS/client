package com.bank.client.models.services;

import com.bank.client.models.documents.Client;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IClientService
{
    Mono<List<Client>> FindAll();
    Mono<Client> Find(String id);
    Mono<Client> Create(Client client);
    Mono<Client> Update(String id, Client client);
    Mono<Object> Delete(String id);
}
