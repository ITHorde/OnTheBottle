package com.bottle.event.service.event;

import com.bottle.event.model.DTO.EventDTO;
import com.bottle.event.model.entity.Event;
import com.bottle.event.model.repository.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DeleteEvent {
    private GetterEvent getterEvent;
    private EventStore eventStore;

    @Autowired
    public DeleteEvent(GetterEvent getterEvent, EventStore eventStore) {
        this.getterEvent = getterEvent;
        this.eventStore = eventStore;
    }

    public String deleteEvent(EventDTO eventDTO) {
        Event event = getterEvent.getEvent(eventDTO.getId());
        event.setIsActive(false);

        try {
            eventStore.createOrUpdate(event);
            return "complete";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
