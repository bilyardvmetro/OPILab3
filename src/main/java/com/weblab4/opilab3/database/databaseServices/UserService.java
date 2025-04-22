package com.weblab4.opilab3.database.databaseServices;

import com.weblab4.opilab3.database.models.User;
import com.weblab4.opilab3.utils.PasswordHasher;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;


    public User findByUsername(String username) {
        try{
            return entityManager.find(User.class, username);

        } catch (NoResultException e){
            return null;
        }
    }

    public boolean register(String username, String password) {
        if (findByUsername(username) != null) return false;

        var newUser = new User(username, PasswordHasher.hashPassword(password));
        entityManager.persist(newUser);
        return true;
    }

    public boolean login(String username, String password) {
        var user = findByUsername(username);

        return user != null && PasswordHasher.verifyPassword(password, user.getPassword());
    }
}
