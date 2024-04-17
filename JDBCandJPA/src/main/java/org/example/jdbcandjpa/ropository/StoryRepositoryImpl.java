package org.example.jdbcandjpa.ropository;

import org.example.jdbcandjpa.entity.Story;
import org.example.jdbcandjpa.utils.StoryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoryRepositoryImpl implements StoryRepository{


    private final JdbcTemplate jdbcTemplate;
    private final StoryRowMapper storyRowMapper;

    public StoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.storyRowMapper = new StoryRowMapper();
    }

    @Override
    public Story findStoryById(Long storyId) {
        try {

            return jdbcTemplate.queryForObject("SELECT * FROM storiesdbjdbc.storiesdbjdbc.stories WHERE id = ?;", storyRowMapper, storyId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Story> getAllStories() {
        try {

            return jdbcTemplate.query("SELECT * FROM storiesdbjdbc.storiesdbjdbc.stories;", storyRowMapper);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean createStory(Story story) {
        try {
            jdbcTemplate.update("insert into storiesdbjdbc.storiesdbjdbc.stories(title, body) values (?,?)", story.getTitle(), story.getBody());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
