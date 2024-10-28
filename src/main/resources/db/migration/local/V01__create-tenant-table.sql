create sequence gen_tenant_config start with 1;

create table if not exists tb_tenant_config (
    id                      bigint       not null constraint client_pkey primary key,
    tx_tenant_id            varchar(255) not null,
    tx_datasource_host      varchar(255) not null,
    tx_datasource_name      varchar(255) not null,
    tx_datasource_scheme    varchar(255) not null,
    tx_datasource_username  varchar(255) not null,
    tx_datasource_password  varchar(255) not null,
    fl_ssl                  boolean not null
);

INSERT INTO public.tb_tenant_config (id, tx_tenant_id, tx_datasource_host, tx_datasource_name, tx_datasource_scheme, tx_datasource_username, tx_datasource_password, fl_ssl ) 
VALUES 
    -- (nextval('gen_tenant_config'), 'tenant-1', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_1', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-2', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_2', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-3', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_3', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-4', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_4', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-5', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_5', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-6', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_6', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-7', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_7', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-8', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_8', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-9', 'jdbc:postgresql://localhost:5433/', 'local_database', 'tenant_9', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-10', 'jdbc:postgresql://localhost:5434/', 'local_database', 'tenant_10', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-11', 'jdbc:postgresql://localhost:5434/', 'local_database', 'tenant_11', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-12', 'jdbc:postgresql://localhost:5434/', 'local_database', 'tenant_12', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-13', 'jdbc:postgresql://localhost:5434/', 'local_database', 'tenant_13', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-14', 'jdbc:postgresql://localhost:5434/', 'local_database', 'tenant_14', 'local_user', 'local_password', false),
    -- (nextval('gen_tenant_config'), 'tenant-15', 'jdbc:postgresql://localhost:5434/', 'local_database', 'tenant_15', 'local_user', 'local_password', false);
