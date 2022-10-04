package co.com.bancopichincha.retojava.adapters.primary.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class AccountRequest {

    @NotNull(message = "Account's number shouldn't be empty")
    private Long number;
    @NotBlank(message = "Account's type shouldn't be empty")
    private String type;
    @NotNull(message = "Account's initial balance shouldn't be empty")
    private Double initialBalance;
    private boolean status;
    @NotNull(message = "Account's client identifier shouldn't be empty")
    private Long clientId;

}
