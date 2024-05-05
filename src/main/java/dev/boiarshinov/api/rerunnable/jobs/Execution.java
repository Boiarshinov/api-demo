package dev.boiarshinov.api.rerunnable.jobs;

public record Execution (
        String id,
        Job job,
        String result
) { }
