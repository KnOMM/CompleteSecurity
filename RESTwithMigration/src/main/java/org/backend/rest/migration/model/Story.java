package org.backend.rest.migration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STORIES")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STORY")
    private String story;

    @Column(name = "CREATED_AT")
    private Timestamp createdAT;

    @Column(name = "USER_ID")
    private Long userId;
}
