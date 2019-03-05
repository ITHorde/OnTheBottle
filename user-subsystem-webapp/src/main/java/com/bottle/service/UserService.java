package com.bottle.service;

import com.bottle.entity.User;
import com.bottle.entity.UserOnline;
import com.bottle.model.dto.SmallUserDTO;
import com.bottle.model.dto.UserDTO;
import com.bottle.model.dto.request.ReqRegDTO;
import com.bottle.model.dto.request.ReqAuthDTO;
import com.bottle.model.dto.request.UserIdDTO;
import com.bottle.repository.UserOnlineRepository;
import com.bottle.model.dto.request.UserIdDTO;
import com.bottle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class UserService {

    private UserRepository userRepository;
    private UserOnlineRepository onlineRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserOnlineRepository onlineRepository) {
        this.userRepository = userRepository;
        this.onlineRepository = onlineRepository;
    }

    public boolean isUserById(UUID id) {
        return userRepository.existsById( id );
    }

    public UUID getIdByLogin(String login) {
        return UUID.fromString( userRepository.getIdByLogin( login ) );
    }

    public boolean addNewUser(ReqRegDTO userDTO) {
        try {
            User user = new User( userDTO );
            user.setLogin( userDTO.getLogin().toLowerCase() );
            user.setEmail( userDTO.getEmail().toLowerCase() );
            userRepository.save( user );
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isAuth(ReqAuthDTO authDTO) {
        return userRepository.isAuth( authDTO.getLogin(), authDTO.getPassword() );
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.getUserById( id );
        return getUserDTO( user );
    }

    @Transactional
    public User getUser(UUID userId) {
        return userRepository.findOne( userId );
    }

    @Transactional
    public void updateUser(User user){
        userRepository.save( user );
    }

    @Transactional
    public SmallUserDTO getSmallInfoUser(UUID userId) {
        User user = userRepository.findOne( userId );
        SmallUserDTO smallUserDTO = new SmallUserDTO();
        smallUserDTO.setId( user.getId() );
        smallUserDTO.setName( user.getName() );
        smallUserDTO.setSurname( user.getSurname() );
        smallUserDTO.setAvatarUrl( user.getAvatarUrl() );
        return smallUserDTO;
    }

    @Transactional
    public List<User> getUsers(List<UserIdDTO> usersId) {
        List<User> users = new ArrayList<>();
        for (UserIdDTO id : usersId) {
            UUID idUser = id.getId();
            User user = userRepository.getUserById( idUser );
            users.add( user );
        }
        return users;
    }

    @Transactional
    public Set<User> getUsers(Set<UUID> usersId) {
        Set<User> users = new HashSet<>();
        for (UUID id : usersId) {
            User user = userRepository.getUserById( id );
            users.add( user );
        }
        return users;
    }

    public Set<UserDTO> getUsers() {
        Set<User> users = userRepository.getAllByDeletedFalse();
        return getUsersDTO( users );
    }

    public Set<UserDTO> getUsersDTO(Set<User> users) {
        Set<UserDTO> usersDTO = new HashSet<>();
        for (User user : users) {
            usersDTO.add( getUserDTO( user ) );
        }
        return usersDTO;
    }

    public void setOnlineStatus(UUID userId) {
/*
        UserOnline userOnline;
        if (!onlineRepository.existsByUserId(userId)){
            userOnline = new UserOnline();
            userOnline.setUserId(userId);
        }else {
            userOnline = onlineRepository.getByUserId(userId);
        }
        userOnline.setOnline(true);
        onlineRepository.save(userOnline);
*/
    }

 //   public UserDTO getUserDTO(User user) {
 //       UserDTO userDTO = new UserDTO();
 //       userDTO.setFriendStatus( user.getFriendStatus() );
 //       userDTO.setName( user.getName() );
 //       userDTO.setSurname( user.getSurname() );
 //       userDTO.setId( user.getId() );
 //       userDTO.setAge( user.getAge() );
 //       userDTO.setAvatarUrl( user.getAvatarUrl() );
 //       userDTO.setCity( user.getCity() );
 //       userDTO.setCountry( user.getCountry() );
 //       List<UUID> friends = new ArrayList<>();
//
 //       for (User friend : user.getFriends()) {
 //           friends.add( friend.getId() );
 //       }
 //       userDTO.setFriendsId( friends );
 //       return userDTO;
 //   }
//
    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName( user.getName() );
        userDTO.setSurname( user.getSurname() );
        userDTO.setId( user.getId() );
        userDTO.setAvatarUrl( user.getAvatarUrl() );
        userDTO.setFriendStatus(user.getFriendStatus());
        userDTO.setDeleted(user.getDeleted());
        return userDTO;
    }

    @Transactional
    public Set<UserDTO> getUsersInfo(List<UserIdDTO> usersId) {
        Set<UserDTO> users = new HashSet<>();

        for (UserIdDTO userIdDTO : usersId) {
            User user = userRepository.getOne( userIdDTO.getId() );
            UserDTO userDTO = getUserDTO( user );
            users.add( userDTO );
        }
        return users;
    }
}
