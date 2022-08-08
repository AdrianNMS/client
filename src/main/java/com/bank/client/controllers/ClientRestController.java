package com.bank.client.controllers;

import com.bank.client.handler.ResponseHandler;
import com.bank.client.models.dao.ClientDao;
import com.bank.client.models.documents.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/client")
public class ClientRestController
{
    @Autowired
    private ClientDao dao;
    private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    @GetMapping
    public Mono<ResponseEntity<Object>> findAll()
    {
        return dao.findAll()
                .doOnNext(client -> log.info(client.toString()))
                .collectList()
                .map(clients -> ResponseHandler.response("Done", HttpStatus.OK, clients))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Object>> find(@PathVariable String id)
    {
        return dao.findById(id)
                .doOnNext(client -> log.info(client.toString()))
                .map(client -> ResponseHandler.response("Done", HttpStatus.OK, client))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> create(@RequestBody Client cli)
    {

        return dao.save(cli)
                .doOnNext(client -> log.info(client.toString()))
                .map(client -> ResponseHandler.response("Done", HttpStatus.OK, client)                )
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable("id") String id, @RequestBody Client cli)
    {
        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.save(cli)
                        .doOnNext(client -> log.info(client.toString()))
                        .map(client -> ResponseHandler.response("Done", HttpStatus.OK, client)                )
                        .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            else
                return Mono.just(ResponseHandler.response("Not found", HttpStatus.NOT_FOUND, null));

        });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable("id") String id)
    {
        log.info(id);

        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.deleteById(id).then(Mono.just(ResponseHandler.response("Done", HttpStatus.OK, null)));
            else
                return Mono.just(ResponseHandler.response("Not found", HttpStatus.NOT_FOUND, null));
        });
    }
}

