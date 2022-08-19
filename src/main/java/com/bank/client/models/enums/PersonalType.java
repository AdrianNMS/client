package com.bank.client.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonalType {
    NOVIP(0),
    VIP(1);

    private final int value;
}
