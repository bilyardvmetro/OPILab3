package com.weblab4.opilab3.database.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name="points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    @Column(name = "r", nullable = false)
    private double r;

    @Column(name = "result", nullable = false)
    private boolean result;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    public Point(double x, double y, double r, boolean result, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.user = user;
    }
}
