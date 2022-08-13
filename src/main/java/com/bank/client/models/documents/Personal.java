package com.bank.client.models.documents;

import com.bank.client.models.enums.PersonalType;
import com.bank.client.models.utils.Audit;
import lombok.Data;

import java.util.List;

@Data
public class Personal extends Audit
{
    private String id;
    private PersonalType personalType;
    private String savingAccount;
    private String currentAccount;
    private List<String> fixedTermAccounts;
}
