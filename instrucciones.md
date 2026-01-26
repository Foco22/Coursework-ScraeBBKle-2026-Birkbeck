# Instructions to Run

Please read the file to run the test and run the game. 

## Compile and Run Tests

```bash
# Compile main source files
javac -d out/production $(find src/main/pij -name "*.java")

# Compile test files
javac -d out/test -cp "lib/junit-platform-console-standalone-1.10.1.jar:out/production" $(find src/main/test -name "*.java")

# Run tests
java -jar lib/junit-platform-console-standalone-1.10.1.jar -cp "out/test:out/production" --scan-classpath
```

## Compile and Run Application

```bash
# Compile
javac -d out src/main/pij/model/*.java src/main/pij/service/*.java src/main/pij/main/*.java src/main/pij/utils/*.java

# Run
java -cp out main.pij.main.Main
```