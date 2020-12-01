package at.fhv.teamg.librarymanagement.server.rest;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.apache.camel.json.simple.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller("/hello")
public class HelloWorldController {
    private static final Logger LOG = LogManager.getLogger(HelloWorldController.class);

    /**
     * Hello World example.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON)
    public HttpResponse<JsonObject> world(HttpRequest<String> request) {
        LOG.info("request from " + request.getRemoteAddress());
        JsonObject json = new JsonObject();
        json.put("hello", "world");
        return HttpResponse.ok(json);
    }
}
