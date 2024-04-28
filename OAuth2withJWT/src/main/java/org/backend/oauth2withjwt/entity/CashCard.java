package org.backend.oauth2withjwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
public record CashCard( @Id Long id, Double amount) {
}
