-- liquibase formatted sql

-- changeset mzaretskiy:1
CREATE INDEX student_name_idx ON student(name);

--changeset mzaretskiy:2
CREATE INDEX faculty_name_and_color_idx ON faculty(name, color);