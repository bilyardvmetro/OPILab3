package com.weblab4.opilab3.database.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * JPA entity class, which describes application User. It also represents User model in database
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="users")
public class User {
    /** username, which is unique id of each user*/
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    /** user's password*/
    @Column(name = "password", nullable = false)
    private String password;
}
