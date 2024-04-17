package org.example.jdbcandjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Story {

    private Long id;


    private String title;


    private String body;


    private Timestamp createdAt;
}
