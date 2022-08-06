package com.bank.client.models.documents;

import com.bank.client.models.utils.Audit;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Company extends Audit
{
    public UUID id;
    public List<UUID> owners;
    public List<UUID> signatories;
    public List<UUID> currentAccounts;
}
