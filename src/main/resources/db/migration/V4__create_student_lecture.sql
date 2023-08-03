CREATE TABLE student_lecture
(
    student_id bigserial NOT NULL,
    lecture_id bigserial NOT NULL,
    PRIMARY KEY (student_id, lecture_id),
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE,
    FOREIGN KEY (lecture_id) REFERENCES lecture (id) ON DELETE CASCADE
);