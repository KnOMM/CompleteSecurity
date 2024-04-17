package org.example.jdbcandjpa.service;

import lombok.RequiredArgsConstructor;
import org.example.jdbcandjpa.entity.Story;
import org.example.jdbcandjpa.ropository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;


    public Story findById(Long storyId) {
        return storyRepository.findStoryById(storyId);
    }

    public List<Story> getAllStories() {
        return storyRepository.getAllStories();
    }

    public boolean createStory(Story story) {
        return storyRepository.createStory(story);
    }
}
