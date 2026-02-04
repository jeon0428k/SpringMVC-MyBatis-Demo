package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import com.springapp.mvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/batch")
public class BatchController {
    @Autowired
    @Qualifier("userService") //this is to specify implementation class
    private IUserService userService;

    @RequestMapping(value = "/rest/patch", method = RequestMethod.PUT)
    @ResponseBody
    public String patch() {
        return "patch";
    }

    @RequestMapping(value = "/rest/save", method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String save(@RequestBody List<String> jobs) {
        for (String job : jobs) {
            System.out.println(job);
        }
        return "save";
    }

    @RequestMapping(value = "/rest/query/{jobName}", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Map<?, ?> query(@PathVariable("jobName") String jobName) {
        System.out.println(jobName);
        List<User> allUsers = new ArrayList<>();
        allUsers.add(new User("test1", "abac"));
        allUsers.add(new User("test2", "qwesfsdf"));
        allUsers.add(new User("test3", "sdfwetrwtg"));
        Map<String, Object> result = new HashMap<>();
        result.put("result", allUsers);
        return result;
    }

    @RequestMapping(
            value = "/rest/upload/files",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public Map<String, Object> uploadFiles(
            @RequestParam(value = "jobName", required = false) String jobName,
            @RequestParam("files") MultipartFile[] files
    ) throws Exception {

        System.out.println(jobName);

        List<Map<String, Object>> fileInfos = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }

            String originalName = file.getOriginalFilename();
            long size = file.getSize();

            // 실제 저장 (예시)
            File dest = new File("D:/workspace/upload/" + originalName);
            file.transferTo(dest);

            Map<String, Object> info = new HashMap<>();
            info.put("fileName", originalName);
            info.put("size", size);

            fileInfos.add(info);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("count", fileInfos.size());
        result.put("files", fileInfos);

        return result;
    }

}
