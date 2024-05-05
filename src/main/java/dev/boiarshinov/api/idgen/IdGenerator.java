package dev.boiarshinov.api.idgen;

import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IdGenerator {

    private final Map<ResourceType, AtomicInteger> counters = new EnumMap<>(ResourceType.class);

    public IdGenerator() {
        for (ResourceType resourceType: ResourceType.values()) {
            counters.put(resourceType, new AtomicInteger());
        }
    }

    public String generate(ResourceType resourceType) {
        AtomicInteger atomicInteger = counters.get(resourceType);
        int nextNumber = atomicInteger.incrementAndGet();

        return "%s%011d".formatted(resourceType.mark, nextNumber);
    }

    public enum ResourceType {
        JOB('J'),
        EXECUTION('E'),
        OPERATION('O');

        private final char mark;

        ResourceType(char mark) {
            this.mark = mark;
        }
    }
}
