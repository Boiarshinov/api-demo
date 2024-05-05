package dev.boiarshinov.api.rerunnable.jobs.api;

import dev.boiarshinov.api.rerunnable.jobs.operation.Operation;
import dev.boiarshinov.api.rerunnable.jobs.operation.OperationExecutor;
import dev.boiarshinov.api.rerunnable.jobs.operation.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rerunnable-jobs")
public class OperationEndpoint {

    private final OperationExecutor operationExecutor;

    public OperationEndpoint(OperationExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    @GetMapping("/operation/{operationId}")
    public OperationResponse getOperation(@PathVariable String operationId) {
        Operation operation = operationExecutor.getOperation(operationId);
        return DumbOperationResponse.fromOperation(operation);
    }

    record DumbOperationResponse(
            String id,
            Status status,
            Object result,
            Object metadata
    ) implements OperationResponse<Object, Object> {
        static DumbOperationResponse fromOperation(Operation operation) {
            return new DumbOperationResponse(
                    operation.getId(),
                    operation.getStatus(),
                    operation.getResult(),
                    operation.getMetadata()
            );
        }
    }
}
