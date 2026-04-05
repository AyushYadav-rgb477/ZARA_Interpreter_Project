ZARA Interpreter
Overview

ZARA Interpreter is a custom scripting language interpreter built in Java. It parses and executes .zara programs supporting basic programming constructs 
such as variables, expressions, conditionals, and loops.

Features
Variable handling
Arithmetic expressions
Conditional statements (if)
Looping (repeat)
Print/output support
Simple and extensible design
Project Structure
Zara_Project/
 ├── src/
 │    └── com/zara/*.java
 ├── examples/
 │    ├── program1.zara
 │    ├── program2.zara
 │    └── program5.zara
 ├── README.md
 ├── .gitignore
How to Run
1. Compile
javac -d out src/com/zara/*.java
2. Create JAR
jar cfe zara.jar com.zara.Main -C out .
3. Run Program
java -jar zara.jar examples/program5.zara
