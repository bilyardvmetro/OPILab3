package com.weblab4.opilab3.responseTemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Point Response DTO object
 */
@Getter
@Setter
@AllArgsConstructor
public class PointResponseTemplate {
    private double x;
    private double y;
    private double r;
    private boolean hitResult;
    private String username;
}
