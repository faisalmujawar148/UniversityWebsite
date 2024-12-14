package uk.ac.rhul.cs2800.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.cs2800.model.state.*;
import uk.ac.rhul.cs2800.observer.*;
import uk.ac.rhul.cs2800.exception.NoGradeAvailableException;
import uk.ac.rhul.cs2800.exception.NoRegistrationException;

public class StudentTest {

  private Student student;

  @BeforeEach
  void beforeEach() {
    student = new Student();
    student.setId(1);
    student.setFirstName("John");
    student.setLastName("Doe");
    student.setUsername("zmac000");
    student.setEmail("JohnDoe@AwesomeUniversity.ac.uk");
  }

  @Test
  void studentTest() {
    // Test 1
    assertEquals(1, student.getId());
    assertEquals("John", student.getFirstName());
    assertEquals("Doe", student.getLastName());
    assertEquals("zmac000", student.getUsername());
    assertEquals("JohnDoe@AwesomeUniversity.ac.uk", student.getEmail());
  }

  @Test
  void gradesTest() throws NoGradeAvailableException {
    // Test 2
    Student student = new Student();
    // Re-done the list grades
    student.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)))));
    assertEquals(100, student.listGrades().get(0).getScore());

  }

  @Test
  void registrationsTest() throws NoRegistrationException {
    // Test 3
    // Re-done the list registrations
    student.setRegistrations(new ArrayList<>(
        List.of(new Registration(new Module("CS2800", "Software Engineering", true)),
            new Registration(new Module("CS2850", "Operating Systems", true)))));
    assertEquals("CS2850", student.listRegistrations().get(1).getModule().getCode());
  }

  @Test
  void computeAverageTest() throws NoGradeAvailableException {
    // Test 4
    // Re-done the list grades
    student.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)))));
    assertEquals(95, student.computeAverage());
  }

  @Test
  void computeAverageTestWithoutGrades() {
    // Test 5
    // implement empty list + null list test
    assertThrows(NoGradeAvailableException.class, () -> {
      student.setGrades(null);
      student.computeAverage();
    });
    assertThrows(NoGradeAvailableException.class, () -> {
      student.setGrades(new ArrayList<>());
      student.computeAverage();
    });
  }

  @Test
  void getGradeTest() throws NoGradeAvailableException {
    // Test 6
    student.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)),
            new Grade(80, new Module("CS2855", "Databases", true)))));
    assertEquals(new Grade(90, new Module("CS2850", "Operating Systems", true)),
        student.getGrade(new Module("CS2850", "Operating Systems", true)));
    // searching for a grade not there (w/ null)
    assertEquals(null, student.getGrade(null));
    // searching for a grade not there (w/ a non-existent module)
    assertEquals(null, student.getGrade(new Module("CS9999", "Secret Module", false)));
    // searching for a grade, with only a partial match
    assertEquals(null, student.getGrade(new Module("CS850", "FAKE FAKE", true)));
  }

  @Test
  void addGradeTest() throws NoGradeAvailableException {
    // Test 7
    student.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)))));
    student.appendGrade(new Grade(80, new Module("CS2855", "Databases", true)));
    assertEquals("CS2855", student.listGrades().get(2).getModule().getCode());

  }



  @Test
  void gradeTestWithoutGrades() {
    // Test 8
    // empty list
    assertThrows(NoGradeAvailableException.class, () -> {
      student.setGrades(null);
      student.getGrade(new Module("CS2850", "Operating Systems", true));
    });
    // null list test
    assertThrows(NoGradeAvailableException.class, () -> {
      student.setGrades(new ArrayList<>());
      student.getGrade(new Module("CS2850", "Operating Systems", true));
    });

  }

  @Test
  void registerModuleTest() throws NoRegistrationException {
    // Test 9
    student.registerModule(new Module("CS2850", "Operating Systems", true));
    assertEquals(new Registration(new Module("CS2850", "Operating Systems", true)),
        student.listRegistrations().get(0));
  }

  @Test
  void registrationsTestWithoutRegistrations() {
    // Test 10
    // Null list
    assertThrows(NoRegistrationException.class, () -> {
      student.setRegistrations(null);
      student.listRegistrations();
    });
    // Empty list
    assertThrows(NoRegistrationException.class, () -> {
      student.setRegistrations(new ArrayList<>());
      student.listRegistrations();
    });
  }

  @Test
  void getGradesWithNullListTest() {
    // Test 11
    student.setGrades(null); // Simulates no grades being set
    assertThrows(NoGradeAvailableException.class, student::listGrades);
  }

  @Test
  void getGradesWithEmptyListTest() {
    // Test 12
    student.setGrades(new ArrayList<>()); // Sets grades to an empty list
    assertThrows(NoGradeAvailableException.class, student::listGrades);
  }

  @Test
  void getGradesWithPopulatedListTest() throws NoGradeAvailableException {
    // Test 13
    List<Grade> gradeList =
        List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)));
    student.setGrades(new ArrayList<>(gradeList));
    assertEquals(gradeList, student.listGrades()); // Confirm that getGrades returns the correct list
  }

  @Test
  void registerModuleSuccessfullyTest() throws NoRegistrationException {
    // tEST 14
    Module newModule = new Module("CS3000", "SUPER SECRET MODULE!", true);
    student.registerModule(newModule);
    assertEquals(new Registration(newModule), student.listRegistrations().get(0));
  }

  @Test
  void registerDuplicateModuleTest() {
    // test 15
    Module module = new Module("CS3000", "SUPER SECRET MODULE!", true);
    // First registration should succeed
    assertDoesNotThrow(() -> student.registerModule(module));
    // Second registration
    assertThrows(NoRegistrationException.class, () -> student.registerModule(module));
  }

  @Test
  void registerMultipleModulesTest() throws NoRegistrationException {
    // Test 16
    Module module1 = new Module("CS3000", "SUPER SECRET MODULE!", true);
    Module module2 = new Module("CS3100", "Machine Learning", true);
    student.registerModule(module1);
    student.registerModule(module2);
    assertEquals(2, student.listRegistrations().size());
    assertEquals(new Registration(module1), student.listRegistrations().get(0));
    assertEquals(new Registration(module2), student.listRegistrations().get(1));
  }

  @Test
  void registerNullModuleTest() {
    // Test 17
    assertThrows(NullPointerException.class, () -> student.registerModule(null));
  }

  @Test
  void firstYearStateTest() throws NoGradeAvailableException {
    // Test 18
    // We can test the print statements by capturing them!
    // i.e we don't have to use the main method for this part
    // Test for FirstYearState

    // Capture output for state-specific logic
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream); // Redirect System.out to capture the printed output

    // Set up state for first-year student with no grades
    StudentState state = new FirstYearState(student);
    student.setState(state);

    // Simulate no grades available
    student.setGrades(new ArrayList<>());

    // Call method to print the average score (which should handle "no grades")
    student.getState().printAverageScore();

    // Expected output when no grades are available
    String expectedOutput =
        "There are no predicted scores for " + student.getFirstName() + " " + student.getLastName();
    assertEquals(expectedOutput + System.lineSeparator(), outputStream.toString());

    // Clear the output stream for the next case
    outputStream.reset();

    // Simulate the student having grades now
    student.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)))));

    // Call method again to print the average score (with grades)
    student.getState().printAverageScore();

    // Expected output when grades are available
    String expectedOutput2 = "The predicted average score of " + student.getFirstName() + " "
        + student.getLastName() + " is " + student.computeAverage();

    // Verify the output with grades
    assertEquals(expectedOutput2 + System.lineSeparator(), outputStream.toString());

    // Restore the original System.out to avoid interference with other tests
    System.setOut(System.out);
  }


  @Test
  void otherYearStateTest() throws NoGradeAvailableException {
    // Test 19
    // Test for OtherYearState

    // Capture output for state-specific logic
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    StudentState state = new OtherYearState(student);
    student.setState(state);
    // Call method specific to this state
    student.getState().printAverageScore();

    // Expected output based on actual average score with no grades
    String expectedOutput =
        "There are no scores for " + student.getFirstName() + " " + student.getLastName();

    // Verify output
    assertEquals(expectedOutput + System.lineSeparator(), outputStream.toString());
    outputStream.reset();
    student.getState().printAverageScore();

    // Expected output based on actual average score with no grades
    String expectedOutput1 =
        "There are no scores for " + student.getFirstName() + " " + student.getLastName();

    // Verify output
    assertEquals(expectedOutput1 + System.lineSeparator(), outputStream.toString());

    outputStream.reset();

    student.setGrades(new ArrayList<>());
    student.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)))));


    // Call method specific to this state
    student.getState().printAverageScore();

    // Expected output based on actual average score
    String expectedOutput2 = "The average score of " + student.getFirstName() + " "
        + student.getLastName() + " is " + student.computeAverage();

    // Verify output
    assertEquals(expectedOutput2 + System.lineSeparator(), outputStream.toString());
  }


  @Test
  void nullStateTest() {
    // Test 20
    // Test when student has no state set
    student.setState(null);
    assertThrows(NullPointerException.class, () -> student.getState().printAverageScore());
  }

  @Test
  void studentStateTest() throws NoGradeAvailableException {
    // Test 21
    // Test when state sets student instead of the other way around

    // Capture output for state-specific logic
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    StudentState state = new OtherYearState(student);
    Student student2 = new Student();
    student2.setId(1);
    student2.setFirstName("John");
    student2.setLastName("Doe");
    student2.setUsername("zmac000");
    student2.setEmail("JohnDoe@AwesomeUniversity.ac.uk");
    student2.setState(state);
    student2.setGrades(
        new ArrayList<>(List.of(new Grade(100, new Module("CS2800", "Software Engineering", true)),
            new Grade(90, new Module("CS2850", "Operating Systems", true)))));
    state.setStudent(student2);

    // Call method specific to this state
    student2.getState().printAverageScore();

    // Expected output based on actual average score
    String expectedOutput = "The average score of " + student2.getFirstName() + " "
        + student2.getLastName() + " is " + student2.computeAverage();
    assertEquals(expectedOutput + System.lineSeparator(), outputStream.toString());

  }


  @Test
  void studentStateTest_failingWithTryCatch() {
    // Test 22
    // Test for failure due to missing state setup
    // Capture output for state-specific logic
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    // Create the state and student2 without setting the state in the student
    Student student2 = new Student();
    student2.setFirstName("John");
    student2.setLastName("Doe");
    StudentState state = new OtherYearState(student);

    // Missing: student2.setState(state);

    try {
      // Attempt to call the method, expecting it to fail
      student2.getState().printAverageScore();
      fail("Expected a NullPointerException to be thrown, but it was not.");
    } catch (NullPointerException e) {
      // Catch the exception and verify its message (optional)
      assertTrue(e.getMessage().contains("Cannot invoke"), "Unexpected exception message.");
    }

    // Clear the output stream after catching the exception
    outputStream.reset();

    // Ensure no unintended output was captured
    assertEquals("", outputStream.toString(), "Unexpected output captured in the stream.");
  }

  @Test
  void setStudentTest() {
    // Test 23
    // Create a minimal concrete implementation of StudentState for testing
    StudentState state = new OtherYearState(null); // Use a concrete subclass

    // Create a Student object
    Student testStudent = new Student();
    testStudent.setId(1);
    testStudent.setFirstName("Test");
    testStudent.setLastName("Student");
    testStudent.setUsername("testStudent");
    testStudent.setEmail("test@student.com");

    // Call the method
    state.setStudent(testStudent);

    // Assert that the state now has the correct student
    assertEquals(testStudent, state.getStudent(),
        "The student was not set correctly in the state.");
  }

  @Test
  void observerManagerTest_Email() {
    // Test 24
    // Capture output for state-specific logic
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    // setup
    Observer emailObserver = new EmailNotificationObserver();
    Observer whatsappObserver = new WhatsAppNotificationObserver();
    ObserverManager manager = ObserverManager.getInstance();
    // subscribe emailObserver
    manager.subscribe(emailObserver);
    // Appendgrade -> i.e update grades -> trigger observer
    Grade grade = new Grade(100, new Module("CS2800", "Software Engineering", true));
    student.appendGrade(grade);
    String message = "A new grade for " + grade.getModule() + " has been added.";
    String expectedOutput = "Message sent via email: " + message;
    assertEquals(expectedOutput + System.lineSeparator(), outputStream.toString());
    System.setOut(printStream);
    outputStream.reset();
    manager.clearObservers();
  }

  @Test
  void observerManagerTest_WhatsApp() {
    // Test 25
    // Capture output for state-specific logic
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    // setup
    Observer emailObserver = new EmailNotificationObserver();
    Observer whatsappObserver = new WhatsAppNotificationObserver();
    ObserverManager manager = ObserverManager.getInstance();
    manager.subscribe(whatsappObserver);
    // trigger observer
    Grade grade = new Grade(100, new Module("CS2800", "Software Engineering", true));
    student.appendGrade(grade);
    String message = "A new grade for " + grade.getModule() + " has been added.";
    String expectedOutput = "Message sent via WhatsApp: " + message;
    assertEquals(expectedOutput + System.lineSeparator(), outputStream.toString());
    outputStream.reset();
    manager.clearObservers();
  }

  








}
