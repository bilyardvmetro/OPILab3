package com.weblab4.opilab3.database.databaseServices;


import com.weblab4.opilab3.database.models.Point;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class PointService {

    @PersistenceContext
    private EntityManager entityManager;


    public void add(Point point) {
        entityManager.persist(point);
    }

    public List<Point> getAllPointsByUsername(String username) {
        return new ArrayList<>(entityManager
                .createQuery("select p from Point p where p.user.username = :username", Point.class)
                .setParameter("username", username)
                .getResultList());
    }
}
