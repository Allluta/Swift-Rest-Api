# Swift REST API

This is a simple REST API built in Java that allows you to manage and interact with SWIFT code data for banks. It provides endpoints to retrieve, add, and delete SWIFT code information for bank branches and headquarters.

## Requirements

- Docker
- Java 17
- Maven

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/swift-rest-api.git
   cd swift-rest-api

    Build and run the application with Docker Compose:

    docker-compose up --build

    This will start two containers: one for the Spring Boot application and one for the MySQL database.

    The application will be available at http://localhost:8080.

API Endpoints
1. Get SWIFT code details

GET /v1/swift-codes/{swift-code}

Example:

GET http://localhost:8080/v1/swift-codes/AAAJBG21XXX

Response:

{
  "address": "Bank Headquarters Address",
  "bankName": "Bank Name",
  "countryISO2": "US",
  "countryName": "United States",
  "isHeadquarter": true,
  "swiftCode": "AAAJBG21XXX",
  "branches": [
    {
      "address": "Branch Address",
      "bankName": "Bank Name",
      "countryISO2": "US",
      "isHeadquarter": false,
      "swiftCode": "AAAJBG21BRN"
    }
  ]
}

2. Get SWIFT codes by country

GET /v1/swift-codes/country/{countryISO2code}

Example:

GET http://localhost:8080/v1/swift-codes/country/US

Response:

{
  "countryISO2": "US",
  "countryName": "United States",
  "swiftCodes": [
    {
      "address": "Bank Headquarters Address",
      "bankName": "Bank Name",
      "countryISO2": "US",
      "isHeadquarter": true,
      "swiftCode": "AAAJBG21XXX"
    }
  ]
}

3. Add a new SWIFT code

POST /v1/swift-codes

Example request body:

{
  "address": "New Bank Address",
  "bankName": "New Bank Name",
  "countryISO2": "US",
  "countryName": "United States",
  "isHeadquarter": true,
  "swiftCode": "AAAJBG21XXX"
}

Response:

{
  "message": "SWIFT code added successfully"
}

4. Delete a SWIFT code

DELETE /v1/swift-codes/{swift-code}

Example:

DELETE http://localhost:8080/v1/swift-codes/AAAJBG21XXX

Response:

{
  "message": "SWIFT code deleted successfully"
}

Running Tests

To run tests, use Maven:

./mvnw test

Stopping the Application

To stop the application and remove the containers, run:

docker-compose down
