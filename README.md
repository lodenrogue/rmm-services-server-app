In order to run build and run the application

    1. Create a database where we can create tables and rows (I am using MySQL)

    2. Run the database.sql file to create the necessary tables and rows. File is located in src/main/resources folder.

    3. Import the project into your IDE (I'm using intelliJ)

    4. Refresh Gradle projects

    5. Change spring.datasource.url to point to your database

    6. Change spring.datasource.username to your username

    7. Change spring.datasource.password to your password

    8. Run Application.class located in src/main/java/com/arkvis/Application.class

Now, let's work through the assigned tasks.
Swagger ui is supplied for convenience but all API calls can be made with any tool that can make http requests. (Postman, for example)

In order to make any request we must include an Authorization header. For simplicity, Authorization value is SECRET.
Authorization: SECRET

Task 1


    1. In order to create devices (Note: type should be either WINDOWS or MAC):
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP POST http://localhost:8080/rmm/devices

        Body
            {
              "customerId": "TEST",
              "systemName": "WINDOWS SERVER",
              "type": "WINDOWS"
            }

        Response will return the newly created device with a generated UUID

    2. In order to get a device we need it's generated UUID
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP GET http://localhost:8080/rmm/devices/{uuid}

    3. In order to update a device we need it's generated UUID
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP PUT http://localhost:8080/rmm/devices/{uuid}

        Body
            {
              "customerId": "TEST",
              "systemName": "WINDOWS MACHINE",
              "type": "WINDOWS"
            }

    4. In order to delete a device we need it's generated UUID
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP DELETE http://localhost:8080/rmm/devices/{uuid}

Task 2

    1. In order to get a list of all services
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP GET http://localhost:8080/rmm/services

    2. In order to add a service for a customer
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP POST http://localhost:8080/rmm/customers/{customerId}/services

        Body
            {
            	"serviceName": "CLOUDBERRY"
            }

    3. In order to delete a service for a customer
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP DELETE http://localhost:8080/rmm/customers/{customerId}/services/{serviceName}

    4. In order to get all services for a customer
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP GET http://localhost:8080/rmm/customers/{customerId}/services

    5. If an attempt is made to register a service that was already registered for the customer
        Response
            HTTP Status 409 Conflict

Task 3

    1. In order to calculate the cost of services for a customer
        Headers
            Authorization: SECRET
            Content-Type: application/json

        Url
            HTTP GET http://localhost:8080/rmm/customers/{customerId}/cost

Task 4 API is secured with encrypted Authorization header
