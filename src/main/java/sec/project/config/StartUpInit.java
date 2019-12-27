/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sec.project.domain.Event;
import sec.project.domain.Signup;
import sec.project.repository.EventRepository;
import sec.project.repository.SignupRepository;

/**
 *
 * @author hoffrenm
 */
@Component
public class StartUpInit {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SignupRepository signupRepository;

    @PostConstruct
    public void init() {
        
        // fill some sample information for testing
        Event sec1 = new Event("Security Conference", "Is your application secure?");
        Event sec2 = new Event("Injections 101", "Parameterized queries");
        Event sec3 = new Event("Leaks", "How can you detect a breach");

        sec1.getAttendees().add(new Signup("Claus Santa", "North Pole"));
        sec1.getAttendees().add(new Signup("Rudolf", "North Pole"));
        sec1.getAttendees().add(new Signup("Lost Little Elf", "South Pole"));

        sec2.getAttendees().add(new Signup("Donald", "Duckburg"));
        sec2.getAttendees().add(new Signup("Gus", "Grandma Duck's farm"));

        sec3.getAttendees().add(new Signup("Cat", "Meow"));
        sec3.getAttendees().add(new Signup("Dog", "Woof"));

        sec1.getAttendees().forEach(su -> signupRepository.save(su));
        sec2.getAttendees().forEach(su -> signupRepository.save(su));
        sec3.getAttendees().forEach(su -> signupRepository.save(su));

        eventRepository.save(sec1);
        eventRepository.save(sec2);
        eventRepository.save(sec3);
    }

}
