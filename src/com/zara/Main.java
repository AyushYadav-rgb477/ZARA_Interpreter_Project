package com.zara;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {
    public static void main(String[] args) throws Exception {
        String sourceCode = Files.readString(Paths.get(args[0]));
        Interpreter interpreter = new Interpreter();
        interpreter.run(sourceCode);
    }
}
