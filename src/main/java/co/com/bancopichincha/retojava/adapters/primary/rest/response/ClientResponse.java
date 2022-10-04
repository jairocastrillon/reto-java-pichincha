package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClientResponse {

    private long id;
    private String name;
    private String genre;
    private int age;
    private String address;
    private String phone;
    private String clientId;
    private String password;
    private boolean status;

}
