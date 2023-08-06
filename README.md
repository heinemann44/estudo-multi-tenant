# Quarkus Application with Dynamic Tenants Support

This is a Quarkus project in Java 20 that implements dynamic tenants functionality, allowing connections to different databases. Each tenant has a separate PostgreSQL database, and migrations are managed using Flyway and Agroal libraries. Additionally, migrations are executed in a separate thread upon server startup. Tenant registration is performed through REST requests, and to test the application, we have a car API that performs selects on the databases related to each tenant.

## Requirements

Make sure you have the following requirements installed on your machine before proceeding:

1. Java 20
2. Quarkus Framework 3.2
3. PostgreSQL 15
4. Flyway
5. Agroal (Connection pool management library)
6. Model Mapper
7. Lombok
8. Hibernate with Panache
9. Resteasy with Jackson

## Database Configuration

To use this project, you will need to have a PostgreSQL server running and configure the connection details in the `application.properties` file. Quarkus will automatically create the tenant's database when a new tenant is created.

## Agroal Configuration (Suggestion)

To optimize connection usage and improve system performance, we suggest implementing a solution to recognize tenants that point to the same database. Currently, Agroal does not identify this situation and creates separate connections for each tenant, even when they share the same database.

The suggestion is to develop a cache mechanism that stores connection information for already registered tenants. Before creating a new connection, the system should check if the tenant's data to be created points to the same database as any existing tenant in the cache. If positive, the new tenant should share the same connection with the existing database, avoiding the creation of unnecessary multiple connections.

This approach will help reduce the Agroal connection pool overhead and make the use of tenants with the same database more efficient. By implementing this suggestion, the system will be able to identify tenants related to the same database and handle their connections more intelligently, ensuring better utilization of available resources.

## Installation and Execution

Follow the steps below to install and run the application:

1. Clone the project repository:

```bash
git clone https://github.com/Willian199/sample-multitenacy
cd quarkus-dynamic-tenants
```

2. Configure the database information in the `application.properties` file.

3. Run the Quarkus application:

```bash
.\mvnw.cmd quarkus:dev 
```

4. The Quarkus server will start, and migrations will be executed in a separate thread.

# Tenant Registration API

## List All Tenants

```
GET http://localhost:8080/tenantController/listAll
```

## Delete Tenant by ID

```
DELETE http://localhost:8080/tenantController/delete/{id}
```

## Save New Tenant

```
POST http://localhost:8080/tenantController/save
```

The request body should contain the Tenant details in JSON format, for example:

```json
{
  "id": 1,
  "tenantId": "tenant1",
  "datasourceHost": "jdbc:postgresql://localhost:5432/",
  "datasourceName": "tenant_1",
  "datasourceUsername": "user",
  "datasourcePassword": "password",
  "flagSSL": true
}
```

# Car API

## List All Cars

```
GET http://localhost:8080/carController/listAll
```

## Delete Car by ID

```
DELETE http://localhost:8080/carController/delete/{id}
```

## Save New Car

```
POST http://localhost:8080/carController/save
```

The request body should contain the Car details in JSON format, for example:

```json
{
  "id": 5,
  "brand": "chevrolet",
  "model": "camaro",
  "year": 2022,
  "engineType": "gasoline engine",
  "transmissionType": "manual"
}
```

## Additional Functionalities (TODO)

1. **Tenant Metrics and Monitoring:** Implement a monitoring system to track tenant performance.

2. **Data Encryption:** Implement data encryption for sensitive information.

3. **Customizable Migrations:** Allow tenants to customize their migrations.

4. **Audit Trail:** Implement an audit trail for tracking tenant actions.

5. **Multi-Tenant Reports:** Provide custom reports for each tenant.

6. **Tenant Usage Statistics:** Offer insights into tenant resource usage.
   
7. **Virtual Threads:** Replace currents threads to virtual threads.


# Conclusion
This project demonstrates how to create a Quarkus application with dynamic tenants support, connecting to different databases for each tenant. Flyway library is used for managing migrations, executing migrations in a separate thread and using an efficient connection pool provides a more efficient and scalable development experience.

Welcome any feedback, suggestions, or contributions. If you have ideas for improving this project or implementing additional features, please feel free to open GitHub issues or submit pull requests.
