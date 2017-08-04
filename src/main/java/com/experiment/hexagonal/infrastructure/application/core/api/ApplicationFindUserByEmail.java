package com.experiment.hexagonal.infrastructure.application.core.api;

import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;

public interface ApplicationFindUserByEmail {
    void setEmail(String email);
    
    ClientUser executeFind();
    String executeFindFullName();
}
