            ################################## **Mostafa Kataya - 2024** ##################################


**Library Management System API**
---------------------------------
![image](https://github.com/mustafakataya00/LibraryManagementSystem/assets/93375540/299775dc-5b29-4049-8528-5dc7821c99e2)

Setup Instructions
--
  Clone the repository.
  
  Run the application using Maven or your preferred IDE.
  
 ---- java level 18
 
 ---- SDK : open-jdk 22


  **Introduction**
  --
This project implements a RESTful API for managing a library system. It allows operations related to library members, books, authors, and their interactions. The API is built using Spring Boot framework in Java.

  **Features**
  --
    Library Member Management
    
      Create a Library Member
      

**Endpoint**: POST /MemberAPI
Request body: Library member details
Creates a new library member.
Get All Library Members

**Endpoint**: GET /MemberAPI
Retrieves all library members.
Get Library Member by ID

**Endpoint**: GET /MemberAPI/{id}
Retrieves a library member by ID.
Update Library Member

**Endpoint**: PUT /MemberAPI/{id}
Request body: Updated library member details
Updates an existing library member.
Delete Library Member

**Endpoint**: DELETE /MemberAPI/{id}
Deletes a library member by ID.
Check Out Book

**Endpoint**: POST /MemberAPI/{memberId}/checkout/{bookId}
Checks out a book for a library member.
Check In Book

**Endpoint**: POST /MemberAPI/{memberId}/checkin/{bookId}
Checks in a book from a library member.
Book Management
Create a Book

**Endpoint**: POST /BooksAPI
Request body: Book details
Creates a new book.
Get All Books

**Endpoint**: GET /BooksAPI
Retrieves all books.
Get Book by ID

**Endpoint**: GET /BooksAPI/{id}
Retrieves a book by ID.
Search Books by Title

**Endpoint**: GET /BooksAPI/title/{name}
Retrieves books by title.
Update Book (Replace)

**Endpoint**: PUT /BooksAPI/{id}
Request body: Updated book details
Updates an existing book.
Update Book (Partial Update)

**Endpoint**: PATCH /BooksAPI/{id}
Request body: Updated book details
Partially updates an existing book.
Delete Book

**Endpoint**: DELETE /BooksAPI/{id}
Deletes a book by ID.
Get Books by Author

**Endpoint**: GET /BooksAPI/GetBooksByAuthor
Request body: Author details
Retrieves books by a specific author.
Get Books by Author Name

**Endpoint**: GET /BooksAPI/GetBooksByAuthorName/{AuthorName}
Retrieves books by author's name.
Author Management
Create an Author

**Endpoint**: POST /AuthorAPI
Request body: Author details
Creates a new author.
Get All Authors

**Endpoint**: GET /AuthorAPI
Retrieves all authors.
Get Author by ID

**Endpoint**: GET /AuthorAPI/{id}
Retrieves an author by ID.
Get Author by Name

**Endpoint**: GET /AuthorAPI/findByName/{name}
Retrieves an author by name.
Update Author (Replace)

**Endpoint**: PUT /AuthorAPI/{id}
Request body: Updated author details
Updates an existing author.
Update Author (Partial Update)

**Endpoint**: PATCH /AuthorAPI/{id}
Request body: Updated author details
Partially updates an existing author.
Delete Author

**Endpoint**: DELETE /AuthorAPI/{id}

Deletes an author by ID.

---------------------------------

**Use of DTO**

For preventing infinite loops


**OverDue System Penalty**
--
Each day after the borrow date plus 7 days will be compensated with 5$ on the borrower , in my code i have made some tests so i have added on my current date like 2 days and made the required return date is now for testing purposes

---------------------------------
**Security**
--
**Authentication and Authorization**: Spring Security is integrated to handle authentication and authorization.

for testing purpose : 
username : user
password : pass

---------------------------------
**Error Handling**
--
Proper error responses with appropriate HTTP status codes (e.g., 404 for not found, 400 for bad request) are implemented for each endpoint.

Custom exceptions (NotFoundException, AlreadyExistsException, etc.) provide detailed error messages.

---------------------------------
**Technologies Used**
--
  **Spring Boot:** Framework for creating RESTful APIs in Java.
  
  **Hibernate:** ORM (Object-Relational Mapping) tool for interacting with the database.
  
  **H2 DATABASE:**

  


              ################################## **Mostafa Kataya - 2024** ##################################
