package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class MovementResponse {

    private long id;
    private long accountNumber;
    private Date submissionDate;
    private String type;
    private double amount;
    private double balance;
}
