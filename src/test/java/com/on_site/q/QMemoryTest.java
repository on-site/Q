package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.util.ProcessRunner;
import com.on_site.util.TestBase;

import java.io.File;

import org.testng.annotations.Test;

public class QMemoryTest extends TestBase {
    @Test
    public void heavyUsageDoesntLeakMemory() throws Exception {
        File javaBin = new File(System.getProperty("java.home"), "bin");
        File java = new File(javaBin, "java");
        String classpath = System.getProperty("java.class.path");
        String className = HeavyUsageDoesntLeakMemory.class.getName();
        ProcessRunner runner = new ProcessRunner(java.getAbsolutePath(), "-Xmx16m", "-cp", classpath, className);
        runner.start(30000);
        assertEquals(runner.getExitValue(), 0, "Process '" + runner.getCommand() + "' finished with an error:\n" + runner.getOutput());
    }

    public static class HeavyUsageDoesntLeakMemory {
        private static final int ITERATIONS = 100;

        public static void main(String[] args) {
            String xml = getXml(1000, 1, "Some Name");

            for (int i = 0; i < ITERATIONS; i++) {
                Q q = $(xml);
                q = q.find("SomeObject").eq(41);
                assertEquals(q.find("id").text(), "42");
            }
        }
    }

    private static String getXml(int size, int startingId, String name) {
        StringBuilder result = new StringBuilder();
        result.append("<root>\n");

        for (int i = 0; i < size; i++) {
            result.append("  <SomeObject>\n");
            result.append("    <id>" + (startingId + i) + "</id>\n");
            result.append("    <name>" + name + i + "</name>\n");
            result.append("  </SomeObject>\n");
        }

        result.append("</root>");
        return result.toString();
    }
}
