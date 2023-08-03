CREATE TABLE lecture
(
    id            bigserial PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   VARCHAR(100) NOT NULL,
    lecture_time  DATETIME,
    university_id BIGINT,
    FOREIGN KEY (university_id) REFERENCES university (id) ON DELETE CASCADE
);