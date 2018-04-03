CREATE TABLE IF NOT EXISTS players(
  id            bigserial  primary key,
  first_name    varchar(20) not null,
  second_name   varchar(20) not null
);