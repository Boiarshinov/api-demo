package dev.boiarshinov.api.rerunnable.jobs;

import java.util.Map;

public record Job(
        String id,
        Map<String, String> properties
) {}
