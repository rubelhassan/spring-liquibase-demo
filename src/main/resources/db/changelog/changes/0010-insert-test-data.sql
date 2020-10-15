--liquibase formatted sql

--changeset rubel:0010_1 context:test splitStatements:true endDelimiter:;
INSERT INTO users (id, email, name, username) VALUES (1, 'rubel.hassan@dsinnovators,com', 'Rubel Hassan', 'rubel');
INSERT INTO tasks (id, description, title, type, user_id) VALUES (1, 'Create Repository for Liquibase Demo', 'Liquibase Code Repository', 'IN_PROGRESS', 1);
INSERT INTO tasks (id, description, title, type, user_id) VALUES (2, 'Write Liquibase Documentation', 'Liquibase Documentation', 'TODO', 1);

--rollback DELETE FROM tasks WHERE id = 1;
--rollback DELETE FROM users WHERE id = 1;


