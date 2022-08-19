package com.bank.client.models.documents;

import com.bank.client.models.enums.CompanyType;
import com.bank.client.models.utils.Audit;
import lombok.Data;

import java.util.List;

@Data
public class Company extends Audit implements ObjectClientType
{
    private String id;
    private CompanyType companyType;
    private List<String> owners;
    private List<String> signatories;
    private List<String> currentAccounts;

    @Override
    public Integer getTypeClient() {
        return companyType.getValue();
    }
}
