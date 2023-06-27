DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int unsigned AUTO_INCREMENT,
  given_name VARCHAR(100) NOT NULL,
  family_name VARCHAR(100) NOT NULL,
  user_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO users (id, given_name, family_name, user_name, email) VALUES (1,'花子','田中','t_hanako','abc@gmail.com');
INSERT INTO users (id, given_name, family_name, user_name, email) VALUES (2,'太郎','山田','y_tarou','efg@gimail.com');
INSERT INTO users (id, given_name, family_name, user_name, email) VALUES (3,'良樹','新屋','s_yoshiki','123@gmail.com');
