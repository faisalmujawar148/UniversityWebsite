package uk.ac.rhul.cs2800.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;  

/**
 * A class that represents the grade of a module.
 */
@Entity
@Table(name = "grades",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "module_code"}))
public class Grade {

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne
  @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
  private Student student;

  private Integer score;

  @ManyToOne
  @JoinColumn(name = "module_code", referencedColumnName = "code", nullable = false)
  private Module module;

  /**
   * Default constructor for Grade class.
   */
  public Grade() {}

  /**
   * Constructor for Grade with the score and module.
   *
   * @param score the marks achieved by the student.
   * @param module the module they got these marks in.
   */
  public Grade(Integer score, Module module) {
    this.score = score;
    this.module = module;
  }

  @Override
  public boolean equals(Object o) {
    // Same object reference check
    if (this == o) {
      return true;
    }
    // Null or class mismatch check
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    // Cast to Grade
    Grade grade = (Grade) o;

    // Compare students (handles nulls)
    if (student == null) {
      if (grade.student != null) {
        return false;
      }
    } else if (!student.equals(grade.student)) {
      return false;
    }


    // Compare modules (handles nulls)
    if (module == null ^ grade.module == null) {
      return false;
    }
    if (module != null && !module.equals(grade.module)) {
      return false;
    }
    // Compare scores (handles nulls)
    if (score == null ^ grade.score == null) {
      return false;
    }
    if (score != null && !score.equals(grade.score)) {
      return false;
    }

    return true;
  }


  @Override
  public int hashCode() {
    int result = 17; // Use a prime number as the initial value

    // Hash each field, giving different treatment to null and non-null values
    result = 31 * result + (student != null ? student.hashCode() : 0);
    result = 31 * result + (module != null ? module.hashCode() : 0);
    result = 31 * result + (score != null ? score.hashCode() : 0);

    return result;
  }

  // Getters and Setters
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Integer getScore() {
    return score;
  }

  /**
   * Setter for score.
   *
   * @param score only allows int values b/w 0 and 100
   */
  public void setScore(Integer score) {
    if (score == null || score < 0 || score > 100) {
      throw new IllegalArgumentException("Score must be between 0 and 100");
    }
    this.score = score;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

}
