package edu.java.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "link")
public class Link {
    @Id
    private Long id;

    @Column
    private String url;

    @Column(name = "last_check_time")
    private OffsetDateTime lastCheckTime;
}
