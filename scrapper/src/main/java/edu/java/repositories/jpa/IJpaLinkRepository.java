package edu.java.repositories.jpa;

import edu.java.domain.jpa.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaLinkRepository extends JpaRepository<Link, Long> {
}
