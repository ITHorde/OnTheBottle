package com.bottle.entity;

import com.bottle.model.dto.request.ReqRegDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "friends")
//@ToString(exclude = {"events", "ownerEvents", "likes", "posts", "comments"})
//@EqualsAndHashCode
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private int age;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private Boolean deleted = false;
    @Column (name = "status")
    private String status;
    @Column(name = "info")
    private String info;

    //@ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RelationShip", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends;

    private String friendStatus;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(ReqRegDTO request) {
        this.login = request.getLogin();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.age = request.getAge();
        this.name = request.getName();
        this.surname = request.getSurname();
        this.avatarUrl = request.getAvatarUrl();
        this.country = request.getCountry();
        this.city = request.getCity();
    }
}
