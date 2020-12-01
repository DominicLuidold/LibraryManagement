package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.rmi.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.camel.json.simple.JsonObject;

@Controller("/search")
public class SearchController {
    /**
     * Search books.
     *
     * @param request http request
     * @param title   title string
     * @param author  author string
     * @param isbn13  isbn13 string
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
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "dvd")
    public HttpResponse<JsonObject> dvd(HttpRequest<String> request) {

        var response = new JsonObject();
        response.put("dvd", "todo");
        return HttpResponse.ok(response);
    }

    /**
     * Search games.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON, uri = "game")
    public HttpResponse<JsonObject> game(HttpRequest<String> request) {

        var response = new JsonObject();
        response.put("game", "todo");
        return HttpResponse.ok(response);
    }
}
