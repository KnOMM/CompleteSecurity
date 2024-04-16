package org.backend.rest.migration.service;

import lombok.RequiredArgsConstructor;
import org.backend.rest.migration.model.Story;
import org.backend.rest.migration.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;

    public Story createStory(Story story) {
        Story newStory = storyRepository.save(story);
        storyRepository.flush();
        return newStory;
    }

    public Story getStoryById(Long id) {
        Optional<Story> optionalStory = storyRepository.findById(id);
        return optionalStory.orElse(null);
    }

    public List<Story> getAllStoriesByUser(Long userId) {
        List<Story> storyList = storyRepository.findAllByUser(userId);
        return storyList;
    }

    public List<Story> getAllStories() {
        List<Story> storyList = storyRepository.findAll();
        return storyList;
    }
}
