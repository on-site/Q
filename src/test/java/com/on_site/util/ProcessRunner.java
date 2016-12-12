package com.on_site.util;

import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * Helper class to build and run a process, capture the output, obtain the exit
 * value, and time out after a maximum amount of time.
 *
 * @author Mike Virata-Stone
 */
public class ProcessRunner {
    private static final long DEFAULT_TIMEOUT = 5000;
    private final ProcessBuilder processBuilder;
    private Process process;
    private OutputReader reader;
    private ProcessTimer timer;

    public ProcessRunner(String... args) {
        processBuilder = new ProcessBuilder(args).redirectErrorStream(true);
    }

    public void start() throws IOException {
        start(DEFAULT_TIMEOUT);
    }

    public void start(long timeoutMs) throws IOException {
        if (process != null) {
            throw new IllegalStateException("Process has already been started!");
        }

        try {
            process = processBuilder.start();
            process.getOutputStream().close();
            reader = new OutputReader().start();
            timer = new ProcessTimer().start();
            timer.join(timeoutMs);
            reader.join();
        } finally {
            // In case there is an error before timer.join is called, make extra
            // sure the process doesn't stick around.
            process.destroy();
        }
    }

    public String getCommand() {
        return Joiner.on(" ").join(processBuilder.command());
    }

    public String getOutput() {
        return reader.getOutput();
    }

    public int getExitValue() {
        return timer.getExitValue();
    }

    private class OutputReader implements Runnable {
        private final Thread thread;
        private final ByteArrayOutputStream bytes;

        public OutputReader() {
            thread = new Thread(this);
            bytes = new ByteArrayOutputStream();
        }

        public OutputReader start() {
            thread.start();
            return this;
        }

        public String getOutput() {
            return bytes.toString();
        }

        @Override
        public void run() {
            try {
                try (InputStream input = process.getInputStream()) {
                    ByteStreams.copy(input, bytes);
                }
            } catch (IOException e) {
            }
        }

        public void join() {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    private class ProcessTimer implements Runnable {
        private final Thread thread;
        private boolean finished = false;
        private volatile boolean completed = false;

        public ProcessTimer() {
            thread = new Thread(this);
        }

        public ProcessTimer start() {
            thread.start();
            return this;
        }

        public int getExitValue() {
            if (finished) {
                return process.exitValue();
            } else {
                return -1;
            }
        }

        @Override
        public void run() {
            try {
                process.waitFor();
                completed = true;
            } catch (InterruptedException e) {
            }
        }

        public void join(long timeoutMs) {
            try {
                try {
                    thread.join(timeoutMs);
                } catch (InterruptedException e) {
                    completed = false;
                }
            } finally {
                if (!completed) {
                    process.destroy();
                } else {
                    finished = true;
                }
            }
        }
    }
}
