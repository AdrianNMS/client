package com.bank.client.controllers.helpers;

import com.bank.client.handler.ResponseHandler;
import com.bank.client.models.documents.Personal;
import com.bank.client.models.enums.PersonalType;
import com.bank.client.models.services.IActiveService;
import com.bank.client.models.services.IClientService;
import com.bank.client.models.services.IMovementService;
import com.bank.client.models.services.IPasiveService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;


public class UpgradeVIPHelper
{
    public static Mono<ResponseEntity<Object>> UpdateClient(Logger log, IClientService clientService, String idClient)
    {
        return clientService.Find(idClient)
                .flatMap(client -> {
                    if(client!=null)
                    {
                        log.info(client.toString());
                        client.getPersonal().setPersonalType(PersonalType.VIP);
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

    public static Mono<ResponseEntity<Object>> CheckBalance(Logger log, IClientService clientService, IMovementService movementService, String idClient, String idPasive)
    {

        return movementService.getBalance(idPasive).flatMap(responseMovement -> {
            log.info(responseMovement.toString());
            if(responseMovement.getData()>0)
                return UpdateClient(log,clientService,idClient);
            else
                return Mono.just(ResponseHandler.response("You don't have enough balance in your credit card", HttpStatus.OK, null));
        })
        .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
        .switchIfEmpty(Mono.just(ResponseHandler.response("Empty", HttpStatus.NO_CONTENT, null)));
    }

    public static Mono<ResponseEntity<Object>> CheckCreditCard(Logger log, IClientService clientService, IPasiveService pasiveService, IMovementService movementService, IActiveService activeService, String idClient, String idPasive)
    {
        return activeService.checkCreditCard(idClient, 2002).flatMap(responseActive ->
                {
                    log.info(idClient);
                    log.info(responseActive.toString());
                    if(responseActive.getData())
                        return CheckBalance(log,clientService,movementService,idClient,idPasive);
                    else
                        return Mono.just(ResponseHandler.response("Not Found", HttpStatus.NO_CONTENT, null));
                }

        )
        .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
        .switchIfEmpty(Mono.just(ResponseHandler.response("Empty", HttpStatus.NO_CONTENT, null)));
    }

    public static Mono<ResponseEntity<Object>> FindActive(Logger log, IClientService clientService,IPasiveService pasiveService, IMovementService movementService,IActiveService activeService, String idClient)
    {
        return pasiveService.ExistByClientIdType(1000, idClient)
                .flatMap(responsePasive -> {
                    if(responsePasive.getData()!= null)
                    {
                        return CheckCreditCard(log,clientService,pasiveService,movementService,activeService,idClient, responsePasive.getData());
                    }
                    else
                        return Mono.just(ResponseHandler.response("Not Found", HttpStatus.NO_CONTENT, null));
                })
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .switchIfEmpty(Mono.just(ResponseHandler.response("Empty", HttpStatus.NO_CONTENT, null)));
    }



    public static Mono<ResponseEntity<Object>> UpdateVIPSequence(Logger log, IClientService clientService, IPasiveService pasiveService, IMovementService movementService, IActiveService activeService, String idClient)
    {
        return FindActive(log,clientService, pasiveService,movementService,activeService,idClient);
    }


}
