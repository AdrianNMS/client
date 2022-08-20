package com.bank.client.models.documents;

import com.bank.client.models.utils.Audit;
import com.bank.client.models.enums.ClientGenre;
import com.bank.client.models.enums.ClientType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Data
@Document(collection = "clients")
public class Client extends Audit
{
    @Id
    private String id;
    @NotNull(message = "type must not be null")
    private ClientType type;
    @NotNull(message = "firstname must not be null")
    private String firstname;
    @NotNull(message = "lastName must not be null")
    private String lastName;
    @NotNull(message = "genre must not be null")
    private ClientGenre genre;
    @NotNull(message = "documentId must not be null")
    private String documentId;
    private String phoneNumber;
    private String email;

    private Personal personal;
    private Company company;

    public void setClientDataId(String idClientData)
    {
        if(type == ClientType.PERSONAL)
        {
            personal.setId(idClientData);
        }
        else if(type == ClientType.COMPANY)
        {
            company.setId(idClientData);
        }
    }

    public Integer getTypeClientData()
    {
        if(type == ClientType.PERSONAL)
        {
            return personal.getPersonalType().getValue();
        }
        else if(type == ClientType.COMPANY)
        {
            return company.getCompanyType().getValue();
        }
        else
        {
            return null;
        }
    }

    public void clear()
    {
        if(type == ClientType.PERSONAL)
        {
            setCompany(null);
        }
        else if(type == ClientType.COMPANY)
        {
            setPersonal(null);
        }
    }


}
