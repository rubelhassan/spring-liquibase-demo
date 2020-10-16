package com.example.springliquibasedemo.modules.task;

import com.example.springliquibasedemo.modules.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
@Relation(value = "task", collectionRelation = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_generator")
    @SequenceGenerator(name="tasks_id_generator", sequenceName = "tasks_id_seq", initialValue=101)
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
