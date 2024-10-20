package camilo.opertationquasarfire.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import camilo.opertationquasarfire.entities.RoleEntity;
import camilo.opertationquasarfire.entities.UserEntity;
import camilo.opertationquasarfire.models.ERole;
import camilo.opertationquasarfire.repositories.UserRepository;
import camilo.opertationquasarfire.requests.CreateUserDTO;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserEntity createUser(CreateUserDTO user) {

         Set<RoleEntity> roles = user.getRoles().stream()
        .map( role -> RoleEntity.builder().name(ERole.valueOf(role)).build())
        .collect(Collectors.toSet());
        
        UserEntity userEntity = UserEntity.builder()
        .username(user.getUsername())
        .password(passwordEncoder.encode(user.getPassword()))
        .email(user.getEmail())
        .roles(roles)
        .build();

        return this.userRepository.save(userEntity);
    }

    public String deleteUserById(String stringId){
        
        Long id = Long.parseLong(stringId);
        if(this.userRepository.existsById(id)){
            this.userRepository.deleteById(id);
            return "User '"+stringId+"' deleted.";
        }else{
            return "User '"+stringId+"' doesn't exist.";
        }

    }

}
