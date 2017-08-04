package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.spi.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @Spy
    private final UserFactory userFactory = new UserFactory();
    
    @InjectMocks
    private UserService userService;
    
    @Captor
    private ArgumentCaptor<User> argument;
    
    @Test
    public void doitCreerUnUtilisateur() {
        // GIVEN
        when(userRepository.findUserWithEmail(any())).thenReturn(Optional.empty());
        
        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail("email");
        userCreate.setPasswordHash("password");
        userCreate.setGender("X");
        userCreate.setFullName("fullName");
        
        // WHEN
        Result result = userService.createUser(userCreate);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);
        
        verify(userRepository).put(argument.capture());
        assertThat(argument.getValue())
                .extracting(
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsOnly(
                        "email", 
                        "password", 
                        Gender.X, 
                        "fullName");
    }
    
    @Test
    public void doitRetournerUneErreurSiCreerUnUtilisateurAvecEmailExistant() {
        // GIVEN
        User user = userFactory.buildNewUser().build();
        when(userRepository.findUserWithEmail(any())).thenReturn(Optional.of(user));
        
        // WHEN
        Result result = userService.createUser(new UserCreateDto());
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);
        
        verify(userRepository, never()).put(any(User.class));
    }
    
    @Test
    public void doitMettreAJourUnUser() {
        // GIVEN
        User user = userFactory.buildNewUser().build();
        user.setEmail("email");
        user.setPasswordHash("password");
        user.setGender(Gender.X);
        user.setFullName("fullName");
        when(userRepository.get(eq(user.getId().getIdentity()))).thenReturn(Optional.of(user));
        
        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(IdentifiantDto.create(user.getId().getIdentity()));
        userUpdate.setEmail("email modifié");
        userUpdate.setGender("MR");
        userUpdate.setFullName("fullName modifié");
        
        when(userRepository.findUserWithEmail(eq("email modifié"))).thenReturn(Optional.empty());  
        
        // WHEN
        Result result = userService.updateUser(userUpdate);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);
        
        verify(userRepository).put(argument.capture());
        assertThat(argument.getValue())
                .extracting(
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsOnly(
                        "email modifié",
                        "password",
                        Gender.MR, 
                        "fullName modifié");
    }
    
    @Test
    public void doitRetournerUneErreurSiLEmailExisteDejaLorsDeLaMiseAJourDeLUser() {
        // GIVEN        
        User user = userFactory.buildNewUser().build();
        when(userRepository.findUserWithEmail(eq("email autre"))).thenReturn(Optional.of(user));          
        
        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(IdentifiantDto.create(UUID.randomUUID()));
        userUpdate.setEmail("email autre");        
        
        // WHEN
        Result result = userService.updateUser(userUpdate);
                
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN); 
        verify(userRepository, never()).put(any(User.class));    
    }
    
    @Test
    public void doitMettreAJourLUserSiLEmailExisteDejaSurLeMemeUser() {
        // GIVEN        
        User user = userFactory.buildNewUser().build();
        user.setEmail("email");
        user.setPasswordHash("password");
        user.setGender(Gender.X);
        user.setFullName("fullName");
        when(userRepository.get(eq(user.getId().getIdentity()))).thenReturn(Optional.of(user));
        
        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(IdentifiantDto.create(user.getId().getIdentity()));
        userUpdate.setEmail("email");
        userUpdate.setGender("MR");
        userUpdate.setFullName("fullName modifié");
        
        when(userRepository.findUserWithEmail(eq("email"))).thenReturn(Optional.of(user));          
        
        // WHEN
        Result result = userService.updateUser(userUpdate);
                
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK); 
        
        verify(userRepository).put(argument.capture());
        assertThat(argument.getValue())
                .extracting(
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsOnly(
                        "email",
                        "password",
                        Gender.MR, 
                        "fullName modifié");
    }
    
    @Test
    public void doitEffacerUnUser() {
        // GIVEN   
        User user = userFactory.buildNewUser().build();
        when(userRepository.get(eq(user.getId().getIdentity()))).thenReturn(Optional.of(user));
        
        UserUpdateDto userToDelete = new UserUpdateDto();
        userToDelete.setIdentifiant(IdentifiantDto.create(user.getId().getIdentity()));
        
        // WHEN
        Result result = userService.deleteUser(userToDelete);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK); 
        
        verify(userRepository).remove(argument.capture());
        assertThat(argument.getValue())
                .extracting(userDeleted -> userDeleted.getId().getIdentity())
                .containsOnly(userToDelete.getIdentifiant().getId());
    }
}
