package uk.ac.rhul.cs2800.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;



/**
 * This class represents the modules that have been registered.
 */
@Entity
public class Registration {
  @Id
  @GeneratedValue
  Long id;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }



  @ManyToOne
  @JoinColumn(name = "student_id", referencedColumnName = "id")
  private Student student;

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  @ManyToOne
  @JoinColumn(name = "module_code", referencedColumnName = "code")
  private Module module;


  /**
   * Default constructor for Registration class.
   */
  public Registration() {

  }

  /**
   * Constructor for the Registration class.
   *
   * @param module The module that is being registered
   */
  public Registration(Module module) {
    this.module = module;
  }

  @Override
  public boolean equals(Object o) {
    // if object is compared to itself
    if (o == this) {
      return true;
    }
    // check if o isn't an instance of Registration class
    if (!(o instanceof Registration)) {
      return false;
    }
    // type-cast o to Registration so that we can compare
    Registration reg = (Registration) o;

    // handle cases where either module is null
    if (this.getModule() == null || reg.getModule() == null) {
      return this.getModule() == reg.getModule(); // returns true only if both are null
    }

    // confirmed both modules are non-null, safe to compare
    return this.getModule().equals(reg.getModule());
  }

  // Whenever you override equals() you have to update your hashCode too
  @Override
  public int hashCode() {
    return Objects.hash(module);
  }


  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }


}
