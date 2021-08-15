# RESTful Web Services

Social Media Application

User (has one or more) -> Posts

- Retrieve all Users - GET /users
- Create a User      - POST /users
- Retrieve one User  - GET /users/{id} -> /users/1
- Delete a User      - DELETE /users/{id} -> /users/1

<br>

- Retrieve all posts for a User - GET /users/{id}/posts
- Create a posts for a User - POST /users/{id}/posts
- Retrieve details of a post - GET /users/{id}/posts/{post_id}

<br> 
DEBUGGING GUIDE (If you have problems)
JPA Hibernate Debugging Guide:
https://github.com/in28minutes/in28minutes-initiatives/blob/master/The-in28Minutes-TroubleshootingGuide-And-FAQ/jpa-and-hibernate.md