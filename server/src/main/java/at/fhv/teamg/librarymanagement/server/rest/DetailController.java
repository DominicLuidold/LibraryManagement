package at.fhv.teamg.librarymanagement.server.rest;

import static at.fhv.teamg.librarymanagement.server.rest.Rest.ADMIN;
import static at.fhv.teamg.librarymanagement.server.rest.Rest.LIBRARIAN;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDetailsDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDetailsDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.BookDetailsDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.UUID;
import org.apache.camel.json.simple.JsonObject;

@Secured(SecurityRule.IS_AUTHENTICATED)
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
    @Secured({ADMIN, LIBRARIAN})
    @Operation(summary = "Get the details of a book", description = "Get the details of a"
        + "book with all attributes.")
    @ApiResponse(responseCode = "200", description = "Book details")
    public HttpResponse<BookDetailsDto> book(HttpRequest<String> request, UUID uuid) {
        var book = Cache.getInstance().getBookDetail(uuid);
        var copies = new MediumCopyService().getCopies(new BookDto.BookDtoBuilder(uuid).build());
        var reservations =
            new ReservationService().getReservations(new BookDto.BookDtoBuilder(uuid).build());

        BookDetailsDto dto = new BookDetailsDto.BookDetailsDtoBuilder().details(book).copies(copies)
            .reservations(reservations).build();

        return HttpResponse.ok(dto);
    }

    /**
     * Get dvd details with copies and reservations.
     *
     * @param request http request
     * @param uuid    dvd uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "dvd/{uuid}")
    @Secured({ADMIN, LIBRARIAN})
    @Operation(summary = "Get the details of a DVD", description = "Get the details of a"
        + "DVD with all attributes.")
    @ApiResponse(responseCode = "200", description = "DVD details")
    public HttpResponse<DvdDetailsDto> dvd(HttpRequest<String> request, UUID uuid) {
        var dvd = Cache.getInstance().getDvdDetail(uuid);
        var copies = new MediumCopyService().getCopies(new DvdDto.DvdDtoBuilder(uuid).build());
        var reservations =
            new ReservationService().getReservations(new DvdDto.DvdDtoBuilder(uuid).build());

        DvdDetailsDto dto = new DvdDetailsDto.DvdDetailsDtoBuilder().details(dvd).copies(copies)
            .reservations(reservations).build();

        return HttpResponse.ok(dto);
    }

    /**
     * Get game details with copies and reservations.
     *
     * @param request http request
     * @param uuid    game uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "game/{uuid}")
    @Secured({ADMIN, LIBRARIAN})
    @Operation(summary = "Get the details of a game", description = "Get the details of a"
        + "game with all attributes.")
    @ApiResponse(responseCode = "200", description = "Game details")
    public HttpResponse<GameDetailsDto> game(HttpRequest<String> request, UUID uuid) {
        var game = Cache.getInstance().getGameDetail(uuid);
        var copies = new MediumCopyService().getCopies(new GameDto.GameDtoBuilder(uuid).build());
        var reservations =
            new ReservationService().getReservations(new GameDto.GameDtoBuilder(uuid).build());

        GameDetailsDto dto =
            new GameDetailsDto.GameDetailsDtoBuilder().details(game).copies(copies)
            .reservations(reservations).build();

        return HttpResponse.ok(dto);
    }
}