package org.example.jdbcandjpa.controller;

import lombok.RequiredArgsConstructor;
import org.example.jdbcandjpa.entity.Story;
import org.example.jdbcandjpa.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/rest/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;


    @ResponseBody
    @GetMapping("/get/{id}")
    public ResponseEntity<Story> findStoryById(@PathVariable("id")Long id) throws Exception{

        try {
            Story story = storyService.findById(id);

            if(story != null) {
                return ResponseEntity.ok(story);
            }

            throw new Exception("Data not found");

        } catch (Exception e){

            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @GetMapping("/get-all")
    public ResponseEntity<List<Story>> getAllStories() throws Exception{

        try {
            List<Story> storyList = storyService.getAllStories();

            if(storyList != null) {
                return ResponseEntity.ok(storyList);
            }

            throw new Exception("Data not found");

        } catch (Exception e){

            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @PostMapping(value = "/create")
    public ResponseEntity<String> createStory(@RequestBody Story story) throws Exception{

        try {
            boolean created = storyService.createStory(story);

            if(created) {
                return ResponseEntity.ok("Created new story");
            }

            throw new Exception("Failed creating story");

        } catch (Exception e){

            e.printStackTrace();
            throw e;
        }

    }
}
