package org.backend.rest.migration.controller;

import org.backend.rest.migration.model.HelloRes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("/api/v1/rest/home")
public class HomeController {

    @ResponseBody
    @GetMapping
    public ResponseEntity<HelloRes> hello() {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        HelloRes helloRes = new HelloRes();
        helloRes.setMessage("Hello World");
        helloRes.setTime(timestamp.toString());

        return ResponseEntity.ok(helloRes);
    }
}
