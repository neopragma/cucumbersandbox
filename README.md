# cucumbersandbox

Sample starter code demonstrating how to set up cucumber-jvm with Java8 for API testing.

## Dependencies

Assuming the goal is to test RESTful APIs that pass JSON objects, these are the relevant dependencies:

* info.cukes.cucumber-java8 - Cucumber-JVM implementation for Java 8
* info.cukes.cucumber-junit - Cucumber runner for JUnit 8
* junit.junit (version 4+) - JUnit testing framework
* org.json.json [1] - JSON support
* com.mashape.unirest.unirest-java [1] - REST support

[1] or any other suitable library

See the ```pom.xml``` file for full details.

## Directory Structure

* Feature files go in src/test/resources/[package.name]/*.feature
* Stepdef files go in src/test/java/[package.name]/*Test.java
* Test runner class is src/test/java/[package.name]/RunCukesTest.java
* Sample stepdef class is src/test/java[package.name]/RpnServiceStepdefs.java

The sample code uses lambda style stepdefs rather than annotation style.

## Target application

The sample code invokes ```http://rpn-service.herokuapp.com/```, which was active at the time the sample code was written.

## Execution

Of course, you can run the tests with any tool you please and with any runtime arguments you please. For demonstration purposes, I use maven and run the features like this:

```shell
mvn clean test -Dcucumber.options="--plugin html:target/functional-test-results --glue classpath:com.neopragma.cucumbersandbox src/test/resources"
```

There's a shell script, ```runtests```, that runs this command. (Not for Windows)

With these options, the test results are written as HTML in directory ```target/functional-test-results```.

## CucumberOptions

I suggest specifying cucumber options on the command-line command you use to run Cucumber, rather than on the @CucumberOptions annotation on the test runner class declaration. This gives you flexibility to control runtime arguments when the scripts are executed as part of a CI process or for _ad hoc_ testing. 
