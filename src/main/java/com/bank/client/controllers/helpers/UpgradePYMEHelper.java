package com.bank.client.controllers.helpers;

import com.bank.client.handler.ResponseHandler;
import com.bank.client.models.documents.Company;
import com.bank.client.models.documents.Personal;
import com.bank.client.models.enums.CompanyType;
import com.bank.client.models.enums.PersonalType;
import com.bank.client.models.services.IActiveService;
import com.bank.client.models.services.IClientService;
import com.bank.client.models.services.IPasiveService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;


public class UpgradePYMEHelper
{

    public static Mono<ResponseEntity<Object>> UpdateClient(Logger log, IClientService clientService, String idClient)
    {
        return clientService.Find(idClient)
                .flatMap(client -> {
                    if(client!=null)
                    {
                        log.info(client.toString());
                        client.getCompany().setCompanyType(CompanyType.PYME);
                        return clientService.Update(idClient,client).flatMap(client1 -> {
                            if(client1!= null)
                                return Mono.just(ResponseHandler.response("Done", HttpStatus.OK, client1));
                            else
                                return Mono.just(ResponseHandler.response("Not Found", HttpStatus.NOT_FOUND, null));
                        });
                    }
                    else
                        return Mono.just(ResponseHandler.response("Not Found Client", HttpStatus.NOT_FOUND, null));

                });
    }


    public static Mono<ResponseEntity<Object>> CheckCreditCard(Logger log, IPasiveService pasiveService, IActiveService activeService, IClientService clientService, String idClient, String idPasive)
    {
        return activeService.checkCreditCard(idClient, 2003).flatMap(responseActive ->
                {
                    if(responseActive.getData())
                    {
                        return UpdateClient(log,clientService,idClient);
                    }
                    else
                        return Mono.just(ResponseHandler.response("Not Found", HttpStatus.NO_CONTENT, null));
                }

        )
        .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
        .switchIfEmpty(Mono.just(ResponseHandler.response("Empty", HttpStatus.NO_CONTENT, null)));
    }

    public static Mono<ResponseEntity<Object>> FindActive(Logger log, IPasiveService pasiveService, IActiveService activeService, IClientService clientService, String idClient)
    {
        return pasiveService.ExistByClientIdType(1001,idClient)
                .flatMap(responsePasive -> {
                    if(responsePasive.getData()!=null)
                    {
                        return CheckCreditCard(log,pasiveService,activeService,clientService,idClient, responsePasive.getData());
                    }
                    else
                        return Mono.just(ResponseHandler.response("Not Found", HttpStatus.NO_CONTENT, null));
                })
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .switchIfEmpty(Mono.just(ResponseHandler.response("Empty", HttpStatus.NO_CONTENT, null)));
    }


    public static Mono<ResponseEntity<Object>> UpdatePYMESequence(Logger log, IPasiveService pasiveService, IActiveService activeService, IClientService clientService, String idClient)
    {
        return FindActive(log,pasiveService,activeService,clientService, idClient);
    }
}
