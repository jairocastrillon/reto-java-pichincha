package co.com.bancopichincha.retojava.adapters.primary.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Account's request DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class AccountRequest {

    /**
     * Account number.
     */
    @NotNull(message = "Account's number shouldn't be empty")
    private Long number;
    /**
     * Account type.
     */
    @NotBlank(message = "Account's type shouldn't be empty")
    private String type;
    /**
     * Account's initial balance.
     */
    @NotNull(message = "Account's initial balance shouldn't be empty")
    private Double initialBalance;
    /**
     * Account's status.
     */
    private boolean status;
    /**
     * Account's client identifier.
     */
    @NotNull(message = "Account's client identifier shouldn't be empty")
    private Long clientId;

}
