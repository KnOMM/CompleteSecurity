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

//    @Repository
//public class StoryRepositoryImpl implements StoryRepository{
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    ...
//
//    @Override
//    public Story findStoryById(Long storyId) {
//        try {
//
//            Story story = (Story) entityManager.createNativeQuery("SELECT *  FROM stories WHERE ID =:storyId",Story.class)
//                    .setParameter("storyId",storyId)
//                    .getSingleResult();
//
//            System.out.println("fetched data from db");
//            return story;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }


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

//    @Override
//    public List<Story> getAllStories() {
//        try {
//
//            List<Story> storyList = entityManager.createNativeQuery("SELECT *  FROM stories",Story.class).getResultList();
//
//            return storyList;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean createStory(Story story) {
//        try {
//
//            transactionTemplate.execute(transactionStatus ->{
//                entityManager.createNativeQuery("INSERT INTO stories(title, body) VALUES (:title, :body)")
//                        .setParameter("title",story.getTitle())
//                        .setParameter("body",story.getBody())
//                        .executeUpdate();
//                transactionStatus.flush();
//                return null;
//            });
//
//            return true;
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return false;
//    }
}
