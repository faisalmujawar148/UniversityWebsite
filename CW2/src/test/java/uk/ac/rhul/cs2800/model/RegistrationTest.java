package uk.ac.rhul.cs2800.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RegistrationTest {

  private static final Module REGISTRATION_MODULE =
      new Module("CS2800", "Software Engineering", true);
  private Registration register;

  @BeforeEach
  void beforeEach() {
    register = new Registration();
    register.setModule(REGISTRATION_MODULE);
  }

  @Test
  void registrationTest() {
    // Test 1
    assertEquals(REGISTRATION_MODULE.getCode(),
        register.getModule().getCode());
  }

  @Test
  void registrationTestWithNewConstructor() {
    // Test 2
    Registration register = new Registration(REGISTRATION_MODULE);
    assertEquals(REGISTRATION_MODULE.getCode(),
        register.getModule().getCode());
  }

  @Test
  void equalsTest() {
    // Test 3
    // Technically since the assertEquals method uses the classes equals() method this is a bit
    // redundant,
    // however, it's still necessary for covering all the bases.
    // 1. normal
    assertEquals(true,
        register.equals(new Registration(REGISTRATION_MODULE)));
    // 2. empty
    Registration emptyreg = new Registration();
    assertEquals(true, emptyreg.equals(new Registration()));
    // 2.a registration w/ null
    Registration hreg = new Registration(null);
    assertEquals(true, hreg.equals(new Registration(null)));
    // 3. false
    assertEquals(false, register.equals(new Registration(new Module("CS2850", "Databases", true))));
    // 4. with itself
    assertEquals(true, register.equals(register));
    // 5. with a different class
    assertEquals(false, register.equals(new Module("CS2850", "Databases", true)));
    // 6. with pure null
    assertEquals(false, register.equals(null));
    // 7. one null, one not
    Registration regNull = new Registration(null);
    Registration regNonNull = new Registration(REGISTRATION_MODULE);
    assertEquals(false, regNull.equals(regNonNull));
    assertEquals(false, regNonNull.equals(regNull));
  }

  @Test
  void testHashCodeWithNullModule() {
    // Test 4
    Registration reg1 = new Registration(null);
    Registration reg2 = new Registration(null);

    // two with null modules have the same hash code
    assertEquals(reg1.hashCode(), reg2.hashCode());
  }

  @Test
  void testHashCodeWithSameModule() {
    // Test 5
    Module module = REGISTRATION_MODULE;
    Registration reg1 = new Registration(module);
    Registration reg2 = new Registration(module);

    // two with the same module have the same hash code
    assertEquals(reg1.hashCode(), reg2.hashCode());
  }

  @Test
  void testHashCodeWithDifferentModules() {
    // Test 6
    Registration reg1 = new Registration(REGISTRATION_MODULE);
    Registration reg2 = new Registration(new Module("CS2850", "Databases", true));

    // two with different modules have different hash codes
    assertNotEquals(reg1.hashCode(), reg2.hashCode());
  }

  @Test
  void testSetIdAndGetId() {
    // test 7
    // Test for setting and getting the registration ID
    Long expectedId = 12345L;
    register.setId(expectedId);

    // Verify that the ID is set and retrieved correctly
    assertEquals(expectedId, register.getId());
  }

  @Test
  void testSetStudentAndGetStudent() {
    // Test 8
    // Test for setting and getting the student object
    Student student = new Student(); // Assuming you have a Student class with a default constructor
    register.setStudent(student);

    // Verify that the student is correctly set and retrieved
    assertNotNull(register.getStudent());
    assertEquals(student, register.getStudent());
  }
}
