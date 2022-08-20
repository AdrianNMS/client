package com.bank.client.models.services;

import com.bank.client.models.utils.ResponsePasive;
import reactor.core.publisher.Mono;

public interface IPasiveService {
    Mono<ResponsePasive> ExistByClientIdType(Integer type, String idClient);
}
