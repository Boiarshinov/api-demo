package dev.boiarshinov.api.rerunnable.jobs;

import dev.boiarshinov.api.idgen.IdGenerator;
import dev.boiarshinov.api.rerunnable.jobs.api.JobsApi;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JobService {

    private final IdGenerator idGenerator;

    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    public JobService(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Job createJob(JobsApi.JobRequest jobRequest) {
        String id = idGenerator.generate(IdGenerator.ResourceType.JOB);
        Job job = new Job(id, jobRequest.properties());
        jobs.put(id, job);
        return job;
    }

    public Job getJob(String id) {
        return jobs.get(id);
    }

    public void deleteJob(String id) {
        jobs.remove(id);
    }
}
