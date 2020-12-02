package at.fhv.teamg.librarymanagement.server.rest;

import static at.fhv.teamg.librarymanagement.server.rest.Rest.ADMIN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.apache.camel.json.simple.JsonObject;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/user")
public class UserController {
    /**
     * Get all users.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON)
    @Secured({ADMIN, LIBRARIAN})
    public HttpResponse<JsonObject> all(HttpRequest<String> request) {
        var users = Cache.getInstance().getAllUsers();
        var response = new JsonObject();
        response.put("users", users);
        return HttpResponse.ok(response);
    }

    /**
     * Get only customers.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "customer")
    @Secured({ADMIN, LIBRARIAN})
    public HttpResponse<JsonObject> customer(HttpRequest<String> request) {
        var customers = Cache.getInstance().getAllCustomers();
        var response = new JsonObject();
        response.put("customers", customers);
        return HttpResponse.ok(response);
    }
}
