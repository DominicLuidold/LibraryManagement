package at.fhv.teamg.librarymanagement.server.rest;

import static at.fhv.teamg.librarymanagement.server.rest.Rest.ADMIN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN_EXTERNAL_LIBRARY;


import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.camel.json.simple.JsonObject;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/lending")
public class LendingController {

    @Post(produces = MediaType.TEXT_JSON, consumes = MediaType.TEXT_JSON)
    @Secured({ADMIN, LIBRARIAN, LIBRARIAN_EXTERNAL_LIBRARY})
    @Operation(summary = "Create a new lending", description = "Create a new lending for "
        + "a specific medium copy and user.")
    @ApiResponse(responseCode = "201", description = "Created lending")
    public HttpResponse<JsonObject> lend(HttpRequest<String> request, @Body LendingDto dto) {
        var msgDto = new LendingService().createLending(dto);

        var response = new JsonObject();
        response.put("message", msgDto.getMessage());

        if (msgDto.getType().equals(MessageDto.MessageType.SUCCESS)) {
            response.put("lending", msgDto.getResult());
            return HttpResponse.ok(response); // todo switch to 201 created?
        } else if (msgDto.getType().equals(MessageDto.MessageType.FAILURE)) {
            return HttpResponse.badRequest(response);
        } else {
            return HttpResponse.serverError(response);
        }
    }

}