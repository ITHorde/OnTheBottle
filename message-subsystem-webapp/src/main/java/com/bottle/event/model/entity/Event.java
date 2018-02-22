package com.bottle.event.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(exclude = {"users", "place"})
@ToString(exclude = {"users", "place"})
public class Event {

    @Id @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", name = "event_id")
    private UUID id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Text")
    private String text;

    @Column(name = "Start_time", nullable = false)
    private Date startTime;

    @Column(name = "End_time", nullable = false)
    private Date endTime;

    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private Place place;
}
