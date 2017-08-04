package com.experiment.hexagonal.infrastructure.client.core.spi;

import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;

public interface ClientFindUserByEmail {
    void setEmail(String email);
    
    ClientUser executeFind();
    String executeFindFullName();
}
