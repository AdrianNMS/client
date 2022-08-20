package com.bank.client.models.services;

import com.bank.client.models.utils.ResponseMovement;
import reactor.core.publisher.Mono;

public interface IMovementService
{
    Mono<ResponseMovement> getBalance(String id);
}
