package dev.boiarshinov.api.idgen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    void generateId() {
        IdGenerator idGenerator = new IdGenerator();
        String id = idGenerator.generate(IdGenerator.ResourceType.JOB);

        assertEquals("J00000000001", id);
    }

}