create table chat
(
    id bigint not null,
    primary key (id)
);

create table link
(
    id bigint generated always as identity,
    url text not null,
    last_check_time timestamp with time zone not null,
    primary key (id),
    unique (url)
);

create table subscriptions
(
    id bigint generated always as identity,
    chat_id bigint not null,
    link_id bigint not null,
    created_at timestamp with time zone not null,

    primary key (id),
    foreign key (chat_id) references chat(id) on delete cascade,
    foreign key (link_id) references link(id) on delete cascade,
    unique(chat_id, link_id)
);

