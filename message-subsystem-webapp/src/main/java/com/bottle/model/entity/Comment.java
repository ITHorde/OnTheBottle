package com.bottle.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {
    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Column(name = "comment_id")
    private UUID id;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String Text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    @JsonFormat(pattern = "MM-yyyy-dd HH:mm:ss")
    private Date date = new Date();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "deleted")
    private Boolean isDeleted;
}