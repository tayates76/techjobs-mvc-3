package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * the @Controller annotation designates the class as a controller class
 * the @RequestMapping annotation maps the controller method or class
 * to the URL
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    //a static variable because this is needed throughout the class to store
    //the choices to search on
    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

    @RequestMapping(value = "")
    public String list(Model model) {

        model.addAttribute("columns", columnChoices);

        return "list";
    }

    @RequestMapping(value = "values")
    //note the use of both "Model model" and "@RequestParam - Type - Variable Name"
    //(@RequestParam String column)
    public String listColumnValues(Model model, @RequestParam String column) {

        if (column.equals("all")) {

            //an ArrayList is declared that holds objects of type HashMap
            //the name of this HashMap is "jobs" which is returned from
            //the JobData findAll() method
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();

            //sends the string "All Jobs" to the named element "title"
            model.addAttribute("title", "All Jobs");

            //adds the ArrayList of HashMaps to the named element "jobs"
            model.addAttribute("jobs", jobs);

            //returns the html titled "list-jobs"
            //this html document lists all the jobs in the database
            return "list-jobs";
        } else {
            //if choice is a particular column, then use the JobData.findAll() method
            //which accepts an argument of a column
            //JobData.findall(column) returns an ArrayList of Strings representing
            //the column data
            ArrayList<String> items = JobData.findAll(column);

			/*
			adding the attributes to the html document "list-column"
			we add the title "All (whatever the column choice is) Values"
			we add the column chosen
			we add the output of item = JobData.findAll(column) which is
			an ArrayList of strings representing the titles under the chosen
			column
			*/
            model.addAttribute("title", "All " + columnChoices.get(column) + " Values");
            model.addAttribute("column", column);
            model.addAttribute("items", items);
            return "list-column";
        }

    }

    //this method is mapped to "list/jobs"
    @RequestMapping(value = "jobs")
    //this is the method that handles search by a column and a string within that column field
    //and returns an ArrayList of HashMaps that represent jobs having the search term
    //within the column
    //@RequestParam String column - the column to search
    //@RequestParam String value - the search string
    //the annotation "@RequestParam" allows Spring Boot to know what value we want to
    //pass from an html file into our method
    //Model model allows us to pass from our method into html that is in the return statement
    public String listJobsByColumnAndValue(Model model,
                                           @RequestParam String column, @RequestParam String value) {

        ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value);
        model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}