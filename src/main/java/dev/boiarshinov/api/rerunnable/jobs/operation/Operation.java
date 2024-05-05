package dev.boiarshinov.api.rerunnable.jobs.operation;

import java.util.concurrent.Callable;

public interface Operation<R,M> extends Callable<R> {
    String getId();
    Status getStatus();
    R getResult();
    M getMetadata();
}
