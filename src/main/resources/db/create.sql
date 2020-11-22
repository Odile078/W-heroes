SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS heroes(
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 age INTEGER,
 power VARCHAR,
 weakness VARCHAR,
 squadId INTEGER,
 completed BOOLEAN
);

CREATE TABLE IF NOT EXISTS squads(
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  cause VARCHAR,
  size INTEGER
);
