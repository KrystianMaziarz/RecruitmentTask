INSERT INTO university (id, name, address) VALUES (10,'UniversityNameTest', 'UniversityAddressTest'),
                                                  (11,'KUL', 'Lublin'),
                                                  (12,'ZZZ', 'Warszawa');

INSERT INTO student (id, first_name, last_name,gender,university_id) VALUES (10,'firstNameTest', 'lastNameTest','MALE',10),
                                                                            (11,'Dawid', 'Dawidowski','MALE',10),
                                                                            (12,'Karolina', 'Karolinowska','FEMALE',10);

INSERT INTO lecture (id,name,description,lecture_time,university_id) VALUES (10,'LectureNameTest', 'LectureDescriptionTest','2222-12-15 15:10:00',10),
                                                                            (11,'Analityka', 'Wykład z analityki','2223-10-11 15:17:00',10),
                                                                            (12,'Rachunki', 'Wykład z rachunków','2224-11-22 15:12:00',10);
