#!/bin/bash
# compile the client code without ant
javac src/client/main/Catan.java src/shared/model/*.java src/client/network/*.java -cp src:lib/gson-2.2.4.jar
