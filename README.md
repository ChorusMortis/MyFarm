# CCPROG3_MP
MP for CCPROG3 AY Term 1, 2022-2023.

# Compile and Usage
1. Clone the repository.
2. Launch terminal (and go) in `MCO1/src`.
3. To compile the program and run it directly, execute the following commands:
```
javac -d "./build/" "./app/Application.java"
java -cp "./build/" app.Application
```
4. Alternatively, the program can be packaged as a JAR file:
```
jar cfve "MyFarm.jar" app.Application -C "./build/" .
java -jar "MyFarm.jar"
```