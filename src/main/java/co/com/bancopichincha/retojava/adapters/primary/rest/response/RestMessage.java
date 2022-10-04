package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class RestMessage {

    private Date timestamp;
    private String message;

}
