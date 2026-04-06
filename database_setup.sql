-- ============================================================
--  SIM Activation Portal - Database Setup Script
--  Run this in MySQL if you prefer manual DB setup
--  (The app auto-creates tables via Hibernate if ddl-auto=update)
-- ============================================================

CREATE DATABASE IF NOT EXISTS SimPortal;
USE SimPortal;

-- SimDetails
CREATE TABLE IF NOT EXISTS SimDetails (
    simId         BIGINT AUTO_INCREMENT PRIMARY KEY,
    serviceNumber VARCHAR(20)  NOT NULL,
    simNumber     VARCHAR(20)  NOT NULL,
    simStatus     VARCHAR(20)  NOT NULL
);

INSERT INTO SimDetails (serviceNumber, simNumber, simStatus) VALUES
('1234567891', '1234567891234', 'active'),
('1234567892', '1234567891235', 'inactive');

-- SimOffers
CREATE TABLE IF NOT EXISTS SimOffers (
    offerId   BIGINT AUTO_INCREMENT PRIMARY KEY,
    callQty   INT,
    cost      DOUBLE,
    dataQty   INT,
    duration  INT,
    offerName VARCHAR(100),
    simId     BIGINT,
    FOREIGN KEY (simId) REFERENCES SimDetails(simId)
);

INSERT INTO SimOffers (callQty, cost, dataQty, duration, offerName, simId) VALUES
(100, 100, 120, 10, 'Free calls and data', 1),
(150, 50,  100, 15, 'Free calls',          2);

-- CustomerAddress
CREATE TABLE IF NOT EXISTS CustomerAddress (
    addressId BIGINT AUTO_INCREMENT PRIMARY KEY,
    address   VARCHAR(100),
    city      VARCHAR(50),
    pincode   VARCHAR(10),
    state     VARCHAR(50)
);

INSERT INTO CustomerAddress (address, city, pincode, state) VALUES
('Jayanagar', 'Bangalore', '560041', 'Karnataka'),
('Vijaynagar', 'Mysore',   '567017', 'Karnataka');

-- Customer
CREATE TABLE IF NOT EXISTS Customer (
    uniqueIdNumber              VARCHAR(20) PRIMARY KEY,
    dateOfBirth                 DATE,
    emailAddress                VARCHAR(100),
    firstName                   VARCHAR(50),
    lastName                    VARCHAR(50),
    idType                      VARCHAR(30),
    state                       VARCHAR(50),
    customerAddress_addressId   BIGINT,
    simId                       BIGINT,
    FOREIGN KEY (customerAddress_addressId) REFERENCES CustomerAddress(addressId),
    FOREIGN KEY (simId) REFERENCES SimDetails(simId)
);

INSERT INTO Customer (uniqueIdNumber, dateOfBirth, emailAddress, firstName, lastName, idType, state, customerAddress_addressId, simId) VALUES
('1234567891234567', '1990-12-12', 'smith@abc.com', 'Smith', 'John', 'Aadhar', 'Karnataka', 1, 1),
('1234567891234568', '1998-12-12', 'bob@abc.com',   'Bob',   'Sam',  'Aadhar', 'Karnataka', 2, 2);

-- CustomerIdentity
CREATE TABLE IF NOT EXISTS CustomerIdentity (
    uniqueIdNumber VARCHAR(20) PRIMARY KEY,
    dateOfbirth    DATE,
    firstName      VARCHAR(50),
    lastName       VARCHAR(50),
    emailAddress   VARCHAR(100),
    state          VARCHAR(50)
);

INSERT INTO CustomerIdentity (uniqueIdNumber, dateOfbirth, firstName, lastName, emailAddress, state) VALUES
('1234567891234567', '1990-12-12', 'Smith', 'John', 'smith@abc.com', 'Karnataka'),
('1234567891234568', '1998-12-12', 'Bob',   'Sam',  'bob@abc.com',   'Karnataka');
