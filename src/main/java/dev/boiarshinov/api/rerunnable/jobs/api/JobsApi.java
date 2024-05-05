package dev.boiarshinov.api.rerunnable.jobs.api;

import dev.boiarshinov.api.rerunnable.jobs.Execution;
import dev.boiarshinov.api.rerunnable.jobs.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/rerunnable-jobs")
public interface JobsApi {

    @PostMapping("/job")
    Job createJob(@RequestBody JobRequest jobRequest);

    @GetMapping("/job/{jobId}")
    Job getJob(@PathVariable String jobId);

    @DeleteMapping("/job/{jobId}")
    void deleteJob(@PathVariable String jobId);

    @PostMapping("/job/{jobId}/run")
    JobOperationResponse runJob(@PathVariable String jobId);


    //todo filters
    @GetMapping("/job/{jobId}/executions")
    List<Execution> getExecutions(@PathVariable String jobId);

    @GetMapping("/job/{jobId}/executions/{executionId}")
    Execution getExecution(@PathVariable String jobId, @PathVariable String executionId);

    record JobRequest(
            Map<String, String> properties
    ) {}
}
