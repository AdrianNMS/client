package com.bank.client.models.dao;

import com.bank.client.models.documents.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientDao extends ReactiveMongoRepository<Client, String>{
}
