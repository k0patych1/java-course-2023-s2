package edu.java.domain.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "chat")
public class TgChat {

    @Id
    private Long id;
}
