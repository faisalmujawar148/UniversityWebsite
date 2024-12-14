package uk.ac.rhul.cs2800.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GradeTest {

  private static final Module GRADE_MODULE = new Module("CS2800", "Software Engineering", true);
  private static final int GRADE_SCORE = 100;
  private Grade grade;
  private Student student1;
  private Student student2;

  @BeforeEach
  void beforeEach() {
    grade = new Grade();
    grade.setScore(GRADE_SCORE);
    grade.setModule(GRADE_MODULE);
    student1 = new Student();
    student1.setId(1L);
    student1.setFirstName("Alice");
    student1.setLastName("Smith");

    student2 = new Student();
    student2.setId(2L);
    student2.setFirstName("Bob");
    student2.setLastName("Johnson");

  }
  public boolean isValidScore(Integer score) {
    if (score == null || score < 0 || score > 100) {
        return false;
    }
    return true;
}

  // Helper method to create grades with specific null fields
  private Grade createGradeWithNulls(boolean nullStudent, boolean nullModule, boolean nullScore) {
    Grade grade = new Grade();
    grade.setStudent(nullStudent ? null : new Student());
    grade.setModule(nullModule ? null : GRADE_MODULE);
    grade.setScore(nullScore ? null : GRADE_SCORE);
    return grade;
  }

  @Test
  void testGradeInitialization() {
    // Test 1: Test initialisation and basic getters
    assertEquals(GRADE_SCORE, grade.getScore());
    assertEquals(GRADE_MODULE.getName(), grade.getModule().getName());
  }

  @Test
  void testGradeConstructor() {
    // Test 2: Test constructor with parameters
    Grade grade = new Grade(100, GRADE_MODULE);
    assertEquals(100, grade.getScore());
    assertEquals(GRADE_MODULE.getCode(), grade.getModule().getCode());
  }

  @Test
  void testModuleSetting() {
    // Test 3: Test setting module
    grade.setModule(new Module("CS2850", "Operating Systems", true));
    assertEquals("CS2850", grade.getModule().getCode());
  }

  @Test
  void testNullModule() {
    // Test 4: Test setting module to null
    grade.setModule(null);
    assertNull(grade.getModule());
  }

  @Test
  void testEqualsSelf() {
    // Test 5: Test comparing the object to itself
    assertTrue(grade.equals(grade));
  }

  @Test
  void testEqualsNull() {
    // Test 6: Test comparing with null
    assertFalse(grade.equals(null));
  }

  @Test
  void testEqualsDifferentClass() {
    // Test 7: Test comparing with an object of different class type
    assertFalse(grade.equals("String"));
  }

  @Test
  void testEqualsWithSameValues() {
    // Test 8: Test equality with identical values
    Grade anotherGrade = new Grade(100, GRADE_MODULE);
    assertTrue(grade.equals(anotherGrade));
  }

  @Test
  void testEqualsDifferentModule() {
    // Test 9: Test equality with different modules
    Grade anotherGrade = new Grade(100, new Module("CS2850", "Operating Systems", true));
    assertFalse(grade.equals(anotherGrade));
  }

  @Test
  void testEqualsDifferentScore() {
    // Test 10: Test equality with different scores
    Grade anotherGrade = new Grade(90, GRADE_MODULE);
    assertFalse(grade.equals(anotherGrade));
  }

  @Test
  void testEqualsBothFieldsNull() {
    // Test 11: Test equality when both fields are null
    Grade gradeNull = new Grade();
    assertTrue(gradeNull.equals(new Grade()));
  }

  @Test
  void testEqualsNullScore() {
    // Test 12: Test when score is null in one instance
    Grade gradeNullScore = new Grade(null, GRADE_MODULE);
    assertFalse(gradeNullScore.equals(grade));
  }

  @Test
  void testEqualsNullModule() {
    // Test 13: Test when module is null in one instance
    Grade gradeNullModule = new Grade(100, null);
    assertFalse(gradeNullModule.equals(grade));
  }

  @Test
  void testEqualsBothFieldsNullInOne() {
    // Test 14: Test when both fields are null in one instance
    Grade gradeNull = new Grade();
    assertFalse(gradeNull.equals(grade));
  }

  @Test
  void testHashCodeSameValues() {
    // Test 15: Test hashCode consistency for same values
    Grade anotherGrade = new Grade(100, GRADE_MODULE);
    assertEquals(grade.hashCode(), anotherGrade.hashCode());
  }

  @Test
  void testHashCodeDifferentValues() {
    // Test 16: Test hashCode consistency for different values
    Grade anotherGrade = new Grade(90, new Module("CS2850", "Databases", true));
    assertNotEquals(grade.hashCode(), anotherGrade.hashCode());
  }

  @Test
  void testHashCodeBothFieldsNull() {
    // Test 17: Test hashCode when both fields are null
    Grade gradeNull = new Grade();
    Grade anotherGradeNull = new Grade();
    assertEquals(gradeNull.hashCode(), anotherGradeNull.hashCode());
  }

  @Test
  void testHashCodeDifferentModules() {
    // Test 18: Test hashCode for grades with different modules
    Grade anotherGrade = new Grade(100, new Module("CS2850", "Databases", true));
    assertNotEquals(grade.hashCode(), anotherGrade.hashCode());
  }

  @Test
  void testHashCodeDifferentScores() {
    // Test 19: Test hashCode for grades with different scores
    Grade anotherGrade = new Grade(90, GRADE_MODULE);
    assertNotEquals(grade.hashCode(), anotherGrade.hashCode());
  }

  @Test
  void testSetIdAndGetId() {
    // Test 20: Test setting and getting the grade ID
    long expectedId = 12345;
    grade.setId(expectedId);
    assertEquals(expectedId, grade.getId());
  }

  @Test
  void testSetStudentAndGetStudent() {
    // Test 21: Test setting and getting the student object
    Student student = new Student(); // Assuming you have a Student class with a default constructor
    grade.setStudent(student);
    assertNotNull(grade.getStudent());
    assertEquals(student, grade.getStudent());
  }

  @Test
  void testEqualsNullFields() {
    // Test 22: Test multiple null field combinations

    // Null score and valid module
    Grade gradeNullScore = new Grade(null, GRADE_MODULE);
    assertFalse(gradeNullScore.equals(grade));

    // Null module and valid score
    Grade gradeNullModule = new Grade(100, null);
    assertFalse(gradeNullModule.equals(grade));

    // Both score and module are null
    Grade gradeNull = new Grade();
    assertFalse(gradeNull.equals(grade));

    // Both score and module are null in both instances
    Grade gradeBothNull1 = new Grade();
    Grade gradeBothNull2 = new Grade();
    assertTrue(gradeBothNull1.equals(gradeBothNull2));

    // Null score with the same module
    Grade gradeNullScoreSameModule = new Grade(null, GRADE_MODULE);
    Grade gradeNullScoreSameModule2 = new Grade(null, GRADE_MODULE);
    assertTrue(gradeNullScoreSameModule.equals(gradeNullScoreSameModule2));

    // Null module with the same score
    Grade gradeSameScoreNullModule = new Grade(100, null);
    Grade gradeSameScoreNullModule2 = new Grade(100, null);
    assertTrue(gradeSameScoreNullModule.equals(gradeSameScoreNullModule2));
  }

  @Test
  void testEqualsClassMismatch() {
    // Test 23: Test for class mismatch (should return false)
    assertFalse(grade.equals("String"));
  }

  @Test
  void testEqualsSelfComparison() {
    // Test 24: Test for comparing object to itself (should return true)
    assertTrue(grade.equals(grade));
  }

  @Test
  void testEqualsWithNullStudent() {
    // Test 25: Ensure student field null branch is tested
    Grade gradeWithNullStudent = new Grade();
    gradeWithNullStudent.setModule(GRADE_MODULE);
    gradeWithNullStudent.setScore(GRADE_SCORE);
    gradeWithNullStudent.setStudent(null); // Null student

    assertTrue(gradeWithNullStudent.equals(grade));
  }

  @Test
  void testEqualsWithNullModule() {
    // Test 26: Ensure module field null branch is tested
    Grade gradeWithNullModule = new Grade();
    gradeWithNullModule.setScore(GRADE_SCORE);
    gradeWithNullModule.setStudent(new Student()); // Non-null student
    gradeWithNullModule.setModule(null); // Null module

    // Comparing a Grade with a null module to one with a non-null module
    assertFalse(gradeWithNullModule.equals(grade));
  }

  @Test
  void testEqualsWithNullScore() {
    // Test 27: Ensure score field null branch is tested

    Grade gradeWithNullScore = new Grade(null, GRADE_MODULE);
    assertFalse(grade.equals(gradeWithNullScore), "Grades should not be equal with null score");
  }

  @Test
  void testEqualsWithNullStudentAndModule() {
    // Test 28: Test with both student and module null
    Grade gradeWithNullStudentModule = new Grade();
    gradeWithNullStudentModule.setScore(GRADE_SCORE);
    gradeWithNullStudentModule.setStudent(null); // Null student
    gradeWithNullStudentModule.setModule(null); // Null module

    // Comparing to a Grade with non-null student and module
    assertFalse(gradeWithNullStudentModule.equals(grade));
  }









  @Test
  void testEqualsWithNullAndNonNullStudent() {
    // Test 33: Ensure handling of null and non-null student
    Grade gradeWithNullStudent = new Grade();
    gradeWithNullStudent.setModule(GRADE_MODULE);
    gradeWithNullStudent.setScore(GRADE_SCORE);
    gradeWithNullStudent.setStudent(null); // Null student

    Grade gradeWithNonNullStudent = new Grade();
    gradeWithNonNullStudent.setModule(GRADE_MODULE);
    gradeWithNonNullStudent.setScore(GRADE_SCORE);
    gradeWithNonNullStudent.setStudent(new Student()); // Non-null student

    // Comparing a Grade with a null student to one with a non-null student
    assertFalse(gradeWithNullStudent.equals(gradeWithNonNullStudent)); // They should not be equal
  }

  @Test
  void testEqualsWithBothNullStudents() {
    // Test 34: Ensure handling of both null students
    Grade gradeWithNullStudent1 = new Grade();
    gradeWithNullStudent1.setModule(GRADE_MODULE);
    gradeWithNullStudent1.setScore(GRADE_SCORE);
    gradeWithNullStudent1.setStudent(null);

    Grade gradeWithNullStudent2 = new Grade();
    gradeWithNullStudent2.setModule(GRADE_MODULE);
    gradeWithNullStudent2.setScore(GRADE_SCORE);
    gradeWithNullStudent2.setStudent(null);

    // Comparing two Grades with null students should return true
    assertTrue(gradeWithNullStudent1.equals(gradeWithNullStudent2));
  }

  @Test
  void testEqualsWithNullStudentAndNonNullStudent() {
    // Test 35: Ensure handling of null student and non-null student
    Grade gradeWithNullStudent = new Grade();
    gradeWithNullStudent.setModule(GRADE_MODULE);
    gradeWithNullStudent.setScore(GRADE_SCORE);
    gradeWithNullStudent.setStudent(null);

    Grade gradeWithNonNullStudent = new Grade();
    gradeWithNonNullStudent.setModule(GRADE_MODULE);
    gradeWithNonNullStudent.setScore(GRADE_SCORE);
    gradeWithNonNullStudent.setStudent(new Student());

    // Comparing a Grade with a null student to one with a non-null student
    assertFalse(gradeWithNullStudent.equals(gradeWithNonNullStudent));
  }

  @Test
  void testEqualsBothStudentFieldsNull() {
    // Test 36: Ensure handling when both student fields are null
    Grade grade1 = new Grade();
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);
    grade1.setStudent(null); // student is null

    Grade grade2 = new Grade();
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);
    grade2.setStudent(null); // student is also null

    // Comparing two Grades with null students
    assertTrue(grade1.equals(grade2)); // They should be equal
  }

  @Test
  void testEqualsStudentNullInCurrentObject() {
    // Test 37: Ensure equality returns false when current object has null student
    // and the grade object has a non-null student.

    Grade grade1 = new Grade();
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);
    grade1.setStudent(null); // student is null

    Grade grade2 = new Grade();
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);
    grade2.setStudent(new Student()); // student is non-null

    // Comparing Grade where one student is null and the other is not
    assertFalse(grade1.equals(grade2)); // They should not be equal
  }


  @Test
  void testEqualsStudentNullInOtherGrade() {
    // Test 38: Ensure equality returns false when grade object has null student
    // and the current object has a non-null student.

    Grade grade1 = new Grade();
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);
    grade1.setStudent(new Student()); // student is non-null

    Grade grade2 = new Grade();
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);
    grade2.setStudent(null); // student is null

    // Comparing Grade where one student is null and the other is not
    assertFalse(grade1.equals(grade2)); // They should not be equal
  }

  @Test
  void testEqualsStudentNotEqual() {
    // Test 39: Ensure that two Grade objects with different students are not equal
    Student student1 = new Student(); // First student
    Student student2 = new Student(); // Second student (different instance)

    Grade grade1 = new Grade();
    grade1.setStudent(student1);
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);

    Grade grade2 = new Grade();
    grade2.setStudent(student2); // Different student
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);

    // They should not be equal because the students are not equal
    assertFalse(grade1.equals(grade2));
  }

  @Test
  void testEqualsWithDifferentStudents() {
    // Test 40: Ensure that if students are different, Grades are not equal
    // even if module and score are the same
    Student student1 = new Student();
    Student student2 = new Student();

    Grade grade1 = new Grade();
    grade1.setStudent(student1);
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);

    Grade grade2 = new Grade();
    grade2.setStudent(student2);
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);

    // Should return false because students are different
    assertFalse(grade1.equals(grade2));
  }

  @Test
  void testConstructorWithNullParameters() {
    // Test 41 -> the name is descriptive about what the test is
    Grade gradeNullScore = new Grade(null, GRADE_MODULE);
    assertNull(gradeNullScore.getScore());
    assertEquals(GRADE_MODULE, gradeNullScore.getModule());

    Grade gradeNullModule = new Grade(100, null);
    assertEquals(100, gradeNullModule.getScore());
    assertNull(gradeNullModule.getModule());
  }

  @Test
  void testHashCodeWithNullFields() {
    // Test 42 -> the name is descriptive about what the test is
    // Create grades with different null combinations
    Grade gradeFullyPopulated = new Grade();
    gradeFullyPopulated.setStudent(new Student());
    gradeFullyPopulated.setModule(GRADE_MODULE);
    gradeFullyPopulated.setScore(GRADE_SCORE);

    Grade gradeNullStudent = new Grade();
    gradeNullStudent.setModule(GRADE_MODULE);
    gradeNullStudent.setScore(GRADE_SCORE);
    gradeNullStudent.setStudent(null);

    Grade gradeNullModule = new Grade();
    gradeNullModule.setStudent(new Student());
    gradeNullModule.setScore(GRADE_SCORE);
    gradeNullModule.setModule(null);



    // Verify that hash codes are different
    Set<Integer> hashCodes = new HashSet<>(Arrays.asList(gradeFullyPopulated.hashCode(),
        gradeNullStudent.hashCode(), gradeNullModule.hashCode()));

    // All hash codes should be unique
    assertEquals(3, hashCodes.size(),
        "Hash codes should be unique for different null combinations");
  }

  @Test
  void testEqualsWithVariousNullFieldCombinations() throws IllegalArgumentException {
    // Test 43 -> the name is descriptive about what the test is
    Grade[] grades = new Grade[] {createGradeWithNulls(false, false, false), // all non-null
        createGradeWithNulls(true, false, false), // student null
        createGradeWithNulls(false, true, false), // module null
        createGradeWithNulls(true, true, false), // student and module null
    };

    // Ensure each grade only equals itself when field combinations are different
    for (int i = 0; i < grades.length; i++) {
      for (int j = 0; j < grades.length; j++) {
        if (i == j) {
          assertTrue(grades[i].equals(grades[j]), "Grade at index " + i + " should equal itself");
        } else {
          // Depending on your specific equality logic, you might want to adjust this
          assertFalse(grades[i].equals(grades[j]),
              "Grades at indices " + i + " and " + j + " should not be equal");
        }
      }
    }
  }


  @Test
  void testHashCodeConsistency() {
    // Test 44 -> the name is descriptive about what the test is
    Grade grade1 = new Grade(GRADE_SCORE, GRADE_MODULE);
    Grade grade2 = new Grade(GRADE_SCORE, GRADE_MODULE);

    // Ensure multiple calls to hashCode return same value
    assertEquals(grade1.hashCode(), grade1.hashCode());
    assertEquals(grade1.hashCode(), grade2.hashCode());
  }

  @Test
  void testEqualsWithUnequalStudents() {
    // Test 45 -> the name is descriptive about what the test is
    // Create two different students
    Student student1 = new Student();
    student1.setId(1L); // Assuming Student has an id setter
    student1.setFirstName("Alice");

    Student student2 = new Student();
    student2.setId(2L);
    student2.setFirstName("Bob");

    // Create two grades with different students but same module and score
    Grade grade1 = new Grade();
    grade1.setStudent(student1);
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);

    Grade grade2 = new Grade();
    grade2.setStudent(student2);
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);

    // Assert that these grades are not equal due to different students
    assertFalse(grade1.equals(grade2), "Grades with different students should not be equal");
  }

  @Test
  void testEqualsWithDifferentStudentsExplicit() {
    // Test 46 -> the name is descriptive about what the test is
    Student student1 = new Student(); // Assume Student class exists with proper equals() override
    student1.setId(1); // Assign unique IDs or distinguishable attributes
    Student student2 = new Student();
    student2.setId(2); // Different ID

    Grade grade1 = new Grade();
    grade1.setStudent(student1);
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);

    Grade grade2 = new Grade();
    grade2.setStudent(student2); // Different student
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);

    // They should not be equal because the students are not equal
    assertFalse(grade1.equals(grade2));
  }

  @Test
  void testEqualsStudentNullInOneGrade() {
    // Test 47 -> the name is descriptive about what the test is
    Grade grade1 = new Grade();
    grade1.setModule(GRADE_MODULE);
    grade1.setScore(GRADE_SCORE);
    grade1.setStudent(null); // Null student

    Grade grade2 = new Grade();
    grade2.setModule(GRADE_MODULE);
    grade2.setScore(GRADE_SCORE);
    grade2.setStudent(new Student()); // Non-null student

    assertFalse(grade1.equals(grade2)); // They should not be equal
  }

  @Test
  void testEqualsWhenStudentsAreNotEqual() {
    // Test 48
    // Create Grade objects with the same module and score but different students
    Grade grade1 = new Grade(90, GRADE_MODULE);
    grade1.setStudent(student1);

    Grade grade2 = new Grade(90, GRADE_MODULE);
    grade2.setStudent(student2);

    // Assert that the equals method returns false
    assertFalse(grade1.equals(grade2), "Grades with different students should not be equal.");
  }

  @Test
  void testEqualsWhenStudentsAreEqual() {
    // Test 49
    // Create Grade objects with the same student, module, and score
    Grade grade1 = new Grade(90, GRADE_MODULE);
    grade1.setStudent(student1);

    Grade grade2 = new Grade(90, GRADE_MODULE);
    grade2.setStudent(student1);

    // Assert that the equals method returns true
    assertTrue(grade1.equals(grade2),
        "Grades with the same student, module, and score should be equal.");
  }

  @Test
  void testSetScoreThrowsExceptionForNegativeScore() {
    // Test 50
    assertThrows(IllegalArgumentException.class, () -> grade.setScore(-1));
  }

  @Test
  void testSetScoreThrowsExceptionForScoreGreaterThan100() {
    // Test 51
    assertThrows(IllegalArgumentException.class, () -> grade.setScore(101));
  }

  @Test
  void testSetScoreSetsScoreForValidValue() {
    // Test 52
    grade.setScore(50);
    assertEquals(50, grade.getScore());
  }

  @Test
  void testSetScoreValidation() {
    // Test 53
    // Test for null score (assuming it is handled by the method)
    try {
      grade.setScore(null);
      assertNull(grade.getScore(), "Score should be null after setting to null");
    } catch (IllegalArgumentException e) {
      // If an exception is thrown, it should be handled gracefully
      assertTrue(true, "Exception thrown for null score");
    }

    // Test for score less than 0
    try {
      grade.setScore(-1);
      fail("Should throw exception for score less than 0");
    } catch (IllegalArgumentException e) {
      assertTrue(true, "Exception thrown for score less than 0");
    }

    // Test for score greater than 100
    try {
      grade.setScore(101);
      fail("Should throw exception for score greater than 100");
    } catch (IllegalArgumentException e) {
      assertTrue(true, "Exception thrown for score greater than 100");
    }

    // Test for valid score
    grade.setScore(50);
    assertEquals(50, grade.getScore(), "Score should be set correctly for valid value");
  }




}
