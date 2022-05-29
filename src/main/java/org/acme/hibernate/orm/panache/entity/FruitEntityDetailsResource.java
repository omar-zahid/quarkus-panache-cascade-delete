package org.acme.hibernate.orm.panache.entity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("entity/fruits/details")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class FruitEntityDetailsResource {

    private static final Logger LOGGER = Logger.getLogger(FruitEntityResource.class.getName());

    @GET
    @Path("{id}")
    public FruitDetailsEntity getSingle(Long id) {
        return FruitDetailsEntity.findByFruitID(id);
    }

    @POST
    @Transactional
    public RestResponse<FruitDetailsEntity> create(FruitDetailsEntity entity) {
        if (entity == null) {
            throw new WebApplicationException("Administration body was not set on request.", 422);
        }
        if (entity.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        entity.persist();
        return ResponseBuilder.ok(entity).status(Status.CREATED).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
