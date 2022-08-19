package com.bank.client.models.impl;

import com.bank.client.models.IParameterService;
import com.bank.client.models.utils.ResponseParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class ParameterImpl implements IParameterService {


    @Autowired
    @Qualifier("getWebClientParameter")
    WebClient webClient;

    @Override
    public Mono<ResponseParameter> findByCode(Integer clientType, Integer code)
    {
        return webClient.get()
                .uri("/api/parameter/catalogue/"+ clientType + "/"+code)
                .retrieve()
                .bodyToMono(ResponseParameter.class);
    }
}
