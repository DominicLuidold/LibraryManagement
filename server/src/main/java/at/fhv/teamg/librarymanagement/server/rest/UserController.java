package at.fhv.teamg.librarymanagement.server.rest;

import static at.fhv.teamg.librarymanagement.server.rest.Rest.ADMIN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
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
    @Operation(summary = "Get all users", description = "Get all users with their details")
    @ApiResponse(responseCode = "200", description = "List of users")
    public HttpResponse<List<UserDto>> all(HttpRequest<String> request) {
        var users = Cache.getInstance().getAllUsers();
        return HttpResponse.ok(users).header("Cache-Control", "no-store");
    }

    /**
     * Get only customers.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "customer")
    @Operation(summary = "Get all customers", description = "Get all customers")
    @ApiResponse(responseCode = "200", description = "List of customers")
    @Secured({ADMIN, LIBRARIAN})
    public HttpResponse<List<UserDto>> customer(HttpRequest<String> request) {
        var customers = Cache.getInstance().getAllCustomers();
        return HttpResponse.ok(customers).header("Cache-Control", "no-store");
    }
}
