CREATE TABLE IF NOT exists users (
  id        bigserial primary key,
  nickname  varchar(30) not null,
  has_token boolean,
  is_oracle boolean
);

CREATE TABLE IF NOT exists wallets (
  id      bigserial primary key,
  user_id bigint not null references users (id),
  balance bigint
);


CREATE TABLE IF NOT exists accounts (
  id            bigserial primary key,
  wallet_id     BIGINT    not null references wallets (id),
  current_visit timestamp not null
);

CREATE TABLE IF NOT EXISTS bets (
  id          bigserial primary key,
  game_id     bigint    not null references games (id),
  cash_to_bet bigint    not null,
  user_id     bigint    not null references users (id),
  bet_date    timestamp not null,
  coefficient bigint    not null
);

CREATE TABLE IF NOT EXISTS coefficients (
  id        bigserial primary key,
  team1_win real not null,
  team2_win real not null,
  draw      real not null
);


CREATE TABLE IF NOT EXISTS teams (
  id        bigserial primary key,
  team_name varchar(30) not null
  -- etc
);

CREATE TABLE IF NOT EXISTS games (
  id        bigserial primary key,
  team1     bigint    not null references teams (id),
  team2     bigint    not null references teams (id),
  game_date timestamp not null
);

--region ========================= Alter table section =========================

ALTER TABLE users
  ADD COLUMN IF NOT EXISTS mobile_number VARCHAR(15);

ALTER TABLE coefficients
  RENAME team1_win TO team1;

ALTER TABLE coefficients
  RENAME team2_win TO team2;

--endregion


/*drop table users cascade;
drop table wallets cascade;
drop table accounts cascade;
drop table teams cascade;
drop table games cascade;
drop table coefficients cascade;
drop table bets cascade;
*/