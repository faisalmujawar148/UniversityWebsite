package uk.ac.rhul.cs2800.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import uk.ac.rhul.cs2800.exception.NoGradeAvailableException;
import uk.ac.rhul.cs2800.exception.NoRegistrationException;
import uk.ac.rhul.cs2800.model.state.StudentState;
import uk.ac.rhul.cs2800.observer.ObserverManager;

/**
 * A class that represents students at the university.
 *
 */
@Entity
public class Student {
  @Id
  private long id;

  private String firstName;
  private String lastName;
  private String username;
  private String email;
  @ManyToMany(mappedBy = "student")
  private List<Grade> grades = new ArrayList<>();
  @ManyToMany(mappedBy = "student")
  private List<Registration> registrations = new ArrayList<>();
  @Transient
  private StudentState state;
  @Transient
  ObserverManager observerManager = ObserverManager.getInstance();

  public StudentState getState() {
    return state;
  }

  public void setState(StudentState state) {
    this.state = state;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get the list of all the grades.
   *
   * @return grades: list of all the grades
   * @throws NoGradeAvailableException when there is no grade in grades
   */
  public List<Grade> listGrades() throws NoGradeAvailableException {
    if (grades == null || grades.size() < 1) {
      throw new NoGradeAvailableException();
    }
    return grades;
  }

  public void setGrades(List<Grade> grades) {
    this.grades = grades;
  }

  /**
   * Get the list of all registrations.
   *
   * @return registrations: list of all of the registered modules.
   * @throws NoRegistrationException when there is no registered modules in the list registrations
   */
  public List<Registration> listRegistrations() throws NoRegistrationException {
    if (registrations == null || registrations.size() < 1) {
      throw new NoRegistrationException();
    }
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

  /**
   * Computes the average grade score from grades.
   *
   * @return float
   * @throws NoGradeAvailableException if there are no grades in the list
   */
  public Float computeAverage() throws NoGradeAvailableException {
    if (grades == null || grades.size() < 1) {
      throw new NoGradeAvailableException();
    }
    int sum = 0;
    for (int x = 0; x < grades.size(); x++) {
      sum = grades.get(x).getScore() + sum;
    }
    return (float) (sum / grades.size());
  }

  /**
   * Adds a grade to the list grades.
   *
   * @param grade the (score + module) to be stored in the grades list.
   */
  public void appendGrade(Grade grade) {
    grades.add(grade);
    observerManager.notify("A new grade for " + grade.getModule() + " has been added.");
  }

  /**
   * Retrieves the grade from the list of grades, by searching the module name.
   *
   * @param module The name of module where you're searching the grade of it.
   * @return grade
   * @throws NoGradeAvailableException No grades to get grades from.
   */
  public Grade getGrade(Module module) throws NoGradeAvailableException {
    if (grades == null || grades.size() < 1) {
      throw new NoGradeAvailableException();
    }
    for (int x = 0; x < grades.size(); x++) {
      if (grades.get(x).getModule().equals(module)) {
        return (Grade) grades.get(x);
      }
    }
    return null;

  }

  /**
   * Adds a new module registration to the list of registrations.
   *
   * @param module The module to be added to the list of registered modules
   * @throws NoRegistrationException if there are duplicate modules being registered
   * @throws NullPointerException if there any null modules being registered
   */
  public void registerModule(Module module) throws NoRegistrationException {
    if (module == null) {
      throw new NullPointerException();
    }
    for (Registration registration : registrations) {
      if (registration.getModule().equals(module)) {
        throw new NoRegistrationException();
      }
    }
    registrations.add(new Registration(module));

  }

}
