package com.weblab4.opilab3.responseTemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Auth Response DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthResponseTemplate {
    private String message;
    private String username;
    private String token;
}
