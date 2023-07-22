Drop Table If Exists tweets;
Drop Table If Exists users;

Create Table users (
  id int unsigned AUTO_INCREMENT,
  given_name Varchar(100) Not Null,
  family_name Varchar(100) Not Null,
  user_name Varchar(100) Not Null,
  password Varchar(100) Not Null,
  email Varchar(100) Not Null,
  PRIMARY KEY(id)
);
Insert Into users (id, given_name, family_name, user_name, password, email) Values (1, '花子', '田中', 't_hanako', 'Abc12345', 'abc@gmail.com');
Insert Into users (id, given_name, family_name, user_name, password, email) Values (2, '太郎', '山田', 'y_tarou', 'Abc12345', 'efg@gimail.com');
Insert Into users (id, given_name, family_name, user_name, password, email) Values (3, '良樹', '新屋', 's_yoshiki', 'Abc12345', '123@gmail.com');

Create Table tweets (
  id int unsigned AUTO_INCREMENT,
  tweet Varchar(500) Not Null,
  created_at Datetime Not Null,
  user_id int unsigned Not Null,
  Primary Key(id),
  Constraint fk_user_id
  Foreign Key (user_id)
  References users (id)
  On Delete Restrict On Update Cascade
);
Insert Into tweets (id, tweet, created_at, user_id) Values (1, '田中花子初Tweet', NOW(), 3);
Insert Into tweets (id, tweet, created_at, user_id) Values (2, 'React.js楽しい', NOW(), 1);
Insert Into tweets (id, tweet, created_at, user_id) Values (3, 'Java楽しい', NOW(), 2);
