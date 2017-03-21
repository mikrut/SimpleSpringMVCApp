create table categories (
    id BIGINT AUTO_INCREMENT,
    name varchar(256)
);

create table news (
    id BIGINT AUTO_INCREMENT,
    name varchar(256),
    publication_date timestamp,
    contents CLOB,
    category_id BIGINT
)