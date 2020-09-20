# Book Extra Chapters | E.1. Cucumber Tests

This repository includes the source code for an extra chapter of the book [Learn Microservices with Spring Boot](https://www.kqzyfj.com/tr67wktqks7GDBDEB979BG9CADD?url=https%3A%2F%2Fwww.apress.com%2Fgp%2Fbook%2F9781484261309).

The full contents of this extra chapter are available online (work in progress): [End-To-End Tests with Cucumber](https://tpd.io/book-extra).

In this online tutorial, I show you how to set up a Cucumber project with `cucumber-jvm`, the Java module. Then, you'll learn a few core aspects:

* An overview of Cucumber and the Gherkin syntax.
* How to map steps to Java code with Cucumber Step definitions.
* Divide features and step definitions.
* Use different placeholders for step method's arguments.
* Connect from Cucumber to a backend server via REST API.
* `DataTable` examples
* etc.

## Running the code

**Requirements:**

* _Docker (including Docker Compose)_
* _JDK (14+)_

To run the cucumber tests against the backend server, you need to deploy the backend services first 🙂

The backend has two Spring Boot microservices with business logic, a gateway, a discovery server, the RabbitMQ broker, and a centralized logs service. You can run the complete system using Docker Compose. If you're interested in how to build this system from scratch, and analyze the pros/cons of microservices, check out [my book](https://www.kqzyfj.com/tr67wktqks7GDBDEB979BG9CADD?url=https%3A%2F%2Fwww.apress.com%2Fgp%2Fbook%2F9781484261309).

For our tests, we access the system using the same entry points as the frontend: the REST API exposed via Gateway on `localhost:8000/`.

Getting the backend up and running is simple. You can pull the publicly available images using Docker Compose. First, clone the code of the book's last chapter:

```bash
$ git clone https://github.com/Book-Microservices-v2/chapter08d.git
```

Then, navigate to the `docker` folder inside the repo, and run the Compose version that points to the public images:

```bash
$ cd chapter08d/docker
$ docker-compose -f docker-compose-public.yml up
```

After the complete system is up and running (it may take some time), you can run the cucumber tests in this repository with the Maven Wrapper.

```bash
# In Linux/Mac
$ ./mvnw clean test

# In windows
> mvnw.cmd clean test
```

You should see the test results in console. If you're curious about what's happening in the backend, just have a look at the docker logs. Events are propagated, and the Gamification microservice assigns new score and badges.

Remember that the [full contents of the guide are available online (in progress)](https://tpd.io/book-extra), **for free**.

Did you like the tutorial? Great! Please star this repo 😄 Thanks!
