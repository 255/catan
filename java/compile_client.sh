#!/bin/bash
# compile the client code without ant
javac src/client/main/Catan.java src/*/*/*.java test/*/*/*.java -cp src:lib/gson-2.2.4.jar:lib/hamcrest-core-1.3.jar:lib/junit-4.11.jar
