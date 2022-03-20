package ru.bliprog.SocialNetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.RolesEnum;
import ru.bliprog.SocialNetwork.repositories.RoleRepository;
import ru.bliprog.SocialNetwork.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

   private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException("Имя пользователя не найдена");
        }
        return user;
    }

    public User findUserById(Long id){
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(null);
    }

    public User findUserByName(String name){
        return userRepository.findByUsername(name);
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public boolean isUsernameAlreadyUsed(String username){
        return userRepository.findByUsername(username) != null;
    }
    public boolean saveNewUser(User user){
        if(isUsernameAlreadyUsed(user.getUsername())){
            return false;
        }
        user.setRoles(Collections.singleton(roleRepository.findByName(RolesEnum.ROLE_USER.toString())));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    public void saveEditUserInfo(User user){
        userRepository.save(user);
    }
    public void ChangePassword(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean deleteUser(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
