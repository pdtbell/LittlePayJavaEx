# LittlePayJavaEx
LittlePay Java coding exercise

Author: Pete Bell

GitHub Repository: https://github.com/pdtbell/LittlePayJavaEx

Assumptions
    • An OFF tap will occur after an ON tap.
    • All StopIDs include a Stop{n}, where n is an integer and the stop number.
    • Only 3 stops are allocated for.
    • I didn’t have time to write in the unit tests. Project includes all dependencies and structure to support Junit jupiter.
    • Uniquely identifying a tap to a traveler is limited to CompanyID.BusID.PAN for the purposes of this exercise.
    • Execution arguments may be supplied but default values of taps: res/taps.csv and trips: res/trips.csv
    
Usage
  Clone the github repo to a local copy
  Building : mvn clean compile assembly:single
  Execution: java -jar target\LittlePayJavaEx-jar-with-dependencies.jar res\taps.csv res\trips.csv
  Unit Tests: mvn test (Note: Unit Tests are empty)

Environment
  Java 11 (Maven)
  Plugins:
    • Maven-compiler-plugin 3.1
    • Maven-dependency-plugin (Required libraries copied to target/libs/)
    • maven-jar-plugin
    • maven-surefire-plugin (JUnit)
    • maven-failsafe-plugin (JUnit)
    • maven-assembly-plugin (JUnit)
  Dependencies:
    • org.apache.commons commons-csv
    • JUnit 5
      ◦ junit-platform-runner (test scope) : provides API and tools used by the IDEs.
      ◦ junit-jupiter-api (test scope) : provides classes and annotations for writing tests, including the @Test annotation. It is transitively included when we include junit-jupiter-engine.
      ◦ junit-jupiter-engine (test scope) : implementation of the Engine API for JUnit Jupiter.
