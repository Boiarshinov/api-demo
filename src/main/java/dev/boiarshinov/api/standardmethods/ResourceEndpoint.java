package dev.boiarshinov.api.standardmethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/resources")
@RestController
public class ResourceEndpoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public Resource getById(@PathVariable String id, @RequestParam List<String> fieldMask) {
        log.info("Params: {}", fieldMask);
        return new Resource(id, "name");
    }

    @PostMapping()
    public Resource create(@RequestBody CreateRequest request) {
        return new Resource("id", request.name);
    }

    @GetMapping("/{id}/status")
    public Map<String, String> status(@PathVariable String id) {
        return Map.of("status", "fine");
    }

    public record Resource(
            String id,
            String name
    ) {}

    public record CreateRequest(
            String name
    ) {}
}
