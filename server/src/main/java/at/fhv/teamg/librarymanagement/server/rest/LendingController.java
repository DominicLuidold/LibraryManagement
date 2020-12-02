package at.fhv.teamg.librarymanagement.server.rest;

import static at.fhv.teamg.librarymanagement.server.rest.Rest.ADMIN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN_EXTERNAL_LIBRARY;


import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.rmi.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.util.UUID;
import org.apache.camel.json.simple.JsonObject;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/lending")
public class LendingController {

    @Post(produces = MediaType.TEXT_JSON)
    @Secured({ADMIN, LIBRARIAN, LIBRARIAN_EXTERNAL_LIBRARY})
    public HttpResponse<JsonObject> lend(HttpRequest<String> request, LendingDto dto) {
        var msgDto = new LendingService().createLending(dto);

        var response = new JsonObject();
        response.put("message", msgDto.getMessage());

        if (msgDto.getType().equals(MessageDto.MessageType.SUCCESS)) {
            response.put("lending", msgDto.getResult());
            return HttpResponse.ok(response);
        } else if (msgDto.getType().equals(MessageDto.MessageType.FAILURE)) {
            return HttpResponse.badRequest(response);
        } else {
            return HttpResponse.serverError(response);
        }
    }

}