package dev.boiarshinov.api.rerunnable.jobs.api;

import dev.boiarshinov.api.rerunnable.jobs.operation.Status;

public interface OperationResponse<R, M> {
    String id();
    Status status();
    R result();
    M metadata();
}
