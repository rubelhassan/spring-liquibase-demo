package com.example.springliquibasedemo.modules.task;

import com.example.springliquibasedemo.modules.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public static enum TaskType{
        TODO, IN_PROGRESS, COMPLETED
    }
}
