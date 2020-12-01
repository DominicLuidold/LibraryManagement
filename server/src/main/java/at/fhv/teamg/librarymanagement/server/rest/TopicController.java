package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.rmi.Cache;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.apache.camel.json.simple.JsonObject;

@Controller("/topic")
public class TopicController {
    /**
     * Get all topics.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON)
    public HttpResponse<JsonObject> all(HttpRequest<String> request) {
        var topics = Cache.getInstance().getAllTopics();
        var response = new JsonObject();
        response.put("topics", topics);
        return HttpResponse.ok(response);
    }
}
