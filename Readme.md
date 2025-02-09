# SWIFT Codes REST API

## 📌 Overview
This project provides a RESTful API to manage SWIFT codes, parsing data from an Excel file and storing it in a database. The API allows retrieving, adding, and deleting SWIFT codes while ensuring efficient querying.

## 🚀 Features
- Parses SWIFT codes from an Excel file.
- Stores data in a relational database (MySQL).
- Provides REST endpoints to retrieve SWIFT codes by code or country.
- Supports adding and deleting SWIFT codes.
- Containerized using Docker for easy deployment.

---

## 🛠️ Requirements
Make sure you have the following installed:
- **Java 17+**
- **Docker & Docker Compose**
- **Maven** (if running without Docker)

---

## 🏗️ Setup & Run

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/YOUR_GITHUB_USERNAME/swift-rest-api.git
cd swift-rest-api
```

### 2️⃣ Run with Docker (Recommended)
```sh
docker-compose up --build
```
This will:
- Build the application
- Start the MySQL database
- Run the Spring Boot application on **http://localhost:8080**

### 3️⃣ Run Manually (Without Docker)
#### Start MySQL Database
```sh
docker run --name swift-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=swift -p 3306:3306 -d mysql:8.0
```
#### Configure `.env` file (if required)
#### Run the Application
```sh
mvn spring-boot:run
```

---

## 🔥 API Endpoints

### 1️⃣ Get SWIFT Code Details
```http
GET /v1/swift-codes/{swiftCode}
```
**Response (Headquarters Example):**
```json
{
  "address": "6TH OF SEPTEMBER BLVD 152, PLOVDIV, 4000",
  "bankName": "ARCUS ASSET MANAGEMENT JSC",
  "countryISO2": "BG",
  "countryName": "BULGARIA",
  "isHeadquarter": true,
  "swiftCode": "AAAJBG21XXX",
  "branches": []
}
```

### 2️⃣ Get All SWIFT Codes for a Country
```http
GET /v1/swift-codes/country/{countryISO2}
```
**Response:**
```json
{
  "countryISO2": "PL",
  "countryName": "POLAND",
  "swiftCodes": [
    {
      "address": "LOPUSZANSKA BUSINESS PARK LOPUSZANSKA 38 D WARSZAWA, MAZOWIECKIE, 02-232",
      "bankName": "ALIOR BANK SPOLKA AKCYJNA",
      "countryISO2": "PL",
      "isHeadquarter": true,
      "swiftCode": "BANKPLPWXXX"
    }
  ]
}
```

### 3️⃣ Add a SWIFT Code
```http
POST /v1/swift-codes
```
**Request Body:**
```json
{
  "address": "UL. KRAKOWSKA 5, KRAKOW, 30-001",
  "bankName": "EXAMPLE BANK",
  "countryISO2": "PL",
  "countryName": "POLAND",
  "isHeadquarter": false,
  "swiftCode": "EXMPPLPW001"
}
```
**Response:**
```json
{
  "message": "Swift code added successfully"
}
```

### 4️⃣ Delete a SWIFT Code
```http
DELETE /v1/swift-codes/{swiftCode}
```
**Response:**
```json
{
  "message": "Swift code deleted successfully"
}
```

---

## 🧪 Testing
You can test the API using:
- **Postman**
- **cURL**

Example cURL request to get a SWIFT code:
```sh
curl -X GET http://localhost:8080/v1/swift-codes/BANKPLPWXXX
```

---



