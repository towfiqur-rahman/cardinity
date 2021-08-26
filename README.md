# Project Task Web App

Setting up a login based webapp for users and administrators to create and manage projects and tasks.

## Technology requirements
* Java 11
* Maven version 3.10 or above
* MySQL version 5.7 or above

## Database Setup
* Create MySQL database with name cardinity.
* Create user cardinity with password cardinity.
* Grant all privileges on database cardinity to user cardinity.
* To use custom database credentials, either update the file `src/main/resources/application.properties`.


## Running project
* To run the project from code base, run the command `mvn spring-boot run`
* To build the project as a war file, run the command `mvn clean install`
    * Add the war file to a Java server webapps.
* The database should be built on start up.
    * If the table `user_type` is empty, the database command `mysql -ucardinity -pcardinity cardity < src/main/resources/data.sql` should be executed.

## Tasks completed
- All tasks given in the specification.

## Rest API URLs using curl command
* Register and login using URLs
    * register: `curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/user/register --data '{ "username": "<username>", "password": "<password>", "enabled": <true/false>, "authorities": [{    "id": <1/2> ]}'`
    * `curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/login --data '{ "username": "<username>", "password": "<password>" }'`
* User profile
     * login: `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/user/profile`
     * `curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/user/update/<user id> --data '{ "enabled": <true/false>, "authorities": [{ "id": <1/2> }] }'`

* Projects
     * create: `curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/projects/create --data '{ "name": "<project name>"}'`
     * page list: `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/projects`
     * projects by user `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/projects/projects-by-user/<user id>`
     * delete: `curl -X DELETE -H 'Authorization: Bearer <access token>' -i http://localhost:8080/projects/delete/<project id>`

* Tasks
     * create: `curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/tasks/create --data '{ "description": "<task description>", "status": "<open/inProgress/closed>", "project": { "id": <project id> } "dueDate": "2021-08-31" }'`
     * view: `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer token' -i http://localhost:8080/tasks/<task id>`
     * page list: `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/tasks`
     * edit: `curl -X DELETE -H 'Authorization: Bearer token' -H 'Content-Type: application/json' -i http://localhost:8080/tasks/update/1 --data '{ "description": "Project 1", "status": "<open/inProgress/closed>", "dueDate": "2021-08-31" }'`
     * tasks by user `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i http://localhost:8080/tasks/tasks-by-user/<user id>`
     * search tasks: `curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Bearer <access token>' -i 'http://localhost:8080/tasks/search-tasks?status=<open/inProgress/closed>&projectId=<project id>&after=2021-07-21&before=2021-08-31'`

## Scope for improvement
1. Use of Spring validation modules.
2. Adding unit tests.
3. Custom error response classes to handle valid and invalid data.
4. User of a database migration plugin to manage database changes and insert table data using Liquibase or Flyway.
5. Use conventional URL pattern `/api/v1/` to the API URL prefix.
6. Add more details on using custom profiles.
