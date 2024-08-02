# Library Management System

## Overview

The Library Management System is a Spring Boot application designed to manage books and borrowing records in a library. It includes features for adding, updating, retrieving, and deleting books and borrowing records. The system uses RESTful APIs for interaction and supports Basic Authentication for secured endpoints.

## Table of Contents

1. [Requirements](#requirements)
2. [Running the Application](#running-the-application)
3. [API Endpoints](#api-endpoints)
   - [Books API](#books-api)
   - [Borrowing Records API](#borrowing-records-api)
   - [Patron API](#patron-api)
4. [Authentication](#authentication)
5. [Error Handling](#error-handling)
6. [Example Requests](#example-requests)

## Requirements

- **Java Development Kit (JDK)**: Version 22
- **Maven**: For building and managing project dependencies
- **IDE**: (Optional) IntelliJ IDEA, Eclipse, or any other Java IDE

## Running the Application

1. **Clone the Repository**

   ```bash
   git clone https://github.com/sh-osama-sami/library.git
   cd library
   ```

2. **Build the Project**
   Ensure you have Maven installed. Run the following command to build the project:
   ```
   mvn clean install
   ```

3. **Run the Application**
   You can run the application using the following command:
   ```
   mvn spring-boot:run
   ```
   Alternatively, you can run the application by executing the main method in the LibraryApplication class from your IDE.

4. **Application URL**
   The application will be available at: http://localhost:8080

## API Endpoints
### Books API
Get All Books

    URL: /api/books
    Method: GET
    Description: Retrieve a list of all books.

Get Book by ID

    URL: /api/books/{id}
    Method: GET
    Description: Retrieve a book by its ID.

Add a Book

    URL: /api/books
    Method: POST
    Description: Add a new book.
    Request Body:
   ```json

    {
      "title": "Book Title",
      "author": "Author Name",
      "year": 2023
    }
   ```

Update a Book

    URL: /api/books/{id}
    Method: PUT
    Description: Update an existing book.
    Request Body:
   ```json

    {
      "title": "Updated Book Title",
      "author": "Updated Author Name",
      "year": 2024
    }
```

Delete a Book

    URL: /api/books/{id}
    Method: DELETE
    Description: Delete a book by its ID.

Borrowing Records API
Get All Borrowing Records

    URL: /api/borrowing
    Method: GET
    Description: Retrieve a list of all borrowing records.

Get Borrowing Record by ID

    URL: /api/borrowing/{id}
    Method: GET
    Description: Retrieve a borrowing record by its ID.

Add a Borrowing Record

    URL: /api/borrowing
    Method: POST
    Description: Add a new borrowing record.
    Request Body:
   ```json

    {
      "bookId": 1,
      "patronId": 2,
      "borrowDate": "2024-01-15"
    }
```
Return a Book

    URL: /api/borrowing/return/{id}
    Method: PUT
    Description: Mark a book as returned.
    Request Body:
```json

    {
      "returnDate": "2024-02-15"
    }
```
Delete a Borrowing Record

    URL: /api/borrowing/{id}
    Method: DELETE
    Description: Delete a borrowing record by its ID.

Patron API
Get All Patrons 

- **URL**: `/api/patrons`
- **Method**: `GET`
- **Description**: Retrieve a list of all patrons.

**Example Request**

```bash
curl -X GET http://localhost:8080/api/patrons
```

**Example Response**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "membershipDate": "2022-01-15"
  }
]
```
Get Patron by ID
- **URL**: `/api/patrons/{id}`
- **Method**: `GET`
- **Description**: Retrieve a patron by their ID.

**Example Request**

```bash
curl -X GET http://localhost:8080/api/patrons/1
```
**Example Response**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "membershipDate": "2022-01-15"
  }
]
```
Add a Patron

- **URL**: `/api/patrons`
- **Method**: `POST`
- **Description**: Add a new patron.
- **Request Body**:
```json

{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "membershipDate": "2024-08-01"
}
```

**Example Request**

```bash

curl -X POST http://localhost:8080/api/patrons \
     -H "Content-Type: application/json" \
     -d '{"name": "Jane Smith", "email": "jane.smith@example.com", "membershipDate": "2024-08-01"}'
```
**Example Response**

```json

{
  "id": 2,
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "membershipDate": "2024-08-01"
}
```
Update a Patron

- **URL**: `/api/patrons/{id}`
- **Method**: `PUT`
- **Description**: Update an existing patron's information.
- **Request Body**:

```json

{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "membershipDate": "2024-08-01"
}
```
Example Request

```bash

curl -X PUT http://localhost:8080/api/patrons/2 \
     -H "Content-Type: application/json" \
     -d '{"name": "Jane Smith", "email": "jane.smith@example.com", "membershipDate": "2024-08-01"}'
```
Example Response

```json

{
  "id": 2,
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "membershipDate": "2024-08-01"
}
```
Delete a Patron

- **URL**: `/api/patrons/{id}`
- **Method**: `DELETE`
- **Description**: Delete a patron by their ID.

Example Request

```bash

curl -X DELETE http://localhost:8080/api/patrons/2
```
Example Response

    Status Code: 204 No Content

This API allows for full CRUD operations on patron records in the library management system, ensuring you can manage patron data efficiently.


## Authentication
### Basic Authentication

For endpoints that require authentication, include Basic Authentication credentials.
Example

To authenticate using Basic Authentication, include the following HTTP headers:

    Header Name: Authorization
    Header Value: Basic base64(username:password)

Where base64(username:password) is the Base64-encoded string of your username and password.
Example using curl

bash

curl -u username:password http://localhost:8080/api/books

## Error Handling
### HTTP Status Codes

    401 Unauthorized: Authentication is required or failed.
    403 Forbidden: You do not have permission to access the resource.
    404 Not Found: The requested resource could not be found.
    400 Bad Request: The request is invalid or malformed.
    500 Internal Server Error: An unexpected error occurred on the server.

### Error Responses

In case of errors, the API will return a JSON object with an error message:
```json

{
  "error": "Error message here"
}
```
### Example Requests
Get All Books

Request
```bash

curl -X GET http://localhost:8080/api/books
```
Response
```json

[
  {
    "id": 1,
    "title": "Book Title",
    "author": "Author Name",
    "year": 2023
  }
]
```

Add a Book

Request
```bash

curl -X POST http://localhost:8080/api/books \
     -H "Content-Type: application/json" \
     -d '{"title": "New Book", "author": "New Author", "year": 2024}'
```
Response

```json

{
  "id": 2,
  "title": "New Book",
  "author": "New Author",
  "year": 2024
}
```
Return a Book

Request

```bash

curl -X PUT http://localhost:8080/api/borrowing/return/1 \
     -H "Content-Type: application/json" \
     -d '{"returnDate": "2024-02-15"}'
```
Response

```json

{
  "id": 1,
  "bookId": 1,
  "patronId": 2,
  "borrowDate": "2024-01-15",
  "returnDate": "2024-02-15"
}
```
