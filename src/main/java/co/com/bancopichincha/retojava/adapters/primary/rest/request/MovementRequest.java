package co.com.bancopichincha.retojava.adapters.primary.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class MovementRequest {

    @NotNull(message = "Movement's account number shouldn't be empty")
    private Long accountNumber;
    @NotNull(message = "Movement's date shouldn't be empty")
    private Date submissionDate;
    @NotBlank(message = "Movement's type shouldn't be empty")
    private String type;
    @NotNull(message = "Movement's amount shouldn't be empty")
    private Double amount;

}
