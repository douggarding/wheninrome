# When in Rome

When in Rome is a simple RESTful web service that was built as a simple exercise to practice and demonstrate my understanding of basic web service technology. There is a single GET endpoint that takes an integer and returns its value as a Roman numeral. As a simple exercise, the functionality and breadth of this project is limited in scope and is intended to be run locally. 

## Building and Running the project

#### Using Maven

There are a few simple commands that can be used to build and and run this project, but they require Maven being installed on your machine. [Maven installation instructions here](https://maven.apache.org/install.html).

To run the project open a terminal and navigate to the project's root directory. You can then run the server by entering `mvn spring-boot:run`. (Alternatively use `./mvnw spring-boot:run` if you're using the Maven Wrapper.)

If there are complications, you can try flushing the existing compiled classes and other resources by executing `mvn clean`. Then run `mvn install` which will compile, test, and package the project back into a fresh JAR file. 

#### Using Java

Alternatively, there should be a compiled JAR file ready to use with a Java command. From the project's root directory, run: `java -jar target/when-in-rome-service-0.0.1-SNAPSHOT.jar`.

#### Calling the Endpoint

There is a single endpoint to this server. It can be called by making a `GET` request using the following URI format: `http://localhost:8080/romannumeral?query={integer}`. The `{integer}` value must be a value in the range of 1-3999. A failed response will give a little detail as to the problem with the request, otherwise you'll receive a JSON response in the form of:

```
{
  “input” : “123”,
  “output” : “CXXIII”
}
```

## Engineering and Testing Methodology

I'm not sure that there is much of a structured, existing methodology that was used during the development of this assignment in a large part of this because this was a relatively small, isolated project that I worked on independently. However, I can distill a high-level overview of the different "steps" involved with my process.

#### 1. Establishing a server, Add an API Endpoint, Implement Conversion Logic

The first step of my process was to make sure I understood what the requirements of the project were and what the expected outcome was. On the most basic level, I knew I needed to create a server that handles an HTTP request for taking a number, converting it to a Roman numeral, and then returning that value. So, I chose a framework (Spring Boot) that I could use to stand up a simple server. I then configured the controller that would handle the `GET /romannumeral` endpoint. Afterwards I built the logic for converting the value from an integer to a Roman numeral. I used [Wikipedia's entry on Roman numerals](https://en.wikipedia.org/wiki/Roman_numerals) to inform my understanding of how they work, and the logic I wrote to make this conversion went through a few different approaches and iterations. 

#### 2. Add Testing and Error Handling

There was not a hard cutover from the previous step to this one, but rather was a very blurred line where testing and error handling was added while the parts from step 1 were being built. But generally, this step involved adding unit testing, integration testing, and expanding error handling. This involved creating unit tests to test the logic of the Roman Numeral conversion, as well as the logic of the Controller that handles the incoming requests. Because unit testing doesn't include some of the processes handled by the server's framework, integration tests helped to verify that the endpoints could successfully receive a request, process it, and return the appropriate response. Because writing quality tests involves considering edge cases, this was a good time to consider how those scenarios would be handled and to then enhance the existing error handling to make it more robust.

#### 3. Add DevOps Capabilities

The last phase was to consider how the service would be handled and managed once deployed to production or some other real-world environment. This mostly involved adding some existing frameworks that would add logging to the service as well as expose metrics so that they could be consumed by an external analytics platform. Adding in these features also involved further refining existing code and updating the testing suites so that they could pass with any additional code that was added as a part of this phase.

#### 4. Wrap Up

Finally, the last thing was to run through _everything_ to verify that it's all still working and functional. This is also involves reading through the code to make any last adjustments, updates, or other forms of refactoring. It also involved double checking existing documentation and adding to it.

#### Other Thoughts and Considerations on This (My) Process

I feel that coding, engineering, and software development is often viewed as a highly structured, organized, technical and sometimes "linear" endeavor. However, I often tend to approach the writing of code and the creation of software as a "creative" endeavor. So naturally, my process emulates what one might go through when writing literature, composing music, or creating other forms of artwork. This of course involves as much pre-planning and thinking as possible, but when it comes to composing, structuring, and otherwise writing code, the process is a little more free-form. A musician might write the lyrics before the music for one song, and then write the music and then the lyrics for another song. 

The nature of this project also forced me to deviate slightly from what my process would normally be. I was essentially given some specifications and then instructed to go complete them in isolation. Normally, I would be asking colleagues and others for advice and suggestions along the way and changing course as correction and/or suggestions were given. I didn't have that resource available to me for this exercise. 

I claim to do great work as a programmer. This does not necessarily guarantee that what I produce is always great in and of itself. But what it does guarantee is that what I produce is the greatest that _I'm_ capable of producing based on my current level of experience and knowledge. The feedback loop from the knowledge and experience that's afforded to me by team members is essentially for me to be able to produce truly great work, beyond what I'm currently capable of when alone. It's also an essential part of me being able to further grow and enhance my own knowledge and experience. 

## Packaging Layout

There was not a lot of intentional changes made to the packaging layout for this project, as much of it is decided by and handled by the Spring framework. The project is packaged into a standard JAR file because that's really all that was needed. 

The project layout and structure is relatively simple because there are not many different files or components. Currently, all of the files containing logic for the `GET /romannumeral` endpoint are in the same Java package. If this project weas expanded to include many different API endpoints, additional features and functionality, etc. I would have structured this differently by expanding the number of packages and sorting the different files appropriately based on their use and functionality.

## Dependency Attribution

The following frameworks and tools were used as a part of this project:

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE for development
* [Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) used as the general purpose programming language
* [Spring Boot](https://spring.io/projects/spring-boot) module for the HTTP server and integration testing
* [Maven](https://maven.apache.org/) for dependency management
* [JUnit 5](https://junit.org/junit5/) for unit testing
* [SLF4J](http://www.slf4j.org/) for logging
* [Micrometer](https://micrometer.io/) for aggregating application monitoring data
* [Prometheus](https://prometheus.io/) for testing application monitoring

#### Explanation of choices:

* **IntelliJ IDEA**: This is the IDE I use regularly and am most familiar with. 
* **Spring Boot**: Where I currently work we use Dropwizard and the Jersey framework for handling RESTful web services in Java, so that seemed like an obvious choice for this project. However, Spring is something I hear about _a lot_ and seems extremely popular for this type of work. Because of its ubiquitousness, I decided that this would be a good opportunity to become more familiar with it. It also had tools and support to get it up and running quickly. 
* **Java 8**: This version was used primarily because it was the default for a Spring Boot project. Java 8 is widely adopted, and due to the small scale nature of this project, it seemed fine to just use Java 8. 
* **Maven**: Maven is something I'm a little familiar with already and Spring is built to easily take advantage of it. Using it here seemed reasonable and efficient.
* **JUnit**: JUnit is pretty ubiquitous for unit testing in Java and is already pre-packaged into everything and ready to use.
* **SLF4j**: Is something I'm already a little familiar with, and Spring already provided some default configurations for it.
* **Micrometer**: Monitoring frameworks are something I'm pretty unfamiliar with. Spring already autoconfigures and integrates with with Micrometer. Also, Micrometer has lots of support for integrating a wide variety of application monitoring tools. This seemed like a natural fit.
* **Prometheus**: This was somewhat of an arbitrary decision out of all the available monitoring tools. This particular tool, however, was open-source and seemed to have a lot of supporting documents for getting it configured and working with Micrometer.

A lot of these choices could have used a lot more in-depth consideration, and a lot more time ideally would be dedicated to better understanding the use of each tool. Given the timeframe for this project, I had to quickly pick something and start making progress. My career _so far_ has mostly had me work with codebases that have already had tools and frameworks like these chosen, set up, integrated, and configured. The timeframe for this project only allowed for a shallow understanding of many of these tools, but understanding how to configure and use frameworks like these on a deeper level is something I'd like to continue learning. 
