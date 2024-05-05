package dev.boiarshinov.api.rerunnable.jobs;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rerunnable-jobs")
public interface JobsApi {

    @PostMapping("/job")
    void createJob(@RequestBody RerunnableJobsEndpoint.JobRequest jobRequest);

    @GetMapping("/job/{jobId}")
    Job getJob(@PathVariable String jobId);

    @DeleteMapping("/job/{jobId}")
    void deleteJob(@PathVariable String jobId);

    @PostMapping("/job/{jobId}/run")
    Object runJob(@PathVariable String jobId);


    //todo filters
    @GetMapping("/job/{jobId}/executions")
    List<Execution> getExecutions(@PathVariable String jobId);

    @GetMapping("/job/{jobId}/executions/{executionId}")
    Execution getExecution(@PathVariable String jobId, @PathVariable String executionId);

}
