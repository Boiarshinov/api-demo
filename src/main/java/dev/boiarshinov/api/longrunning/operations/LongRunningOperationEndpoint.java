package dev.boiarshinov.standardmethods.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/long")
public class LongRunningOperationEndpoint {

    @GetMapping("operation/{operationId}")
    <R extends Result, M> Operation<R, M> getOperationStatus(@PathVariable String operationId) {
        return null;
    }

    record Operation<R extends Result, M>(
            String id,
            Status status,
            R result,
            M metadata
    ) {}

    enum Status {
        IN_PROGRESS,
        COMPLETE,
        ERROR
    }

    interface Result {}

    record OperationError(
            String code,
            String message,
            Object details
    ) implements Result {}
}
