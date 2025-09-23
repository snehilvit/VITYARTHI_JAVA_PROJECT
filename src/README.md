Campus Course & Records Manager (CCRM)
1. Project Overview
CCRM is a console-based Java application designed to manage students, courses, enrollments, and grades for an educational institution. It is built using Java SE and demonstrates core object-oriented principles, modern file I/O with NIO.2, and several key Java APIs and design patterns.

How to Run
Prerequisites: JDK 11 or higher.

Compile: Navigate to the src directory and run:

javac edu/ccrm/cli/Main.java

Run: From the src directory, execute the main class:

java edu.ccrm.cli.Main

2. Java Platform Concepts
Evolution of Java
1995: Java 1.0 released by Sun Microsystems.

1998: J2SE 1.2 (Java 2) released, introducing Swing and Collections.

2004: J2SE 5.0 released with major features like Generics, Enums, and Annotations.

2014: Java SE 8 released, introducing Lambdas, Streams API, and a new Date/Time API.

2018: Java SE 11 released as a new Long-Term Support (LTS) version.

Present: Java follows a faster 6-month release cycle, with LTS versions every 2 years.

Java ME vs. SE vs. EE
Feature

Java ME (Micro Edition)

Java SE (Standard Edition)

Java EE (Enterprise Edition)

Purpose

Mobile, embedded devices, IoT

Desktop apps, servers, core Java dev

Large-scale, web, enterprise apps

Core API

Subset of Java SE API + specific libs

The core Java programming platform

Superset of Java SE API

Key Techs

MIDlets, CLDC, CDC

JDK, JRE, JVM, Swing, AWT, JDBC, NIO.2

Servlets, JSP, EJB, JPA, REST APIs

Example App

Old feature phone games

This CCRM Project, Eclipse IDE

Online banking, e-commerce sites

JDK vs. JRE vs. JVM
JVM (Java Virtual Machine): An abstract machine that provides the runtime environment in which Java bytecode can be executed. It interprets the compiled .class files. It is platform-dependent.

JRE (Java Runtime Environment): A software package that contains what is required to run a Java program. It includes the JVM, standard libraries, and other components. It does not contain development tools like compilers.

JDK (Java Development Kit): The full-featured SDK for Java. It contains everything in the JRE, plus development tools like the compiler (javac), debugger (jdb), and archiver (jar). You need the JDK to write and compile Java code.

Interaction: A developer writes Java code (.java) and uses the JDK to compile it into bytecode (.class). To run the program, the JRE starts up a JVM instance, which loads, verifies, and executes the bytecode.

3. Setup Screenshots
(This is where you would place your actual screenshots)

JDK Installation Verification (java -version):

Eclipse Project Setup & Run Configuration:

Program Running (Sample Operations):

4. Syllabus Topic to Code Mapping
Syllabus Topic

File / Class / Method where demonstrated

Packages

edu.ccrm.cli, edu.ccrm.domain, edu.ccrm.service, etc.

OOP - Encapsulation

Person.java, Student.java (private fields with public getters)

OOP - Inheritance

Student.java and Instructor.java extend Person.java

OOP - Abstraction

Person.java is an abstract class with an abstract getProfile() method.

OOP - Polymorphism

student.getProfile() would call the specific implementation in Student.

Enums with Constructors

Grade.java, Semester.java

Design Pattern - Singleton

AppConfig.java (getInstance() method)

Design Pattern - Builder

Course.java (static nested Builder class)

Exception Handling (Custom)

StudentNotFoundException.java, thrown in manageEnrollments()

Exception Handling (try-catch)

Main.java in methods like manageEnrollments() and manageGrading().

NIO.2 File I/O

FileService.java (uses Path, Paths, Files.lines, Files.walk)

Java Streams API

CourseService.java (filtering), FileService.java (processing lines)

Java Date/Time API

FileService.java (used for timestamped backup folder names)

Recursion

DirectoryUtils.java (calculateDirectorySize() method)

Lambdas & Func. Interfaces

Used with Streams API, e.g., students.stream().filter(s -> ...)

