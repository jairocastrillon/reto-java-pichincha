package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client's response DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class ClientResponse {

    /**
     * Client's identifier.
     */
    private long id;
    /**
     * Client's name.
     */
    private String name;
    /**
     * Client's genre.
     */
    private String genre;
    /**
     * Client's age.
     */
    private int age;
    /**
     * Client's address.
     */
    private String address;
    /**
     * Client's phone.
     */
    private String phone;
    /**
     * Client's nickname.
     */
    private String clientId;
    /**
     * Client's password.
     */
    private String password;
    /**
     * Client's status.
     */
    private boolean status;

}
