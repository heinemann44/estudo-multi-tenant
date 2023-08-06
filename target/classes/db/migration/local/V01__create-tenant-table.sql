create sequence gen_tenant_config start with 3;

create table if not exists tb_tenant_config (
    id                      bigint       not null constraint client_pkey primary key,
    tx_tenant_id            varchar(255) not null,
    tx_datasource_host      varchar(255) not null,
    tx_datasource_name      varchar(255) not null,
    tx_datasource_username  varchar(255) not null,
    tx_datasource_password  varchar(255) not null,
    fl_ssl                  boolean not null
);

INSERT INTO public.tb_tenant_config (id, tx_tenant_id, tx_datasource_host, tx_datasource_name, tx_datasource_username, tx_datasource_password, fl_ssl ) 
VALUES (1, 'tenant1', 'jdbc:postgresql://localhost:5432/', 'tenant_1', 'user', 'password', false);

INSERT INTO public.tb_tenant_config (id, tx_tenant_id, tx_datasource_host, tx_datasource_name, tx_datasource_username, tx_datasource_password, fl_ssl ) 
VALUES (2, 'tenant2', 'jdbc:postgresql://localhost:5432/', 'tenant_2', 'user', 'password', false);
