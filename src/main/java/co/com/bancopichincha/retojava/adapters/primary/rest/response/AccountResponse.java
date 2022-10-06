package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Account's response DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {

    /**
     * Account's identifier.
     */
    private long id;
    /**
     * Account's number.
     */
    private long number;
    /**
     * Account's type.
     */
    private String type;
    /**
     * Account's initial balance.
     */
    private double initialBalance;
    /**
     * Account's status.
     */
    private boolean status;
    /**
     * Client identifier associated to account.
     */
    private long clientId;
    /**
     * Client name belong to account.
     */
    private String clientName;
}
