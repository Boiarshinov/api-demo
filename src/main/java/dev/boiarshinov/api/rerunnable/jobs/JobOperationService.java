package dev.boiarshinov.api.rerunnable.jobs;

import dev.boiarshinov.api.idgen.IdGenerator;
import dev.boiarshinov.api.rerunnable.jobs.operation.Operation;
import dev.boiarshinov.api.rerunnable.jobs.operation.OperationExecutor;
import dev.boiarshinov.api.rerunnable.jobs.operation.Status;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class JobOperationService {

    private final OperationExecutor operationExecutor;
    private final ExecutionService executionService;
    private final IdGenerator idGenerator;

    public JobOperationService(
            OperationExecutor operationExecutor,
            ExecutionService executionService,
            IdGenerator idGenerator
    ) {
        this.operationExecutor = operationExecutor;
        this.executionService = executionService;
        this.idGenerator = idGenerator;
    }

    public Operation<String, String> createOperation(Job job) {
        String id = idGenerator.generate(IdGenerator.ResourceType.OPERATION);
        JobOperation jobOperation = new JobOperation(id, job, result -> executionService.createExecution(job, result));

        operationExecutor.runOperation(jobOperation);
        return jobOperation;
    }

    private static class JobOperation implements Operation<String, String> {

        private final String id;
        private final Job job;
        private final Consumer<String> callback;
        private Status status = Status.IN_PROGRESS;
        private String result = null;

        private long startTime;
        private long finishTime;

        public JobOperation(String id, Job job, Consumer<String> callback) {
            this.id = id;
            this.job = job;
            this.callback = callback;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public Status getStatus() {
            return status;
        }

        @Override
        public String getResult() {
            return result;
        }

        @Override
        public String getMetadata() {
            long runTime;
            if (status != Status.IN_PROGRESS) {
                runTime = finishTime - startTime;
            } else {
                runTime = System.currentTimeMillis() - startTime;
            }
            return "Run time: %d".formatted(runTime);
        }

        @Override
        public String call() {
            startTime = System.currentTimeMillis();
            try {
                Thread.sleep(10000);
                result = "complete";
                status = Status.COMPLETE;
            } catch (Exception e) {
                result = "error";
                status = Status.ERROR;
            }
            callback.accept(result);
            finishTime = System.currentTimeMillis();
            return result;
        }
    }
}
