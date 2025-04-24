package com.weblab4.opilab3.database.databaseServices;

import com.weblab4.opilab3.database.models.User;
import com.weblab4.opilab3.utils.PasswordHasher;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 * EJB bean which performs login, registration and search user by username
 */
@Stateless
public class UserService {

    /**
     * Entity manager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * try to fetch user by username from database
     *
     * @param username user's username
     * @return User object
     */
    public User findByUsername(String username) {
        try {
            return entityManager.find(User.class, username);

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * try to perform user registration
     *
     * @param username user's username
     * @param password user's password
     * @return true if user doesn't exists
     */
    public boolean register(String username, String password) {
        if (findByUsername(username) != null) return false;

        var newUser = new User(username, PasswordHasher.hashPassword(password));
        entityManager.persist(newUser);
        return true;
    }

    /**
     * try to perform user login
     *
     * @param username user's username
     * @param password user's password
     * @return true id login successful
     */
    public boolean login(String username, String password) {
        var user = findByUsername(username);

        return user != null && PasswordHasher.verifyPassword(password, user.getPassword());
    }
}
