package com.bank.client.models.documents;

import com.bank.client.models.utils.Audit;
import lombok.Data;

import java.util.UUID;

@Data
public class Personal extends Audit
{
    private UUID id;
    private UUID savingAccount;
    private UUID currentAccount;
    private UUID fixedTermAccountId;
}
