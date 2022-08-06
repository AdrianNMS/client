package com.bank.client.models.dao;

import com.bank.client.models.documents.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface ClientDao extends ReactiveMongoRepository<Client, String>{
}
