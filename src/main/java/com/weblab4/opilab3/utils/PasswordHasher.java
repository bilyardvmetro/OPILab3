package com.weblab4.opilab3.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password operations
 */
public class PasswordHasher {
    /**Hash password
     * @param password - user's password
     * @return hashed password
     * */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    /**
     * @param password - user's password
     * @param hashedPassword - hashed password form database
     * @return true if password are identical
     * */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
