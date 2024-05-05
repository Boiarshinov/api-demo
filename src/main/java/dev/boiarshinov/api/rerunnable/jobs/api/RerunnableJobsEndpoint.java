package dev.boiarshinov.api.rerunnable.jobs.api;

import dev.boiarshinov.api.rerunnable.jobs.*;
import dev.boiarshinov.api.rerunnable.jobs.operation.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RerunnableJobsEndpoint implements JobsApi {

    private final JobService jobService;
    private final ExecutionService executionService;
    private final JobOperationService jobOperationService;

    public RerunnableJobsEndpoint(
            JobService jobService,
            ExecutionService executionService,
            JobOperationService jobOperationService
    ) {
        this.jobService = jobService;
        this.executionService = executionService;
        this.jobOperationService = jobOperationService;
    }

    @Override
    public Job createJob(JobRequest jobRequest) {
        return jobService.createJob(jobRequest);
    }

    @Override
    public Job getJob(String jobId) {
        return jobService.getJob(jobId);
    }

    @Override
    public void deleteJob(String jobId) {
        jobService.deleteJob(jobId);
    }


    @Override
    public JobOperationResponse runJob(String jobId) {
        Job job = jobService.getJob(jobId);
        Operation<String, String> operation = jobOperationService.createOperation(job);
        return JobOperationResponse.fromOperation(operation);
    }

    @Override
    public List<Execution> getExecutions(String jobId) {
        return executionService.listExecutions();
    }

    @Override
    public Execution getExecution(String jobId, String executionId) {
        return executionService.getExecution(executionId);
    }
}
