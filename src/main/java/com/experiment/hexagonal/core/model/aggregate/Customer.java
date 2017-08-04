package com.experiment.hexagonal.core.model.aggregate;

import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import java.util.Objects;

public final class Customer {
    private final User user;
    private final Adresse adresse;

    public Customer(User user, Adresse adresse) {
        this.user = user;
        this.adresse = adresse;
    }
    
    public Customer withUser(User user) {
        return new Customer(user, adresse);
    }
    
    public Customer withAdresse(Adresse adresse) {
        return new Customer(user, adresse);
    }

    public User getUser() {
        return user;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.adresse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        return Objects.equals(this.user, other.user) && Objects.equals(this.adresse, other.adresse);
    }
}
