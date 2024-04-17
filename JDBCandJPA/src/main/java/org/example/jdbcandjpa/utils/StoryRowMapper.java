package org.example.jdbcandjpa.utils;

import org.example.jdbcandjpa.entity.Story;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StoryRowMapper implements RowMapper<Story> {


    @Override
    public Story mapRow(ResultSet rs, int rowNum) throws SQLException {
        Story story = new Story();

        story.setId(rs.getLong("ID"));
        story.setTitle(rs.getString("TITLE"));
        story.setBody(rs.getString("BODY"));
        story.setCreatedAt(Timestamp.valueOf( rs.getString("CREATED_AT")));
        return story;
    }
}
