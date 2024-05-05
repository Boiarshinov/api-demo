package dev.boiarshinov.api.rerunnable.jobs;

import dev.boiarshinov.api.idgen.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ExecutionService {

    private final IdGenerator idGenerator;

    Map<String, Execution> executions = new ConcurrentHashMap<>();

    public ExecutionService(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Execution createExecution(Job job, String result) {
        String id = idGenerator.generate(IdGenerator.ResourceType.EXECUTION);
        Execution execution = new Execution(id, job, result);
        executions.put(id, execution);
        return execution;
    }

    public List<Execution> listExecutions() {
        return new ArrayList<>(executions.values());
    }

    public Execution getExecution(String executionId) {
        return executions.get(executionId);
    }
}
