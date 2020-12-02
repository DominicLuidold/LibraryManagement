package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.rmi.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.camel.json.simple.JsonObject;

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
    public HttpResponse<JsonObject> book(HttpRequest<String> request,
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
        var response = new JsonObject();
        response.put("books", books);
        return HttpResponse.ok(response);
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
    public HttpResponse<JsonObject> dvd(HttpRequest<String> request,
                                        @Nullable @QueryValue String title,
                                        @Nullable @QueryValue String director,
                                        @Nullable @QueryValue LocalDate releaseDate,
                                        @Nullable @QueryValue UUID topic) {
        var dvds = Cache.getInstance()
            .searchDvd(
                new DvdDto.DvdDtoBuilder()
                    .title(title == null ? "" : title)
                    .director(director == null ? "" : director)
                    .releaseDate(releaseDate == null ? LocalDate.MIN : releaseDate)
                    .topic(topic)
                    .build());

        var response = new JsonObject();
        response.put("dvds", dvds);
        return HttpResponse.ok(response);
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
    public HttpResponse<JsonObject> game(HttpRequest<String> request,
                                         @Nullable @QueryValue String title,
                                         @Nullable @QueryValue String developer,
                                         @Nullable @QueryValue String platforms,
                                         @Nullable @QueryValue UUID topic) {
        var games = Cache.getInstance()
            .searchGame(
                new GameDto.GameDtoBuilder()
                    .title(title == null ? "" : title)
                    .developer(developer == null ? "" : developer)
                    .platforms(platforms == null ? "" : platforms)
                    .topic(topic)
                    .build()
            );

        var response = new JsonObject();
        response.put("games", games);
        return HttpResponse.ok(response);
    }
}
