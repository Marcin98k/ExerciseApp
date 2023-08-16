package com.example.exerciseapp.mClasses;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BackgroundTask {

    public static void executeWithLoading(Runnable task, Runnable onPreExecute,
                                          Runnable onPostExecute) {

        onPreExecute.run();

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            task.run();
            onPostExecute.run();
        });
    }
}
