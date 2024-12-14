package uk.ac.rhul.cs2800.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2800.model.Grade;
import uk.ac.rhul.cs2800.model.Module;
import uk.ac.rhul.cs2800.model.Student;
import uk.ac.rhul.cs2800.repository.GradeRepository;
import uk.ac.rhul.cs2800.repository.ModuleRepository;
import uk.ac.rhul.cs2800.repository.StudentRepository;

/**
 * The controller of Grade.
 */
@RestController
public class GradeController {
  private final StudentRepository studentRepository;
  private final GradeRepository gradeRepository;
  private final ModuleRepository moduleRepository;

  /**
   * Constructor for GradeController with specified repositories for managing grade, module, and
   * student.
   *
   * @param studentRepository for managing and accessing student
   * @param gradeRepository for managing and accessing grade
   * @param moduleRepository for managing and accessing module
   */
  public GradeController(StudentRepository studentRepository, GradeRepository gradeRepository,
      ModuleRepository moduleRepository) {
    this.studentRepository = studentRepository;
    this.gradeRepository = gradeRepository;
    this.moduleRepository = moduleRepository;
  }

  /**
   * Adds a new grade.
   *
   * @param params A map containing "student_id", "module_code", and "score"
   * @return The saved grade or error response
   */
  @PostMapping(value = "/grades/addGrade")
  public ResponseEntity<Object> addGrade(@RequestBody Map<String, String> params) {
    // Check for required parameters
    if (!params.containsKey("student_id") || !params.containsKey("module_code")
        || !params.containsKey("score")) {
      return new ResponseEntity<>("Missing required parameters", HttpStatus.BAD_REQUEST);
    }

    // Find student, return Bad Request if not found
    final Student student = studentRepository.findById(Long.valueOf(params.get("student_id")))
        .orElse(null);
    if (student == null) {
      return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
    }

    // Find module, return Bad Request if not found
    final Module module = moduleRepository.findByCodeIgnoreCase(params.get("module_code"));
    if (module == null) {
      return new ResponseEntity<>("Module not found", HttpStatus.BAD_REQUEST);
    }

    // Validate score
    // Parse score
    int score;
    try {
      score = Integer.parseInt(params.get("score"));
    } catch (NumberFormatException e) {
      return new ResponseEntity<>("Score must be a valid integer", HttpStatus.BAD_REQUEST);
    }

    if (score < 0 || score > 100) {
      return new ResponseEntity<>("Score must be between 0 and 100", HttpStatus.BAD_REQUEST);
    }

    // Create and save grade
    Grade grade = new Grade();
    grade.setScore(score);
    grade.setStudent(student);
    grade.setModule(module);

    grade = gradeRepository.save(grade);
    return new ResponseEntity<>(grade, HttpStatus.OK);
  }
}
