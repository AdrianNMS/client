package com.bank.client.models.utils;

import lombok.Data;

@Data
public class ResponseParameter
{
    private Object data;

    private String message;

    private String status;

}
