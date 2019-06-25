package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @RequestMapping(value = "")

    //adding the capability to send data from the handler to the returned
    //html file (using the syntax "Model model" within the parameters
    //of the method
    public String index(Model model) {

        //hashmap declared to hold the search choices
        HashMap<String, String> actionChoices = new HashMap<>();

        //hashmap methods to add elements to a hashmap
        //
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        //model.addAttribute allows us to send information to the returned html file
        //"actions" - quotes-named element-quotes is the syntax to name the element to
        //send to "index"
        //actionChoices is the hashmap object sent to the named element associated
        //with "actions"
        model.addAttribute("actions", actionChoices);
        System.out.println("test");

        //the syntax for returning the html page to render
        return "index";
    }

}