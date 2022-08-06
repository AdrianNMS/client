package com.bank.client.models.documents;

import com.bank.client.models.utils.Audit;
import com.bank.client.models.enums.clientGenre;
import com.bank.client.models.enums.clientType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "clients")
public class Client extends Audit
{
    @Id
    private String id;
    private clientType type;
    private String firstname;
    private String lastName;
    private clientGenre genre;
    private String documentId;
    private String phoneNumber;
    private String email;
    private Object clientData;

}
