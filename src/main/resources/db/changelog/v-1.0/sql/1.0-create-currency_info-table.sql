create table if not exists currency_info(
    id int primary key,
    currency_code varchar(10),
    currency_rate numeric
);