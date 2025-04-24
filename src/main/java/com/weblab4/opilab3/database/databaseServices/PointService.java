package com.weblab4.opilab3.database.databaseServices;


import com.weblab4.opilab3.database.models.Point;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

/**
 * EJB bean which performs add point operation and fetches user points
 */
@Stateless
public class PointService {
    /**
     * Entity manager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * adds user's point to database
     * @param point - point JPA entity
     */
    public void add(Point point) {
        entityManager.persist(point);
    }

    /**
     * get user's points from database
     * @param username - user's username  whose points you need to get
     * @return list of  user's points
     */
    public List<Point> getAllPointsByUsername(String username) {
        return new ArrayList<>(entityManager
                .createQuery("select p from Point p where p.user.username = :username", Point.class)
                .setParameter("username", username)
                .getResultList());
    }
}
