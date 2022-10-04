package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {

    private long id;
    private long number;
    private String type;
    private double initialBalance;
    private boolean status;
    private long clientId;
    private String clientName;
}
