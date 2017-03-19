create table categories (
    id BIGINT,
    name varchar(256)
);

create table news (
    id BIGINT,
    name varchar(256),
    publication_date timestamp,
    contents CLOB,
    category_id BIGINT
)