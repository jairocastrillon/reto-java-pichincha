package co.com.bancopichincha.retojava.adapters.primary.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class ClientRequest {

    private Long id;
    @NotBlank(message = "Client's name should not be empty")
    private String name;
    @NotBlank(message = "Client's genre should not be empty")
    private String genre;
    private int age;
    @NotBlank(message = "Client's address should not be empty")
    private String address;
    @NotBlank(message = "Client's phone should not be empty")
    private String phone;
    @NotBlank(message = "Client's id should not be empty")
    private String clientId;
    @NotBlank(message = "Client's password should not be empty")
    private String password;
    private boolean status;
}
