package com.bank.client.models.documents;

import com.bank.client.models.utils.Audit;
import lombok.Data;

import java.util.List;

@Data
public class Personal extends Audit
{
    public String id;
    private String savingAccount;
    private String currentAccount;
    private List<String> fixedTermAccounts;
}
