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

    /**
     * Creates a new lending.
     *
     * @param request HTTP Request
     * @param dto Details of lending to create
     * @return Created Lending DTO
     */
    @Post(uri = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secured({ADMIN, LIBRARIAN, LIBRARIAN_EXTERNAL_LIBRARY})
    @Operation(summary = "Create a new lending", description = "Create a new lending for "
        + "a specific medium copy and user.")
    @ApiResponse(responseCode = "201", description = "Created lending")
    public HttpResponse<JsonObject> lend(HttpRequest<String> request,
                                         @Body LendingDto dto) {
        var msgDto = new LendingService().createLending(dto);

        var response = new JsonObject();
        response.put("message", msgDto.getMessage());

        if (msgDto.getType().equals(MessageDto.MessageType.SUCCESS)) {
            response.put("lending", msgDto.getResult());
            return HttpResponse.created(response).header("Cache-Control", "no-store");
        } else if (msgDto.getType().equals(MessageDto.MessageType.FAILURE)) {
            return HttpResponse.badRequest(response).header("Cache-Control", "no-store");
        } else {
            return HttpResponse.serverError(response).header("Cache-Control", "no-store");
        }
    }

}