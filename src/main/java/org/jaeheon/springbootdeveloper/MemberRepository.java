package org.jaeheon.springbootdeveloper;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository provides JPA related methods such as flushing the persistence context and deleting records in a batch
// JpaRepository is a generic interface that takes two arguments: the entity type and the type of the primary key
// JpaRepository<Entity, PrimaryKey>
@Repository
// JpaRepository is a JPA specific extension of Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Query methods are methods that find information from the database and are declared on the repository interface
    Optional<Member> findByName(String name);
}
