package co.com.bancopichincha.retojava.adapters.primary.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Client request DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class ClientRequest {

    /**
     * Client identifier.
     */
    private Long id;
    /**
     * Client name.
     */
    @NotBlank(message = "Client's name should not be empty")
    private String name;
    /**
     * Client genre.
     */
    @NotBlank(message = "Client's genre should not be empty")
    private String genre;
    /**
     * Client's age.
     */
    private int age;
    /**
     * Client's address.
     */
    @NotBlank(message = "Client's address should not be empty")
    private String address;
    /**
     * Client's phone.
     */
    @NotBlank(message = "Client's phone should not be empty")
    private String phone;
    /**
     * Client's identifier.
     */
    @NotBlank(message = "Client's id should not be empty")
    private String clientId;
    /**
     * Client's password.
     */
    @NotBlank(message = "Client's password should not be empty")
    private String password;
    /**
     * Client's status.
     */
    private boolean status;
}
