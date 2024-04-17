package org.example.jdbcandjpa.ropository;

import org.example.jdbcandjpa.entity.Story;

import java.util.List;

public interface StoryRepository {

   Story findStoryById(Long storyId);

   List<Story> getAllStories();

   boolean createStory(Story story);
}