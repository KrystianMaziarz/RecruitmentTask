CREATE TABLE student
(
    id            bigserial PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    gender        VARCHAR(10),
    university_id BIGINT,
    FOREIGN KEY (university_id) REFERENCES university (id) ON DELETE CASCADE
);