package com.bank.client.controllers;

import com.bank.client.controllers.helpers.GetParamsHelper;
import com.bank.client.controllers.helpers.UpgradePYMEHelper;
import com.bank.client.controllers.helpers.UpgradeVIPHelper;
import com.bank.client.handler.ResponseHandler;
import com.bank.client.models.documents.Client;
import com.bank.client.models.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientRestController
{
    @Autowired
    private IClientService clientService;

    @Autowired
    private IParameterService parameterService;
    @Autowired
    private IPasiveService pasiveService;
    @Autowired
    private IActiveService activeService;
    @Autowired
    private IMovementService movementService;
    private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    @GetMapping
    public Mono<ResponseEntity<Object>> findAll()
    {
        log.info("[INI] findAll Client");
        return clientService.FindAll()
                .flatMap(clients -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, clients)))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] findAll Client"));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Object>> find(@PathVariable String id)
    {
        log.info("[INI] find Client");
        return clientService.Find(id)
                .flatMap(client -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, client)))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] find Client"));
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> create(@Valid @RequestBody Client cli)
    {
        log.info("[INI] create Client");
        return clientService.Create(cli)
            .flatMap(client -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, client)))
            .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
            .doFinally(fin -> log.info("[END] create Client"));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable("id") String id,@Valid @RequestBody Client cli)
    {
        log.info("[INI] update Client");
        return clientService.Update(id,cli)
                .flatMap(client -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, client))                )
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] update Client"));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable("id") String id)
    {
        log.info("[INI] delete Client");

        return clientService.Delete(id)
                .flatMap(o -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, null)))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .switchIfEmpty(Mono.just(ResponseHandler.response("Error", HttpStatus.NO_CONTENT, null)))
                .doFinally(fin -> log.info("[END] delete Client"));
    }

    @GetMapping("/param/{id}/{code}")
    public Mono<ResponseEntity<Object>> getParam(@PathVariable("id") String id, @PathVariable("code")  Integer code)
    {
        log.info("[INI] getParams");

        return GetParamsHelper.GetParamsSecuence(id,code,parameterService,clientService,log)
                .doFinally(fin -> log.info("[END] getParams"));
    }

    @GetMapping("/vip/{idClient}")
    public Mono<ResponseEntity<Object>> updateVIP(@PathVariable("idClient") String idClient)
    {
        log.info("[INI] updateVIP");

        return UpgradeVIPHelper.UpdateVIPSequence(log,clientService, pasiveService,movementService,activeService,idClient)
                .doFinally(fin -> log.info("[END] updateVIP"));

    }

    @GetMapping("/pyme/{idClient}")
    public Mono<ResponseEntity<Object>> updatePyme(@PathVariable("idClient") String idClient)
    {
        log.info("[INI] updatePyme");

        return UpgradePYMEHelper.UpdatePYMESequence(log,pasiveService,activeService,clientService, idClient)
                .doFinally(fin -> log.info("[END] updatePyme"));

    }

}

