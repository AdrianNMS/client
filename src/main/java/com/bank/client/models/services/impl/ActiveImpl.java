package com.bank.client.models.services.impl;

import com.bank.client.models.services.IActiveService;
import com.bank.client.models.utils.ResponseActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ActiveImpl implements IActiveService
{
    @Qualifier("getWebClientActive")
    @Autowired
    WebClient webClient;

    @Override
    public Mono<ResponseActive> checkCreditCard(String id, Integer typeCreditCard)
    {
        return webClient.get()
                .uri("/api/active/creditcard/"+ id +"/"+ typeCreditCard)
                .retrieve()
                .bodyToMono(ResponseActive.class);
    }
}
