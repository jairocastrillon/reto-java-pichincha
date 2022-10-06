package co.com.bancopichincha.retojava.adapters.primary.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Movement's request DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class MovementRequest {

    /**
     * Account's number associated to movement.
     */
    @NotNull(message = "Movement's account number shouldn't be empty")
    private Long accountNumber;
    /**
     * Movement's amount.
     */
    @NotNull(message = "Movement's amount shouldn't be empty")
    private Double amount;

}
