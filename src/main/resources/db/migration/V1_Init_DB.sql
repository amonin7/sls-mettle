create table item (
    id uuid not null,
    cost float8,
    created_at timestamp,
    deleted_at timestamp,
    description varchar(200),
    name varchar(20),
    type int4,
    updated_at timestamp,
    primary key (id)
)
