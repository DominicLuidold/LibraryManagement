package at.fhv.teamg.librarymanagement.server.rest;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.security.Principal;
import org.apache.camel.json.simple.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/hello")
public class HelloWorldController {
    private static final Logger LOG = LogManager.getLogger(HelloWorldController.class);

    /**
     * Hello World example.
     *
     * @param principal authenticated user
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON)
    public HttpResponse<JsonObject> world(Principal principal) {
        JsonObject json = new JsonObject();
        json.put("hello", principal.getName());
        return HttpResponse.ok(json);
    }
}
