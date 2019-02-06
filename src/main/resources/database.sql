--
-- Create Device Table
--
--
CREATE TABLE devices (
    id                  CHAR(38) NOT NULL PRIMARY KEY,
    customer_id         VARCHAR(100) NOT NULL,
    system_name         VARCHAR(500) NOT NULL,
    device_type         VARCHAR(50) NOT NULL,
    INDEX(customer_id)
);
--
-- Create service table
--
CREATE TABLE services (
    service_name        VARCHAR(100) NOT NULL PRIMARY KEY,
    description         VARCHAR(500) NOT NULL,
    windows_cost        DECIMAL(4,2) NOT NULL,
    mac_cost            DECIMAL(4,2) NOT NULL
);

INSERT INTO services(service_name, description, windows_cost, mac_cost)
    VALUES
    ('ANTIVIRUS', 'Antivirus service', 5.00, 7.00),
    ('CLOUDBERRY', 'Data backup service', 3.00, 3.00),
    ('PSA', 'Ticketing System', 2.00, 2.00),
    ('TEAMVIEWER', 'Remote Connection System', 1.00, 1.00);
--
-- Create customer service table
--
CREATE TABLE customer_services (
    id                  CHAR(38) NOT NULL PRIMARY KEY,
    customer_id         VARCHAR(100) NOT NULL,
    service_name        VARCHAR(100) NOT NULL
);
--
-- Create index for customer id to search
--
CREATE INDEX ix_customer_services_customer_id
    ON customer_services(customer_id);
--
-- Create composite unique key for customer id and service name
--
CREATE UNIQUE INDEX ix_customer_services_customer_id_service_name
    ON customer_services(customer_id, service_name);
