package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.rmi.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.UUID;
import org.apache.camel.json.simple.JsonObject;

@Controller("/detail")
public class DetailController {
    /**
     * Get book details with copies and reservations.
     *
     * @param request http request
     * @param uuid    book uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "book/{uuid}")
    public HttpResponse<JsonObject> book(HttpRequest<String> request, UUID uuid) {
        var book = Cache.getInstance().getBookDetail(uuid);
        var copies = new MediumCopyService().getCopies(new BookDto.BookDtoBuilder(uuid).build());
        var reservations =
            new ReservationService().getReservations(new BookDto.BookDtoBuilder(uuid).build());

        var response = new JsonObject();
        response.put("detail", book);
        response.put("copies", copies);
        response.put("reservations", reservations);
        return HttpResponse.ok(response);
    }

    /**
     * Get dvd details with copies and reservations.
     *
     * @param request http request
     * @param uuid    dvd uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "dvd/{uuid}")
    public HttpResponse<JsonObject> dvd(HttpRequest<String> request, UUID uuid) {
        var dvd = Cache.getInstance().getDvdDetail(uuid);
        var copies = new MediumCopyService().getCopies(new DvdDto.DvdDtoBuilder(uuid).build());
        var reservations =
            new ReservationService().getReservations(new DvdDto.DvdDtoBuilder(uuid).build());

        var response = new JsonObject();
        response.put("detail", dvd);
        response.put("copies", copies);
        response.put("reservations", reservations);
        return HttpResponse.ok(response);
    }

    /**
     * Get game details with copies and reservations.
     *
     * @param request http request
     * @param uuid    game uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "game/{uuid}")
    public HttpResponse<JsonObject> game(HttpRequest<String> request, UUID uuid) {
        var game = Cache.getInstance().getGameDetail(uuid);
        var copies = new MediumCopyService().getCopies(new GameDto.GameDtoBuilder(uuid).build());
        var reservations =
            new ReservationService().getReservations(new GameDto.GameDtoBuilder(uuid).build());

        var response = new JsonObject();
        response.put("detail", game);
        response.put("copies", copies);
        response.put("reservations", reservations);
        return HttpResponse.ok(response);
    }
}