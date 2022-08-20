package com.bank.client.models.services;

import com.bank.client.models.utils.ResponseActive;
import reactor.core.publisher.Mono;

public interface IActiveService
{
    Mono<ResponseActive> checkCreditCard(String id, Integer typeCreditCard);
}
