package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.apache.camel.json.simple.JsonObject;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/topic")
public class TopicController {
    /**
     * Get all topics.
     *
     * @param request http request
     * @return http response
     */
    @Get(produces = MediaType.TEXT_JSON)
    @Operation(summary = "Get all topics", description = "Get all available topics")
    @ApiResponse(responseCode = "200", description = "List of topics")
    public HttpResponse<List<TopicDto>> all(HttpRequest<String> request) {
        var topics = Cache.getInstance().getAllTopics();
        return HttpResponse.ok(topics);
    }
}
