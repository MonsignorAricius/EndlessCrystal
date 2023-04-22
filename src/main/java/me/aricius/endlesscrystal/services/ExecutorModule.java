package me.aricius.endlesscrystal.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorModule implements IModule {
    private ExecutorService service = null;

    @Override
    public void starting() {
        if (this.service != null) {
            throw new IllegalStateException("An ExecutorModule instance may only be started once!");
        }
        this.service = Executors.newSingleThreadExecutor();
    }

    @Override
    public void closing() {
        this.service.shutdown();
    }

    public void submit(Runnable task) {
        this.service.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return this.service.submit(task);
    }
}
