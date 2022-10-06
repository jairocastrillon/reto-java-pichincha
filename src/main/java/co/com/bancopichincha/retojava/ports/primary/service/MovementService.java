package co.com.bancopichincha.retojava.ports.primary.service;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.MovementRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.MovementResponse;

public interface MovementService {

    MovementResponse saveMovement(MovementRequest movement);
    
}
