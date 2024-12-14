package uk.ac.rhul.cs2800.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uk.ac.rhul.cs2800.model.Module;

/**
 * A repository that manages Module instances.
 */


public interface ModuleRepository extends CrudRepository<Module, String> {

  @Query("SELECT m FROM Module m WHERE LOWER(m.code) = LOWER(:code)")
  Module findByCodeIgnoreCase(@Param("code") String code);
}
