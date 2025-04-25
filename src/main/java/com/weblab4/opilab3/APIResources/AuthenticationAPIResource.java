package com.weblab4.opilab3.APIResources;

import com.weblab4.opilab3.JWT.JWTUtil;
import com.weblab4.opilab3.database.databaseServices.UserService;
import com.weblab4.opilab3.database.models.User;
import com.weblab4.opilab3.responseTemplates.AuthResponseTemplate;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * auth api class
 */
@Path("/auth")
public class AuthenticationAPIResource {

    @EJB
    private UserService userService;

    private final ResourceBundle messages = ResourceBundle.getBundle("locale/responses", Locale.getDefault());

    /**
     * register handler
     *
     * @param user user
     * @return response
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        System.out.println(messages);
        if (!userService.register(user.getUsername(), user.getPassword())) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new AuthResponseTemplate(messages.getString("auth_username_already_exists_error"), "", ""))
                    .build();
        }
        return Response.ok().build();
    }

    /**
     * login handler
     *
     * @param user user
     * @return response
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {

        if (!userService.login(user.getUsername(), user.getPassword())) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new AuthResponseTemplate(messages.getString("auth_wrong_password_or_non_existing_user_error"), "", ""))
                    .build();
        }

        String token = JWTUtil.generateToken(user.getUsername());
        return Response.ok().entity(new AuthResponseTemplate(messages.getString("auth_success"), user.getUsername(), token)).build();
    }

}