package com.bottle.repository;

import com.bottle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    default boolean isAuth(String login, String pass) {
        return existsByLoginIgnoreCaseAndPasswordAndDeletedFalse(login, pass);
    }


    boolean existsByLoginIgnoreCaseAndPasswordAndDeletedFalse(String login, String pass);

    List<User> getByName(String name);

    List<User> getBySurname(String surname);

    List<User> getByCity(String city);

    List<User> getByCountry(String country);

    List<User> getByAge(int age);

    boolean existsById(UUID id);

    User getUserById(UUID id);

    @Query("SELECT u.id FROM User u WHERE LOWER(u.login) = LOWER(?1)")
    String getIdByLogin(String login);

    Set<User> getAllByFriendsContaining(User user);

    Set<User> getAllByDeletedFalse();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.name = ?2, u.surname = ?3, u.age = ?4, u.email = ?5, u.country = ?6, u.city = ?7," +
            "u.status = ?8, u.info = ?9, u.password = ?10 where u.id = ?1")
    int setUserById(UUID id, String name, String surname, Integer age, String email, String country, String city, String status, String info, String password);

    @Query("select u from User u where lower(u.name) like :s or lower(u.surname) like :s")
    List<User> getAllUsersLike(@Param("s") String s);

    @Query("select u from User u where lower(u.name) in :s or lower(u.surname) in :s")
    List<User> getAllUsersIn(@Param("s") List<String> s);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.deleted=?2 where u.id=?1")
    void setDeleted(UUID id, boolean deleted);
}
