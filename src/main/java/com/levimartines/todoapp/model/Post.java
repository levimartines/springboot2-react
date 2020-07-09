package com.levimartines.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "POST")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "POST_DESC")
    private String description;

    @Column(name = "USR_ID")
    private Long userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USR_ID", insertable = false, updatable = false)
    private User user;

    public Post(String description, Long userId) {
        this.description = description;
        this.userId = userId;
    }

}
