package uk.ac.rhul.cs2800.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.rhul.cs2800.model.Grade;
import uk.ac.rhul.cs2800.model.Module;
import uk.ac.rhul.cs2800.model.Student;
import uk.ac.rhul.cs2800.repository.GradeRepository;
import uk.ac.rhul.cs2800.repository.ModuleRepository;
import uk.ac.rhul.cs2800.repository.StudentRepository;

public class GradeControllerTest {

  private GradeController gradeController;
  private StudentRepository studentRepository;
  private ModuleRepository moduleRepository;
  private GradeRepository gradeRepository;

  private Student mockStudent;
  private Module mockModule;

  @BeforeEach
  void setUp() {
    // Mock repositories
    studentRepository = mock(StudentRepository.class);
    moduleRepository = mock(ModuleRepository.class);
    gradeRepository = mock(GradeRepository.class);

    // Create the controller with mocked repositories
    gradeController = new GradeController(studentRepository, gradeRepository, moduleRepository);

    // Setup mock student
    mockStudent = new Student();
    mockStudent.setId(1L);
    mockStudent.setFirstName("John");
    mockStudent.setLastName("Doe");

    // Setup mock module
    mockModule = new Module();
    mockModule.setCode("CS2800");
    mockModule.setName("Software Engineering");
  }

  @Test
  void addGrade_successful() {
    // Test 1
    // Test successful grade addition
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "CS2800");
    params.put("score", "85");

    when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findByCodeIgnoreCase("CS2800")).thenReturn(mockModule);

    Grade savedGrade = new Grade();
    savedGrade.setStudent(mockStudent);
    savedGrade.setModule(mockModule);
    savedGrade.setScore(85);
    when(gradeRepository.save(org.mockito.ArgumentMatchers.any(Grade.class)))
        .thenReturn(savedGrade);

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(85, ((Grade) response.getBody()).getScore());
  }

  @Test
  void addGrade_missingStudentId() {
    // Test 2
    // Test missing student_id
    Map<String, String> params = new HashMap<>();
    params.put("module_code", "CS2800");
    params.put("score", "85");

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Missing required parameters", response.getBody());
  }

  @Test
  void addGrade_missingModuleCode() {
    // Test 3
    // Test missing module_code
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("score", "85");

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Missing required parameters", response.getBody());
  }

  @Test
  void addGrade_studentNotFound() {
    // Test 4
    // Test student not found
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "999"); // Non-existent student ID
    params.put("module_code", "CS2800");
    params.put("score", "85");

    when(studentRepository.findById(999L)).thenReturn(Optional.empty());

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Student not found", response.getBody());
  }


  @Test
  void addGrade_moduleNotFound() {
    // Test 5
    // Test module not found
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "INVALID");
    params.put("score", "85");

    when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findByCodeIgnoreCase("INVALID")).thenReturn(null);

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Module not found", response.getBody());
  }

  @Test
  void addGrade_missingScore() {
    // Test 6
    // Test missing score
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "CS2800");

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Missing required parameters", response.getBody());
  }

  @Test
  void addGrade_invalidScoreFormat() {
    // Test 7
    // Test invalid score format
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "CS2800");
    params.put("score", "invalid_score");

    when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findByCodeIgnoreCase("CS2800")).thenReturn(mockModule);

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Score must be a valid integer", response.getBody());
  }

  @Test
  void addGrade_invalidScore() {
    // Test 8
    // Test score out of bounds (high)
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "CS2800");
    params.put("score", "101");

    when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findByCodeIgnoreCase("CS2800")).thenReturn(mockModule);

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Score must be between 0 and 100", response.getBody());
  }

  @Test
  void addGrade_negativeScore() {
    // Test 9
    // Test score out of bounds (low)
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "CS2800");
    params.put("score", "-1");

    when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findByCodeIgnoreCase("CS2800")).thenReturn(mockModule);

    ResponseEntity<?> response = gradeController.addGrade(params);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Score must be between 0 and 100", response.getBody());
  }




}
