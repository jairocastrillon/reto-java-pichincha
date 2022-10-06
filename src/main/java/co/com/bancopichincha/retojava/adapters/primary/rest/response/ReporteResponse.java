package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Report response DTO class.
 */
@NoArgsConstructor
@Getter
@Setter
public class ReporteResponse {

    /**
     * Movement's submission date.
     */
    private Date submissionDate;
    /**
     * Movement's client name.
     */
    @JsonProperty("client")
    private String accountClientName;
    /**
     * Movement's account number.
     */
    private long accountNumber;
    /**
     * Movement's account type.
     */
    private String accountType;
    /**
     * Movement's account initial balance.
     */
    private double accountInitialBalance;
    /**
     * Movement's account status.
     */
    private boolean accountStatus;
    /**
     * Movement's amount.
     */
    private double amount;
    /**
     * Movement's balance.
     */
    private double balance;

}
