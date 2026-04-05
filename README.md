🚀 ZARA Interpreter

A lightweight custom scripting language interpreter built in Java.
It parses and executes .zara programs supporting variables, expressions, conditionals, and loops.

✨ Features

🧮 Arithmetic expressions
🔁 Looping (repeat)
🔀 Conditional execution (if)
📦 Variable handling
🖨️ Output printing
⚙️ Simple and extensible architecture

📂 Project Structure

Zara_Project/
├── src/com/zara/        # Interpreter source code
├── examples/            # Sample Zara programs
│   ├── program1.zara
│   ├── program2.zara
│   └── program5.zara
├── README.md
└── .gitignore

⚙️ How to Run

1️⃣ Compile
javac -d out src/com/zara/*.java
2️⃣ Create Executable JAR
jar cfe zara.jar com.zara.Main -C out .
3️⃣ Run Program
java -jar zara.jar examples/program5.zara

🧪 Example

Sample .zara Program
show "Hello, Zara!";
Output
Hello, Zara!
🧠 How It Works
1. Tokenizer → Breaks input into tokens
2.Parser → Builds syntax structure
3.Interpreter → Executes instructions
