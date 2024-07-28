CREATE TABLE if not exists public.DATASOURCECONFIG (
   id bigint PRIMARY KEY,
   tenant_id VARCHAR(10),
   driverclassname VARCHAR(255),
   url VARCHAR(255),
   schema VARCHAR(255),
   username VARCHAR(255),
   password VARCHAR(255),
   initialize BOOLEAN
);

INSERT INTO DATASOURCECONFIG VALUES (1, 'tenant-1', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-1', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (2, 'tenant-2', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-2', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (3, 'tenant-3', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-3', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (4, 'tenant-4', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-4', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (5, 'tenant-5', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-5', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (6, 'tenant-6', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-6', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (7, 'tenant-7', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-7', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (8, 'tenant-8', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-8', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (9, 'tenant-9', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5433/local_database', 'tenant-9', 'local_user', 'local_password', true);

INSERT INTO DATASOURCECONFIG VALUES (10, 'tenant-10', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5434/local_database', 'tenant-10', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (11, 'tenant-11', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5434/local_database', 'tenant-11', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (12, 'tenant-12', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5434/local_database', 'tenant-12', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (13, 'tenant-13', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5434/local_database', 'tenant-13', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (14, 'tenant-14', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5434/local_database', 'tenant-14', 'local_user', 'local_password', true);
INSERT INTO DATASOURCECONFIG VALUES (15, 'tenant-15', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5434/local_database', 'tenant-15', 'local_user', 'local_password', true);