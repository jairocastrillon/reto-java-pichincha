package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Movement's response DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class MovementResponse {

    /**
     * Movement's identifier.
     */
    private long id;
    /**
     * Movement's account number.
     */
    private long accountNumber;
    /**
     * Movement's submission date.
     */
    private Date submissionDate;
    /**
     * Movement's amount.
     */
    private double amount;
    /**
     * Movement's balance.
     */
    private double balance;

}
