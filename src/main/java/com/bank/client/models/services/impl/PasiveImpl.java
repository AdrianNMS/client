package com.bank.client.models.services.impl;

import com.bank.client.models.services.IPasiveService;
import com.bank.client.models.utils.ResponseParameter;
import com.bank.client.models.utils.ResponsePasive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PasiveImpl implements IPasiveService
{
    @Autowired
    @Qualifier("getWebClientPasive")
    WebClient webClient;

    @Override
    public Mono<ResponsePasive> ExistByClientIdType(Integer type, String idClient)
    {
        return webClient.get()
                .uri("/api/active/client/"+type+"/"+idClient)
                .retrieve()
                .bodyToMono(ResponsePasive.class);
    }
}
