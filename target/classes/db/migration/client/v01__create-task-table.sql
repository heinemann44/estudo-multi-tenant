create sequence gen_car start with 1;

create table if not exists tb_car (
    id                      bigint       not null constraint car_pkey primary key,
    tx_brand                varchar(100) not null,
    tx_model                varchar(100) not null,
    nr_year                 integer not null,
    tx_engine_type          varchar(50),
    tx_transmission_type    varchar(50)
);


insert into tb_car (id, tx_brand, tx_model, nr_year, tx_engine_type, tx_transmission_type)
values
    (nextval('gen_car'), 'toyota', 'corolla', 2022, 'gasoline engine', 'automatic'),
    (nextval('gen_car'), 'honda', 'civic', 2023, 'gasoline engine', 'automatic'),
    (nextval('gen_car'), 'ford', 'focus', 2021, 'gasoline engine', 'manual'),
    (nextval('gen_car'), 'tesla', 'model 3', 2023, 'electric', 'automatic'),
    (nextval('gen_car'), 'chevrolet', 'camaro', 2022, 'gasoline engine', 'manual');

