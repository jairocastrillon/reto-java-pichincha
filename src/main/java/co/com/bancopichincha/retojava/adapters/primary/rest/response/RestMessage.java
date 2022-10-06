package co.com.bancopichincha.retojava.adapters.primary.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Rest exception message DTO class.
 */
@AllArgsConstructor
@Getter
@Setter
public class RestMessage {

    /**
     * Exception's date.
     */
    private Date timestamp;
    /**
     * Exception's message.
     */
    private String message;
    /**
     * Exception's details.
     */
    private List<String> details;

}
