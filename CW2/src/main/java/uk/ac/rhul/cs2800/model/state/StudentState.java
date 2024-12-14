package uk.ac.rhul.cs2800.model.state;

import uk.ac.rhul.cs2800.model.Student;

/**
 * Abstract class representing a state of a student. Defines common behavior for all student states.
 */
public abstract class StudentState {
  private Student student;

  /**
   * Abstract class representing a state of a student. Defines common behavior for all student
   * states.
   */
  StudentState(Student student) {
    this.student = student;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Prints the average score for the student. This method is meant to be overridden by concrete
   * states.
   */
  public void printAverageScore() {
    // Empty... like my brain rn...
  }
}
