package dev.boiarshinov.api.rerunnable.jobs.operation;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OperationExecutor {

    private final Map<String, Operation> operations = new ConcurrentHashMap<>();

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public <R, M> Operation<R, M> runOperation(Operation<R, M> operation) {
        threadPool.submit(operation);
        operations.put(operation.getId(), operation);

        return operation;
    }

    public Operation getOperation(String operationId) {
        return operations.get(operationId);
    }
}
