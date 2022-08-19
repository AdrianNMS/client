package com.bank.client.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompanyType
{
    COMPANY(0),
    PYME(1);

    private final int value;

}
