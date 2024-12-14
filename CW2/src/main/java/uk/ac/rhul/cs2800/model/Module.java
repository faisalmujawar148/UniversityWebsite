package uk.ac.rhul.cs2800.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;


/**
 * A class that represents the university module.
 */
@Entity
public class Module {
  @Id
  private String code;

  private String name;
  private Boolean mnc;

  /**
   * Default Constructor.
   */
  public Module() {
    this.code = new String();
    this.name = new String();
    this.mnc = new Boolean(false);
  }

  /**
   * Constructor for Module with specified details.
   *
   * @param code the code of the module
   * @param name the name of the module
   * @param mnc whether the module is mandatory or not
   */
  public Module(String code, String name, Boolean mnc) {
    this.code = code;
    this.name = name;
    this.mnc = mnc;
  }

  @Override
  public boolean equals(Object o) {
    // if object is compared to itself
    if (o == this) {
      return true;
    }
    // Check if o isn't an instance of module class
    if (!(o instanceof Module)) {
      return false;
    }
    // Type-cast o to Module so that we can compare
    Module mod = (Module) o;
    
    // null safe comparison for name, mnc, and code
    boolean codeEquals = (this.getCode() == null && mod.getCode() == null)
        || (this.getCode() != null && this.getCode().equals(mod.getCode()));
    
    boolean nameEquals = (this.getName() == null && mod.getName() == null)
        || (this.getName() != null && this.getName().equals(mod.getName()));

    boolean mncEquals = (this.getMnc() == null && mod.getMnc() == null)
        || (this.getMnc() != null && this.getMnc().equals(mod.getMnc()));

    // combine comparisons and return
    return codeEquals && nameEquals && mncEquals;
    
  }

  // Whenever you override equals() you have to update your hashCode too
  @Override
  public int hashCode() {
    return Objects.hash(code, name, mnc);
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;

  }

  public Boolean getMnc() {
    return mnc;
  }

  public void setMnc(boolean mncStatus) {
    this.mnc = mncStatus;
  }

}
