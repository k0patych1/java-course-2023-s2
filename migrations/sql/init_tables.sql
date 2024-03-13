create table chat
(
    chat_id bigint not null,
    primary key (chat_id)
);

create table link
(
    link_id  bigint generated always as identity,
    url text not null,
    primary key (link_id),
    unique (url)
);

create table subscriptions
(
    id bigint generated always as identity,
    chat_id bigint not null,
    link_id bigint not null,
    created_at timestamp with time zone not null,

    primary key (id),
    foreign key (chat_id) references chat(chat_id),
    foreign key (link_id) references link(link_id)
);

