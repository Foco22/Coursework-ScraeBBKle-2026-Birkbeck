# First command
javac -d out/production $(find src/main/pij -name "*.java")                                                                                             
                                                                                                                                                          
# Compilar todos los tests                                                                                                                              
javac -d out/test -cp "lib/junit-platform-console-standalone-1.10.1.jar:out/production" $(find src/main/test -name "*.java")                            
                                                                                                                                                        
# Ejecutar tests                                                                                                                                        
java -jar lib/junit-platform-console-standalone-1.10.1.jar -cp "out/test:out/production" --scan-classpath