package org.backend.rest.migration.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.backend.rest.migration.model.Story;
import org.backend.rest.migration.model.User;
import org.backend.rest.migration.model.response.ErrorRes;
import org.backend.rest.migration.service.StoryService;
import org.backend.rest.migration.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("api/v1/rest/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    @ResponseBody
    @GetMapping("get-all")
    public ResponseEntity getAllStories() {
        try {
            List<Story> storyList = storyService.getAllStories();
            return ResponseEntity.ok(storyList);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @PostMapping("create-story")
    public ResponseEntity createStory(HttpServletRequest request, HttpServletResponse response, @RequestBody Story story) {
        try {
            String userEmail = request.getUserPrincipal().getName();
            User user = userService.getUserByEmail(userEmail);
            if (user == null) {
                ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid user");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            Long userId = user.getId();
            story.setUserId(userId);

            Date date = new Date();
            Timestamp currentTimeStamp = new Timestamp(date.getTime());
            story.setCreatedAT(currentTimeStamp);

            Story newStory = storyService.createStory(story);

            return ResponseEntity.ok(newStory);

        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @ResponseBody
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getStoryById(@PathVariable("id") Long id){

        try {
            Story story = storyService.getStoryById(id);
            if(story == null){
                ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "No story is found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            return ResponseEntity.ok(story);

        } catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @GetMapping("/get-by-user-id/{userId}")
    public ResponseEntity getAllStoriesByUserId(@PathVariable("userId") Long userId){

        try {
            List<Story> storyList = storyService.getAllStoriesByUser(userId);


            return ResponseEntity.ok(storyList);

        } catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
