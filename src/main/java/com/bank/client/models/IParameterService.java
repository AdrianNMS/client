package com.bank.client.models;

import com.bank.client.models.utils.ResponseParameter;
import reactor.core.publisher.Mono;

public interface IParameterService {

    Mono<ResponseParameter> findByCode(Integer clientType, Integer code);
}
