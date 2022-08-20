package com.bank.client.controllers.helpers;

import com.bank.client.handler.ResponseHandler;
import com.bank.client.models.documents.Client;
import com.bank.client.models.services.IClientService;
import com.bank.client.models.services.IParameterService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class GetParamsHelper
{
    public static Mono<ResponseEntity<Object>> getParameter(Client client, Integer code, IParameterService parameterService, Logger log)
    {

        return parameterService.findByCode(client.getTypeClientData(), code)
                .flatMap(responseParameter ->
                {
                    log.info(responseParameter.toString());
                    if (responseParameter.getData() != null)
                        return Mono.just(ResponseHandler.response("Done", HttpStatus.OK, responseParameter.getData()));
                    else
                        return Mono.just(ResponseHandler.response("Parameter not found", HttpStatus.NOT_FOUND, null));
                });
    }

    public static Mono<ResponseEntity<Object>> CheckClient(String id, int code, IParameterService parameterService, IClientService clientService, Logger log)
    {
        return clientService.Find(id)
                .flatMap(client -> {
                    if(client!=null)
                    {
                        log.info(client.toString());
                        return getParameter(client,code,parameterService,log);
                    }
                    else
                        return Mono.just(ResponseHandler.response("Client not found", HttpStatus.NOT_FOUND, null));
                });
    }

    public static Mono<ResponseEntity<Object>> GetParamsSecuence(String id, int code, IParameterService parameterService, IClientService clientService, Logger log)
    {
        return CheckClient(id,code,parameterService,clientService,log);
    }
}
