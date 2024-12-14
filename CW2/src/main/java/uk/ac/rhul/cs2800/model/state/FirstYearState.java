package uk.ac.rhul.cs2800.model.state;

import uk.ac.rhul.cs2800.exception.NoGradeAvailableException;
import uk.ac.rhul.cs2800.model.Student;

/**
 * Represents the state of a first-year student.
 */
public class FirstYearState extends StudentState {

  /**
   * Constructs a FirstYearState for the given student.
   *
   * @param student The student to associate with this state.
   */
  public FirstYearState(Student student) {
    super(student);
  }

  /**
   * Prints the predicted average score for a first-year student. If no grade is available, an
   * appropriate message is printed.
   */
  @Override
  public void printAverageScore() {
    super.printAverageScore();
    Student student = this.getStudent();
    try {
      System.out.println(
          "The predicted average score of " + student.getFirstName() + " " + student.getLastName()
              + " is " + student.computeAverage());
    } catch (NoGradeAvailableException e) {
      System.out.println("There are no predicted scores for " + student.getFirstName() + " "
          + student.getLastName());
    }
  }

}
