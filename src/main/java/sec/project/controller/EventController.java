/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Event;
import sec.project.domain.Signup;
import sec.project.repository.EventRepository;

/**
 *
 * @author hoffrenm
 */
@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String list(Model model) {
        List<Event> events = eventRepository.findAll();

        model.addAttribute("events", events);

        return "list";
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
    public String show(Model model, @PathVariable Long id) {
        Event event = eventRepository.findOne(id);

        model.addAttribute("event", event);

        return "event";
    }

    @RequestMapping(value = "/events/{id}/details", method = RequestMethod.GET)
    public String showDetails(Model model, @PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ted = auth.getName();

        if (!ted.equals("ted")) {
            return "list";
        }

        Event event = eventRepository.findOne(id);
        List<Signup> attendees = event.getAttendees();

        model.addAttribute("event", event);
        model.addAttribute("attendees", attendees);

        return "details";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, @RequestParam(required = false) String text) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:./database", "sa", "");

        List<Event> results = new ArrayList<>();

        if (text != null && !text.isEmpty()) {
            String query = "SELECT * FROM event "
                    + "WHERE event.name = '" + text + "';";

            ResultSet rs = connection.createStatement().executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");

                results.add(new Event(name, description));
            }
        }

        model.addAttribute("results", results);

        return "search";
    }

}
