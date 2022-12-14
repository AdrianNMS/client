package com.bank.client.models.services.impl;

import com.bank.client.models.services.IMovementService;
import com.bank.client.models.utils.ResponseMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MovementImpl implements IMovementService
{
    @Qualifier("getWebClientMovement")
    @Autowired
    WebClient webClient;

    @Override
    public Mono<ResponseMovement> getBalance(String id)
    {
        return webClient.get()
                .uri("/api/movement/balance/"+ id)
                .retrieve()
                .bodyToMono(ResponseMovement.class);
    }
}
