package uk.ac.rhul.cs2800.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModuleTest {
  
  private static final String MODULE_NAME = "Software Engineering";
  private static final String MODULE_CODE = "CS2800";
  private static final boolean MODULE_MNC = true;
  private Module module;

  @BeforeEach
  void beforeEach() {
    module = new Module();
    module.setCode(MODULE_CODE);
    module.setName(MODULE_NAME);
    module.setMnc(MODULE_MNC);
  }
  
  @Test
  void moduleTest() {
    // Test 1
    assertEquals(MODULE_CODE, module.getCode());
    assertEquals(MODULE_NAME, module.getName());
    assertEquals(MODULE_MNC, module.getMnc());
  }

  @Test
  void moduleTestWithConstructor() {
    // Test 2
    Module module = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);
    assertEquals(MODULE_MNC, module.getMnc());
    assertEquals(MODULE_CODE, module.getCode());
    assertEquals(MODULE_NAME, module.getName());
  }

  @Test
  void equalsTest() {
    assertNotNull(module, "module was not initialized properly in @BeforeEach");
    // Test 3
    // Technically since the assertEquals method uses the classes equals() method this is a bit
    // redundant,
    // however, it's still necessary for covering all the bases.
    // 1. normal
    assertEquals(true, module.equals(new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC)));
    // 2. null
    Module emptymod = new Module();
    assertEquals(false, emptymod.equals(new Module(null, null, false)));
    // 3. false
    assertEquals(false, module.equals(new Module("CS2850", "Databases", MODULE_MNC)));
    // 4. with itself
    assertEquals(true, module.equals(module));
    // 5. with another class
    assertEquals(false,
        module.equals(new Grade(80, new Module("CS2850", "Databases", MODULE_MNC))));
    // 6. null fields
    Module modNullCode = new Module(null, MODULE_NAME, MODULE_MNC);
    assertEquals(false, module.equals(modNullCode)); // One has null code, the other doesn't

    Module modNullName = new Module(MODULE_CODE, null, MODULE_MNC);
    assertEquals(false, module.equals(modNullName)); // One has null name, the other doesn't

    Module modNullMnc = new Module(MODULE_CODE, MODULE_NAME, null);
    assertEquals(false, module.equals(modNullMnc)); // One has null mnc, the other doesn't
  }

  @Test
  void testEqualsWithNullCodeInOneInstance() {
    // Test 4
    Module modWithNullCode = new Module(null, MODULE_NAME, MODULE_MNC);
    Module modWithCode = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);

    assertFalse(modWithNullCode.equals(modWithCode));
    assertFalse(modWithCode.equals(modWithNullCode));
  }

  @Test
  void testEqualsWithNullNameInOneInstance() {
    // Test 5
    Module modWithNullName = new Module(MODULE_CODE, null, MODULE_MNC);
    Module modWithName = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);

    assertFalse(modWithNullName.equals(modWithName));
    assertFalse(modWithName.equals(modWithNullName));
  }

  @Test
  void testEqualsWithNullMncInOneInstance() {
    // Test 6
    Module modWithNullMnc = new Module(MODULE_CODE, MODULE_NAME, null);
    Module modWithMnc = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);

    assertFalse(modWithNullMnc.equals(modWithMnc));
    assertFalse(modWithMnc.equals(modWithNullMnc));
  }

  @Test
  void testEqualsWithAllFieldsNullInBothInstances() {
    // Test 7
    Module mod1 = new Module(null, null, null);
    Module mod2 = new Module(null, null, null);

    assertTrue(mod1.equals(mod2));
  }

  @Test
  void testEqualsWithDifferentMncValues() {
    // Test 8
    Module modWithTrueMnc = new Module(MODULE_CODE, null, MODULE_MNC);
    Module modWithFalseMnc = new Module(MODULE_CODE, null, false);

    assertFalse(modWithTrueMnc.equals(modWithFalseMnc));
    assertFalse(modWithFalseMnc.equals(modWithTrueMnc));
  }

  @Test
  void testEqualsWithAllFieldsNullInFirstAndNonNullInSecond() {
    // Test 9
    Module modAllFieldsNull = new Module(null, null, null);
    Module modWithFields = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);

    assertFalse(modAllFieldsNull.equals(modWithFields));
    assertFalse(modWithFields.equals(modAllFieldsNull));
  }

  @Test
  void testHashCodeWithNonNullFields() {
    // Test 10
    Module mod1 = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);
    Module mod2 = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);
    assertEquals(mod1.hashCode(), mod2.hashCode());
  }

  @Test
  void testHashCodeWithDifferentFields() {
    // Test 11
    Module mod1 = new Module(MODULE_CODE, MODULE_NAME, MODULE_MNC);
    Module mod2 = new Module(MODULE_CODE, "Data Science", false);
    assertNotEquals(mod1.hashCode(), mod2.hashCode());
  }

  @Test
  void testHashCodeWithNullFields() {
    // Test 12
    Module mod1 = new Module(null, null, null);
    Module mod2 = new Module(null, null, null);
    assertEquals(mod1.hashCode(), mod2.hashCode());
  }

  @Test
  void testHashCodeWithPartialNullFields() {
    // Test 13
    Module mod1 = new Module(MODULE_CODE, null, MODULE_MNC);
    Module mod2 = new Module(MODULE_CODE, null, MODULE_MNC);
    assertEquals(mod1.hashCode(), mod2.hashCode());
  }
}
