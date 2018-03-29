package com.bottle.service.event;

import com.bottle.model.DTO.EventDTO;
import com.bottle.model.entity.Event;
import com.bottle.model.entity.Place;
import com.bottle.model.entity.User;
import com.bottle.model.repository.EventStore;
import com.bottle.service.place.AllPlaceService;
import com.bottle.service.user.AllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class RegistrationEvent {
    private BuildEvent buildEvent;
    private EventStore eventStore;
    private AllPlaceService placeService;
    private AllUserService userService;
    private EntityBinder entityBinder;

    @Autowired
    public RegistrationEvent(
            BuildEvent buildEvent, EventStore eventStore, AllPlaceService placeService, AllUserService userService, EntityBinder entityBinder) {
        this.buildEvent = buildEvent;
        this.eventStore = eventStore;
        this.placeService = placeService;
        this.userService = userService;
        this.entityBinder = entityBinder;
    }

    public String createAndSave(EventDTO eventDTO) {
        try {
            Event event = buildEvent.build(eventDTO);
            Place place = placeService.createOrGet(eventDTO);
            User user = userService.createOrGet(eventDTO);

            event.setPlace(place);
            event.setOwner(user);
            entityBinder.addUserToEvent(user, event);

            eventStore.createOrUpdate(event);
            return "complete";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
