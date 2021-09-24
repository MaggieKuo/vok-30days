 create TABLE Grade(
     id bigint auto_increment PRIMARY KEY,
     mandarin DOUBLE NOT NULL,
     description VARCHAR(50) NOT NULL,
     english DOUBLE NOT NULL,
     math DOUBLE NOT NULL,
     pe DOUBLE NOT NULL,
     student_id bigint not null REFERENCES Student(id)
 )