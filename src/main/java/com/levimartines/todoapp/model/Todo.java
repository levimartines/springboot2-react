package com.levimartines.todoapp.model;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TODO")
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TDO_ID")
    private Long id;

    @Column(name = "USR_ID")
    @NotNull
    private Long userId;

    @Column(name = "TDO_DESC")
    @NotNull
    @NotEmpty
    private String description;

    @Column(name = "TDO_DONE")
    private boolean done;

    @Column(name = "TDO_DATE")
    @NotNull
    private LocalDate dueDate;

}
