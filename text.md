javac -d out/production src/main/pij/model/Tile.java src/main/pij/model/Bag.java src/main/pij/main/Main.java

javac -d out/test -cp "lib/junit-platform-console-standalone-1.10.1.jar:out/production" src/main/test/java/main/pij/ScrabbleTest.java

java -jar lib/junit-platform-console-standalone-1.10.1.jar -cp "out/test:out/production" --scan-classpath