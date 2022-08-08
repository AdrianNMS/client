package com.bank.client.models.documents;

import com.bank.client.models.utils.Audit;
import com.bank.client.models.enums.ClientGenre;
import com.bank.client.models.enums.ClientType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "clients")
public class Client extends Audit
{
    @Id
    private String id;
    private ClientType type;
    private String firstname;
    private String lastName;
    private ClientGenre genre;
    private String documentId;
    private String phoneNumber;
    private String email;
    private Object clientData;

}
