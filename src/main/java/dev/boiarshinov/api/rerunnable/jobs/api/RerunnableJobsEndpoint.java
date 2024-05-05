package dev.boiarshinov.api.rerunnable.jobs;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RerunnableJobsEndpoint implements JobsApi {

    private final JobService jobService;
    private final ExecutionService executionService;

    public RerunnableJobsEndpoint(JobService jobService, ExecutionService executionService) {
        this.jobService = jobService;
        this.executionService = executionService;
    }

    @Override
    public void createJob(JobRequest jobRequest) {
        jobService.createJob();
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
    public Object runJob(String jobId) {
        Job job = jobService.getJob(jobId);
        return executionService.createExecution(job);
    }

    @Override
    public List<Execution> getExecutions(String jobId) {
        return null;
    }

    @Override
    public Execution getExecution(String jobId, String executionId) {
        return executionService.getExecution(executionId);
    }

    record JobRequest(
            String destinationDir,
            String compressionFormat
    ) {}
}
