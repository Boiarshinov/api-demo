package dev.boiarshinov.api.rerunnable.jobs;

import dev.boiarshinov.api.idgen.IdGenerator;
import dev.boiarshinov.api.rerunnable.jobs.operation.Operation;
import dev.boiarshinov.api.rerunnable.jobs.operation.OperationExecutor;
import dev.boiarshinov.api.rerunnable.jobs.operation.Status;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

    private final OperationExecutor operationExecutor;
    private final ExecutionService executionService;
    private final IdGenerator idGenerator;

    public OperationService(OperationExecutor operationExecutor, ExecutionService executionService, IdGenerator idGenerator) {
        this.operationExecutor = operationExecutor;
        this.executionService = executionService;
        this.idGenerator = idGenerator;
    }

    public Operation<String, String> createOperation(Job job) {
        String id = idGenerator.generate(IdGenerator.ResourceType.OPERATION);
        JobOperation jobOperation = new JobOperation(id, job, () -> executionService.createExecution(job));

        operationExecutor.runOperation(jobOperation);

        return jobOperation;
    }

    private static class JobOperation implements Operation<String, String> {

        private final String id;
        private final Job job;
        private final Runnable callback;
        private Status status = Status.IN_PROGRESS;
        private String result = null;
        private String metadata = null;

        public JobOperation(String id, Job job, Runnable callback) {
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
            return metadata;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(10000);
                callback.run();
                result = "complete";
                status = Status.COMPLETE;
            } catch (Exception e) {
                result = "error";
                status = Status.ERROR;
            }
            return result;
        }
    }
}
