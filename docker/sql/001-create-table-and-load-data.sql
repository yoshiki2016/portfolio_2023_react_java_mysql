DROP TABLE IF EXISTS tweets;
CREATE TABLE tweets (
  id int unsigned AUTO_INCREMENT,
  tweet VARCHAR(500) NOT NULL,
  created_at DATETIME NOT NULL,
  user_id int unsigned NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_user_id
  FOREIGN KEY (user_id)
  REFERENCES users (id)
  ON DELETE RESTRICT ON UPDATE CASCADE
);
INSERT INTO tweets (id, tweet, created_at, user_id) VALUES (1, '田中花子初Tweet', NOW(), 1);
INSERT INTO tweets (id, tweet, created_at, user_id) VALUES (2, 'React.js楽しい', NOW(), 1);
INSERT INTO tweets (id, tweet, created_at, user_id) VALUES (3, 'Java楽しい', NOW(), 1);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int unsigned AUTO_INCREMENT,
  given_name VARCHAR(100) NOT NULL,
  family_name VARCHAR(100) NOT NULL,
  user_name VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY(id)
);
INSERT INTO users (id, given_name, family_name, user_name, password, email) VALUES (1, '花子', '田中', 't_hanako', 'Abc12345', 'abc@gmail.com');
INSERT INTO users (id, given_name, family_name, user_name, password, email) VALUES (2, '太郎', '山田', 'y_tarou', 'Abc12345', 'efg@gimail.com');
INSERT INTO users (id, given_name, family_name, user_name, password, email) VALUES (3, '良樹', '新屋', 's_yoshiki', 'Abc12345', '123@gmail.com');
