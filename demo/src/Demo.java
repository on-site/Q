import bsh.Interpreter;

import java.io.InputStreamReader;

public class Demo {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "bsh".equals(args[0])) {
            bsh();
        }
    }

    public static void bsh() throws Exception {
        Interpreter interpreter = new Interpreter(new InputStreamReader(System.in), System.out, System.err, true);
        interpreter.source("demo/demo.bsh");
        interpreter.run();
    }
}
