# SIM Activation Portal - Spring Boot REST API

A Spring Boot REST API that automates the SIM activation process for customers.

---

## Technology Stack

| Layer        | Technology                      |
|--------------|---------------------------------|
| Framework    | Spring Boot 3.2.0               |
| Language     | Java 17                         |
| Database     | MySQL 8.x                       |
| ORM          | Spring Data JPA / Hibernate     |
| Validation   | Spring Boot Validation (Jakarta)|
| Build Tool   | Maven                           |

---

## Project Structure

```
SimActivationPortal/
├── src/
│   ├── main/
│   │   ├── java/com/sim/portal/
│   │   │   ├── SimActivationPortalApplication.java   ← Main class
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java              ← Seeds sample data
│   │   │   ├── controller/
│   │   │   │   ├── SimController.java
│   │   │   │   └── CustomerController.java
│   │   │   ├── service/
│   │   │   │   ├── SimService.java
│   │   │   │   └── CustomerService.java
│   │   │   ├── serviceimpl/
│   │   │   │   ├── SimServiceImpl.java
│   │   │   │   └── CustomerServiceImpl.java
│   │   │   ├── repository/
│   │   │   │   ├── SimDetailsRepository.java
│   │   │   │   ├── SimOffersRepository.java
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   ├── CustomerAddressRepository.java
│   │   │   │   └── CustomerIdentityRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── SimDetails.java
│   │   │   │   ├── SimOffers.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── CustomerAddress.java
│   │   │   │   └── CustomerIdentity.java
│   │   │   ├── dto/
│   │   │   │   ├── SimValidationRequest.java
│   │   │   │   ├── CustomerBasicDetailsRequest.java
│   │   │   │   ├── CustomerPersonalDetailsRequest.java
│   │   │   │   ├── UpdateAddressRequest.java
│   │   │   │   ├── IdProofValidationRequest.java
│   │   │   │   └── ApiResponse.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── SimActivationException.java
│   │   │       └── ResourceNotFoundException.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/sim/portal/
│           ├── SimServiceImplTest.java
│           └── CustomerServiceImplTest.java
├── database_setup.sql
└── pom.xml
```

---

## Setup & Running

### Step 1 — Configure MySQL

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SimPortal?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### Step 2 — Database Setup (Two options)

**Option A** — Let Hibernate auto-create tables (recommended):
The `ddl-auto=update` setting in `application.properties` handles table creation.
The `DataInitializer.java` seeds all sample data automatically on first run.

**Option B** — Run the SQL script manually:
```bash
mysql -u root -p < database_setup.sql
```

### Step 3 — Build & Run

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

The app starts at: `http://localhost:8080`

---

## REST API Reference

### 1. SIM Validation — `POST /api/sim/validate`

Validates SIM number + Service number. Returns available offers on success.

**Request:**
```json
{
  "simNumber": "1234567891235",
  "serviceNumber": "1234567892"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "SIM validated successfully. Available offers:",
  "data": [ { "offerId": 2, "offerName": "Free calls", "cost": 50.0, ... } ]
}
```

**Error Responses:**
- `"SIM number should be 13-digit numeric value"` — bad SIM format
- `"Service number should be 10-digit numeric value"` — bad service number format
- `"Invalid details, please check again SIM number/Service number!"` — not found
- `"SIM already active"` — SIM status is already active

---

### 2. Customer Basic Details — `POST /api/customer/validate/basic`

Validates customer email and date of birth.

**Request:**
```json
{
  "email": "smith@abc.com",
  "dob": "1990-12-12"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "Customer basic details validated successfully."
}
```

**Error Responses:**
- `"Email value is required"` / `"Dob value is required"` — missing fields
- `"Invalid email"` — email doesn't match pattern
- `"Date of birth should be in yyyy-mm-dd format"` — bad date format
- `"No request placed for you."` — email/dob not found in DB

---

### 3. Customer Personal Details — `POST /api/customer/validate/personal`

Validates first name, last name, and confirm email (acts as OTP).

**Request:**
```json
{
  "firstName": "Smith",
  "lastName": "John",
  "confirmEmail": "smith@abc.com"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "Customer personal details validated successfully.",
  "data": { "firstName": "Smith", "lastName": "John", ... }
}
```

**Error Responses:**
- `"Firstname/Lastname should be maximum of 15 characters"` — name too long or has special chars
- `"No customer found for the provided details"` — name not in DB
- `"Invalid email details!!"` — confirm email doesn't match stored email

---

### 4. Update Address — `PUT /api/customer/address/update`

Updates existing address or creates a new one.

**Request:**
```json
{
  "addressId": 1,
  "address": "MG Road 45",
  "city": "Bangalore",
  "pincode": "560001",
  "state": "Karnataka"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "Address updated successfully.",
  "data": { "addressId": 1, "address": "MG Road 45", ... }
}
```

**Error Responses:**
- `"Address should be maximum of 25 characters"`
- `"Pin should be 6 digit number"`
- `"City/State should not contain any special characters except space"`

---

### 5. ID Proof Validation & SIM Activation — `POST /api/customer/validate/idproof`

Validates Aadhar details against `CustomerIdentity` table and activates the SIM.

**Request:**
```json
{
  "uniqueIdNumber": "1234567891234568",
  "firstName": "Bob",
  "lastName": "Sam",
  "dateOfBirth": "1998-12-12"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "SIM activated successfully!"
}
```

**Error Responses:**
- `"Id should be 16 digit"` — uniqueIdNumber not 16 digits
- `"Invalid details"` — name mismatch or ID not found
- `"Incorrect date of birth details"` — DOB mismatch
- `"SIM already active"` — SIM was already activated

---

## SoapUI Testing Guide

Import the following requests into SoapUI (Method: POST/PUT, Content-Type: application/json).

| # | Method | Endpoint                             | Purpose                     |
|---|--------|--------------------------------------|-----------------------------|
| 1 | POST   | /api/sim/validate                    | SIM validation              |
| 2 | POST   | /api/customer/validate/basic         | Email + DOB validation      |
| 3 | POST   | /api/customer/validate/personal      | Name + confirm email        |
| 4 | PUT    | /api/customer/address/update         | Update address              |
| 5 | POST   | /api/customer/validate/idproof       | ID proof + SIM activation   |

---

## Sample Test Data

| Customer | SIM Number    | Service Number | Email          | DOB        | Unique ID         | SIM Status |
|----------|---------------|----------------|----------------|------------|-------------------|------------|
| Smith John | 1234567891234 | 1234567891   | smith@abc.com  | 1990-12-12 | 1234567891234567  | active     |
| Bob Sam  | 1234567891235 | 1234567892     | bob@abc.com    | 1998-12-12 | 1234567891234568  | inactive   |

> Use **Bob Sam's** data for the full SIM activation flow (status = inactive).
