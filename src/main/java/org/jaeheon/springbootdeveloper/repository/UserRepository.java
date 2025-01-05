package org.jaeheon.springbootdeveloper.repository;

import java.util.Optional;
import org.jaeheon.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // findByName()
    // findByNameAndAge()
    // findByNameOrAge()
    // findByAgeLessThan()
    // findByAgeGreaterThan()
    // findByName(Is)Null();
}
