package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results", method = RequestMethod.GET)
    public String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        //System.out.println(searchType + " " + searchTerm);
        ArrayList<HashMap<String, String>> jobs=null;
        int jobCount = 0;
        //check if searchType is "all"
        //if searchType is all and there is no searthTerm, then return allJobs
        if("all".equals(searchType) && (searchTerm == null || searchTerm == "")){
            jobs = JobData.findAll();
            System.out.println("test1");
            jobCount = jobs.size();
        }else if("all".equals(searchType) && (searchTerm != null)) {
            jobs = JobData.findByValue(searchTerm);
            System.out.println("test2");
            jobCount = jobs.size();
        } else {
            System.out.println("test3");
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            jobCount = jobs.size();

        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("jobCount", jobCount);
        //System.out.println(searchTerm);

        model.addAttribute("columns", ListController.columnChoices);
        System.out.println("search method");
        return "search";
    }
}