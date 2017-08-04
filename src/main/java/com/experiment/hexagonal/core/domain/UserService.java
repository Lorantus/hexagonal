package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.CreateUser;
import com.experiment.hexagonal.core.api.DeleteUser;
import com.experiment.hexagonal.core.api.FindUser;
import com.experiment.hexagonal.core.api.UpdateUser;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserService implements CreateUser, UpdateUser, DeleteUser, FindUser {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    private final Function<String, Gender> parseGender = 
            gender -> gender == null ? null : Gender.valueOf(gender);
    
    private final Function<Gender, String> valueOfGender = 
            gender -> gender == null ? null : gender.name();
    
    @Autowired
    public UserService(
            @Qualifier("inMemoryUserRepository") 
            UserRepository userRepository,
            UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }
    
    @Override
    public Result createUser(UserCreateDto userCreate) {
        return userRepository.findUserWithEmail(userCreate.getEmail())
                .map(found -> TransactionResult.asForbidden("Cet email existe déjà"))
                .orElseGet(()-> {
                    userRepository.put(
                            userFactory.buildUser(UserId.randomId())
                                    .withEmail(userCreate.getEmail())
                                    .withPasswordHash(userCreate.getPasswordHash())
                                    .withGender(parseGender.apply(userCreate.getGender()))
                                    .withFullName(userCreate.getFullName())
                                    .build()
                    );
                    return TransactionResult.asSuccess();
                });
    }
    
    @Override
    public Result updateUser(UserUpdateDto userUpdate) {
        return userRepository.findUserWithEmail(userUpdate.getEmail())
                .filter(user -> !user.getId().getIdentity().equals(userUpdate.getIdentifiant().getId()))
                .map(found -> TransactionResult.asForbidden("Cet email existe déjà"))
                .orElseGet(() -> {
                    IdentifiantDto userIdDto = userUpdate.getIdentifiant();
                    return userRepository.get(userIdDto.getId())
                        .map(user -> {
                            userRepository.put(
                                    userFactory.updateUser(user)
                                        .withEmail(userUpdate.getEmail())
                                        .withPasswordHash(user.getPasswordHash())
                                        .withGender(parseGender.apply(userUpdate.getGender()))
                                        .withFullName(userUpdate.getFullName())
                                        .update()
                            );
                            return TransactionResult.asSuccess();
                        })
                        .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
                });        
    }
    
    @Override
    public Result deleteUser(UserUpdateDto userUpdate) {
        IdentifiantDto userIdDto = userUpdate.getIdentifiant();
        return userRepository.get(userIdDto.getId())
                .map(user -> {
                    userRepository.remove(user);
                    return TransactionResult.asSuccess();
                })
                .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
    }

    @Override
    public Result<UserUpdateDto> findUserByEmail(UserUpdateDto userEmail) {
        return userRepository.findUserWithEmail(userEmail.getEmail())
                .map(user -> {
                    UserUpdateDto userResult = new UserUpdateDto();
                    IdentifiantDto identifiantDto = IdentifiantDto.create(user.getId().getIdentity());
                    userResult.setIdentifiant(identifiantDto);
                    userResult.setEmail(user.getEmail());
                    userResult.setGender(valueOfGender.apply(user.getGender()));
                    userResult.setFullName(user.getFullName());            
                    return TransactionResult.asSuccess(userResult);
                })
                .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
    }
}
