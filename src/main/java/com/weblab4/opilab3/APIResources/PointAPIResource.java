package com.weblab4.opilab3.APIResources;

import com.weblab4.opilab3.JWT.JWTUtil;
import com.weblab4.opilab3.database.databaseServices.PointService;
import com.weblab4.opilab3.database.databaseServices.UserService;
import com.weblab4.opilab3.database.models.Point;
import com.weblab4.opilab3.database.models.User;
import com.weblab4.opilab3.requestTemplates.PointRequest;
import com.weblab4.opilab3.responseTemplates.PointResponseTemplate;
import com.weblab4.opilab3.utils.AreaFucntions;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.ResourceBundle;

/**
 * auth api class
 */
@Path("/point")
public class PointAPIResource {

    @EJB
    PointService pointService;

    @EJB
    UserService userService;

    private final ResourceBundle messages = ResourceBundle.getBundle("locale/responses");

    /**
     * add point handler
     *
     * @param request    point request
     * @param auth_token auth token
     * @return response
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPoint(PointRequest request, @HeaderParam("Authorization") String auth_token) {

        System.out.println(request.getX());
        System.out.println(request.getY());
        System.out.println(request.getR());

        if (auth_token == null || !auth_token.startsWith("Bearer "))
            return Response.status(Response.Status.UNAUTHORIZED).entity(messages.getString("token_invalid_error")).build();

        String token = auth_token.substring(7);

        if (!JWTUtil.validateToken(token))
            return Response.status(Response.Status.UNAUTHORIZED).entity(messages.getString("token_auth_header_error")).build();

        String username = JWTUtil.getUsernameFromToken(token);
        User owner = userService.findByUsername(username);

        if (owner == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(messages.getString("token_user_not_found_error")).build();

        boolean hitResult = AreaFucntions.hitCheck(request.getX(), request.getY(), request.getR());
        pointService.add(new Point(request.getX(), request.getY(), request.getR(), hitResult, owner));

        return Response.status(Response.Status.CREATED).entity(hitResult).build();
    }

    /**
     * register handler
     *
     * @param auth_token auth token
     * @return response
     */
    @GET
    @Path("/getUserPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPoints(@HeaderParam("Authorization") String auth_token) {

        if (auth_token == null || !auth_token.startsWith("Bearer "))
            return Response.status(Response.Status.UNAUTHORIZED).entity(messages.getString("token_invalid_error")).build();

        String token = auth_token.substring(7);

        if (!JWTUtil.validateToken(token))
            return Response.status(Response.Status.UNAUTHORIZED).entity(messages.getString("token_auth_header_error")).build();

        String username = JWTUtil.getUsernameFromToken(token);
        User owner = userService.findByUsername(username);

        if (owner == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(messages.getString("token_user_not_found_error")).build();

        List<Point> points = pointService.getAllPointsByUsername(username);

        List<PointResponseTemplate> response = points.stream()
                .map(point -> new PointResponseTemplate(
                        point.getX(),
                        point.getY(),
                        point.getR(),
                        point.isResult(),
                        point.getUser().getUsername()
                )).toList();

        return Response.status(Response.Status.OK).entity(response).build();
    }
}
