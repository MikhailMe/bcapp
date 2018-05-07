-- bcapp
-- mikhail
-- mikhail

CREATE TABLE IF NOT EXISTS teams(
    id bigserial primary key,
    team_name varchar(30) not null
    -- etc
);

--region ========================= Core =========================

CREATE TABLE IF NOT EXISTS coefficients(
    id bigserial primary key,
    team1_win int not null,
    team2_win int not null,
    draw int not null
    -- etc
);

CREATE TABLE IF NOT EXISTS bets(
    id bigserial primary key,
    user_id bigint not null references user(id),
    team1 bigint not null references teams(id),
    team2 bigint not null references teams(id),
    goals_team1 bigint,
    goals_team2 bigint,
    coefficients_id bigint not null references coefficients(id),
    bet_date date not null
    -- etc
);

--endregion


--region ========================= Client =========================

CREATE TABLE IF NOT exists users(
    id bigserial primary key,
    nickname varchar(30) not null,
    has_token boolean,
    is_oracle boolean
);

CREATE TABLE IF NOT exists wallets(
    id bigserial primary key,
    user_id bigint not null references users(id),
    balance bigint
);


CREATE TABLE IF NOT exists accounts(
    id bigserial primary key,
    user_id bigint not null references users(id),
    wallet_id BIGINT not null references wallets(id)
   current_visit date not null
);

--endregion