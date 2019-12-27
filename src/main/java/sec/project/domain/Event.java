/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author hoffrenm
 */
@Entity
@Data
@NoArgsConstructor
public class Event extends AbstractPersistable<Long> {

    private String name;
    private String description;

    @OneToMany
    private List<Signup> attendees = new ArrayList<>();

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
        this.attendees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Signup> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Signup> attendees) {
        this.attendees = attendees;
    }

}
