import bsh.Interpreter;

import java.io.InputStreamReader;

public class Demo {
    public static void main(String[] args) throws Exception {
        Interpreter interpreter = new Interpreter(new InputStreamReader(System.in), System.out, System.err, true);
        interpreter.eval(new InputStreamReader(Demo.class.getResourceAsStream("demo.bsh")));
        interpreter.run();
    }
}
