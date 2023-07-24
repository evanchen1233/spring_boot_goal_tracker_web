DROP TABLE IF EXISTS `goal`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `goal_type`;
DROP TABLE IF EXISTS `goal_status`;
DROP TABLE IF EXISTS `stream`;

CREATE TABLE IF NOT EXISTS Stream(
	id INT NOT NULL PRIMARY KEY,
    stream_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Role(
	id INT NOT NULL PRIMARY KEY,
    role_name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS User(
	username VARCHAR(50) NOT NULL PRIMARY KEY,
    last_name VARCHAR(50) NOT NULL,
    first_name  VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL default 1,
    stream_id INT,
	FOREIGN KEY (stream_id) REFERENCES Stream(id),
    FOREIGN KEY (role_id) REFERENCES Role(id)
);

CREATE TABLE IF NOT EXISTS Goal_Type(
	id INT NOT NULL PRIMARY KEY,
    goal_type_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Goal_Status(
	id INT NOT NULL PRIMARY KEY,
    goal_status_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Goal(
	id INT NOT NULL PRIMARY KEY,
    start_DATE DATE NOT NULL,
    target_end_DATE DATE NOT NULL,
    end_DATE DATE,
	description VARCHAR(255),
    goal_type_id INT NOT NULL,
    goal_status_id INT NOT NULL,
    username VARCHAR(50) NOT NULL,
    FOREIGN KEY (goal_type_id) REFERENCES Goal_Type(id),
    FOREIGN KEY (goal_status_id) REFERENCES Goal_Status(id),
    FOREIGN KEY (username) REFERENCES user(username)
);

INSERT INTO role(id,role_name)
VALUES(1,"USER");
INSERT INTO role(id,role_name)
VALUES(2,"ADMIN");

INSERT INTO stream(id,stream_name)
VALUES(1,"Java Development");
INSERT INTO stream(id,stream_name)
VALUES(2,"BABI");
INSERT INTO stream(id,stream_name)
VALUES(3,"TA");
INSERT INTO stream(id,stream_name)
VALUES(4,"AWS");

INSERT INTO goal_type(id, goal_type_name)
VALUES(1,"Learning Path");
INSERT INTO goal_type(id, goal_type_name)
VALUES(2,"Training");
INSERT INTO goal_type(id, goal_type_name)
VALUES(3,"Interview");
INSERT INTO goal_type(id, goal_type_name)
VALUES(4,"Self-Study");

INSERT INTO goal_status(id, goal_status_name)
VALUES(1,"Planning");
INSERT INTO goal_status(id, goal_status_name)
VALUES(2,"In Progress");
INSERT INTO goal_status(id, goal_status_name)
VALUES(3,"Done");
INSERT INTO goal_status(id, goal_status_name)
VALUES(4,"Discontinued");



#admin
INSERT INTO user(username,last_name,first_name,password,stream_id,role_id)
VALUES("admin@fdmgroup.com","Barton","April","$2a$12$tNs7ACtTHCFbRcLRcd1lMuNaPTbLbyNRRTTT0P.bwAak.trqyB6ue",NULL,2);


#user1
INSERT INTO user(username,last_name,first_name,password,stream_id,role_id)
VALUES("user@fdmgroup.com","Chen","Yifeng","$2a$12$tNs7ACtTHCFbRcLRcd1lMuNaPTbLbyNRRTTT0P.bwAak.trqyB6ue",2,DEFAULT);
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(600,"2023-05-15","2023-05-19","2023-05-19","UNIX training with Joshua", 2, 3, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(1000,"2023-05-15","2023-05-25","2023-05-26","Personal Data Pipeline project on GCP", 4, 3, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(500,"2023-04-12","2023-04-19","2023-04-19","PowerApps upskilling for Pod", 1, 3, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(400,"2023-04-12","2023-05-12","2023-05-10","Data Engineeing learning path", 1, 3, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(300,"2023-05-12","2023-05-12","2023-05-12","Mock interview with James", 3, 3, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(200,"2023-05-27","2023-08-06",NULL,"Donald Java training", 2, 2, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(100,"2023-05-27","2023-09-06",NULL,"AWS Solutions Architect Associate exam Prep", 4, 2, "user@fdmgroup.com");
INSERT INTO goal(id, start_DATE, target_end_DATE,end_DATE,description,goal_type_id,goal_status_id,username)
VALUES(110,"2023-07-21","2023-07-24",NULL,"Murex research for job opportunity", 4, 1, "user@fdmgroup.com");

