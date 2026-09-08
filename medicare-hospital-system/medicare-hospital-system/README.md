# MediCare Hospital Admission System — PROG6112 Assignment 1

## Project Structure
```
medicare-hospital-system/
├── src/medicare/            All main source files (package: medicare)
│   ├── Patient.java
│   ├── PatientCategory.java
│   ├── Inpatient.java
│   ├── Bed.java
│   ├── BedManager.java
│   ├── PatientManager.java
│   ├── HospitalController.java
│   ├── HospitalSystem.java         <- main() entry point
│   ├── DuplicatePatientIDException.java
│   ├── PatientNotFoundException.java
│   ├── InvalidBedOperationException.java
│   ├── BedAlreadyOccupiedException.java
│   └── NoBedAvailableException.java
├── test/medicare/
│   └── HospitalSystemTest.java     <- JUnit 5 test class
├── lib/                             <- put junit-platform-console-standalone.jar here
├── bin/                             <- compiled .class output (generated)
└── README.md
```

## How to Run (plain javac/java — no IDE needed)
```bash
cd medicare-hospital-system
javac -d bin src/medicare/*.java
java -cp bin medicare.HospitalSystem
```

## How to Run the Unit Tests
The test class uses JUnit 5 (Jupiter). Easiest options:

**Option A — Eclipse / IntelliJ (recommended for this assignment)**
1. Import this folder as a Java project.
2. Mark `src` as the Sources Root and `test` as the Test Sources Root.
3. Add JUnit 5 to the project (Eclipse: right-click project → Build Path → Add Library → JUnit → JUnit 5; IntelliJ prompts automatically when you open a test file).
4. Right-click `HospitalSystemTest.java` → Run As/Run 'HospitalSystemTest'.

**Option B — Command line with the JUnit standalone jar**
1. Download `junit-platform-console-standalone-1.10.x.jar` and place it in `lib/`.
2. Compile and run:
```bash
javac -d bin src/medicare/*.java
javac -cp bin:lib/junit-platform-console-standalone-1.10.x.jar -d bin test/medicare/*.java
java -jar lib/junit-platform-console-standalone-1.10.x.jar -cp bin --scan-class-path
```



