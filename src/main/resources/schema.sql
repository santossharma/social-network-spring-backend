create table if not exists social_network_user (
    id bigserial primary key ,
    first_name varchar(100),
    last_name varchar(100),
    login varchar(100),
    password varchar(100),
    token varchar(100),
    created_date timestamp
);
create sequence if not exists seq_social_network_user start 1 increment 1;

create table if not exists message(
    id bigserial,
    content text,
    user_id bigint references social_network_user(id),
    created_date timestamp
);

create sequence if not exists seq_message start 1 increment 1;


create table friends (
user_id bigint not null references social_network_user(id),
friend_id not null references social_network_user(id)
)

