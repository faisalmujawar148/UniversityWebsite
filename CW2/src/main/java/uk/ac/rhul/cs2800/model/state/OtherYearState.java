package uk.ac.rhul.cs2800.model.state;

import uk.ac.rhul.cs2800.exception.NoGradeAvailableException;
import uk.ac.rhul.cs2800.model.Student;

/**
 * Represents the state of a student in other years (e.g., second year or above).
 */
public class OtherYearState extends StudentState {

  /**
   * Constructs an OtherYearState for the given student.
   *
   * @param student The student to associate with this state.
   */
  public OtherYearState(Student student) {
    super(student);
  }

  /**
   * Prints the average score for a student in other years. If no grade is available, an appropriate
   * message is printed.
   */
  @Override
  public void printAverageScore() {
    super.printAverageScore();
    Student student = this.getStudent();
    try {
      System.out.println("The average score of " + student.getFirstName() + " "
          + student.getLastName() + " is " + student.computeAverage());
    } catch (NoGradeAvailableException e) {
      System.out
          .println(
              "There are no scores for " + student.getFirstName() + " " + student.getLastName());
    }
  }

}
