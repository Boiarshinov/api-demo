package dev.boiarshinov.api.rerunnable.jobs.api;

import dev.boiarshinov.api.rerunnable.jobs.operation.Operation;
import dev.boiarshinov.api.rerunnable.jobs.operation.Status;

public record JobOperationResponse(
        String id,
        Status status,
        String result,
        String metadata
) implements OperationResponse<String, String> {

    static JobOperationResponse fromOperation(Operation<String, String> operation) {
        return new JobOperationResponse(
                operation.getId(),
                operation.getStatus(),
                operation.getResult(),
                operation.getMetadata()
        );
    }
}
