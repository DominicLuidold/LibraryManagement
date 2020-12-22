package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/search")
public class SearchController {
    /**
     * Search books.
     *
     * @param request http request
     * @param title   title
     * @param author  author
     * @param isbn13  isbn13
     * @param topic   topic uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "book")
    @Operation(summary = "Search for books", description = "One can search by title, "
        + "author, isb1n13, topic")
    @Parameter(name = "title", description = "Title of the book")
    @Parameter(name = "author", description = "Author of the book")
    @Parameter(name = "isbn13", description = "Isbn13 of the book")
    @Parameter(name = "topic", description = "Name of book's topic")
    @ApiResponse(responseCode = "200", description = "List of book results")
    public HttpResponse<List<BookDto>> book(
        HttpRequest<String> request,
        @Nullable @QueryValue String title,
        @Nullable @QueryValue String author,
        @Nullable @QueryValue String isbn13,
        @Nullable @QueryValue UUID topic
    ) {
        var books = Cache.getInstance()
            .searchBook(
                new BookDto.BookDtoBuilder()
                    .title(title == null ? "" : title)
                    .author(author == null ? "" : author)
                    .isbn13(isbn13 == null ? "" : isbn13)
                    .topic(topic)
                    .build());

        return HttpResponse.ok(books).header("Cache-Control", "no-store");
    }


    /**
     * Search dvds.
     *
     * @param request     http request
     * @param title       title
     * @param director    director
     * @param releaseDate releaseDate
     * @param topic       topic uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "dvd")
    @Operation(summary = "Search for dvds", description = "One can search by title, "
        + "director, releaseDate, topic")
    @Parameter(name = "title", description = "Title of the DVD")
    @Parameter(name = "director", description = "Director of the DVD")
    @Parameter(name = "releaseDate", description = "Release date of the DVD")
    @Parameter(name = "topic", description = "Name of DVD's topic")
    @ApiResponse(responseCode = "200", description = "List of DVD results")
    public HttpResponse<List<DvdDto>> dvd(
        HttpRequest<String> request,
        @Nullable @QueryValue String title,
        @Nullable @QueryValue String director,
        @Format("yyyy-MM-dd") @Nullable @QueryValue LocalDate releaseDate,
        @Nullable @QueryValue UUID topic
    ) {
        var dvds = Cache.getInstance()
            .searchDvd(
                new DvdDto.DvdDtoBuilder()
                    .title(title == null ? "" : title)
                    .director(director == null ? "" : director)
                    .releaseDate(releaseDate == null ? LocalDate.MIN : releaseDate)
                    .topic(topic)
                    .build());

        return HttpResponse.ok(dvds).header("Cache-Control", "no-store");
    }

    /**
     * Search games.
     *
     * @param request   http request
     * @param title     title
     * @param developer developer
     * @param platforms platforms
     * @param topic     topic uuid
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "game")
    @Operation(summary = "Search for games", description = "One can search by title, "
        + "developer, platforms, topic")
    @Parameter(name = "title", description = "Title of the game")
    @Parameter(name = "developer", description = "Developer of the game")
    @Parameter(name = "platforms", description = "Platforms of the game")
    @Parameter(name = "topic", description = "Name of Game's topic")
    @ApiResponse(responseCode = "200", description = "List of game results")
    public HttpResponse<List<GameDto>> game(
        HttpRequest<String> request,
        @Nullable @QueryValue String title,
        @Nullable @QueryValue String developer,
        @Nullable @QueryValue String platforms,
        @Nullable @QueryValue UUID topic
    ) {
        var games = Cache.getInstance()
            .searchGame(
                new GameDto.GameDtoBuilder()
                    .title(title == null ? "" : title)
                    .developer(developer == null ? "" : developer)
                    .platforms(platforms == null ? "" : platforms)
                    .topic(topic)
                    .build()
            );

        return HttpResponse.ok(games).header("Cache-Control", "no-store");
    }
}
