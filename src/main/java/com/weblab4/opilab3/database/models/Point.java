package com.weblab4.opilab3.database.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class, which describes application user's points.
 * It also represents Point model in database
 */
@Data
@NoArgsConstructor

@Entity
@Table(name = "points")
public class Point {
    /**
     * unique point id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    /**
     * x coordinate
     */
    @Column(name = "x", nullable = false)
    private double x;
    /**
     * y coordinate
     */
    @Column(name = "y", nullable = false)
    private double y;
    /**
     * radius of area
     */
    @Column(name = "r", nullable = false)
    private double r;
    /**
     * result which represents whether point in or out of area
     */
    @Column(name = "result", nullable = false)
    private boolean result;
    /**
     * user, who owns this point
     */
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    /**
     * constructor
     *
     * @param x      - x coordinate
     * @param y      - y coordinate
     * @param r      - area radius
     * @param result - hit result
     * @param user   - User object
     */
    public Point(double x, double y, double r, boolean result, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.user = user;
    }
}
