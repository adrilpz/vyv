                  ********** pojo-examples **********

Initializing the database and building the examples
---------------------------------------------------

- mvn sql:execute install

Running Web appplications
-------------------------

pojo-tapestrytutorial, pojo-advhibernatetut and pojo-minibank provide Web
applications. pojo-advhibernatetut and pojo-minibank require the database
server to be running.

Below it is indicated how to run pojo-minibank. Rest of Web applications are
run in a similar way.

+ Running with Maven/Jetty.

    * cd pojo-minibank
      mvn jetty:run

    * Browse to http://localhost:9090/pojo-minibank

+ Running with Tomcat.

    * Copy the ".war" file (e.g. pojo-minibank/target/pojo-minibank.war) to
      Tomcat's "webapp" directory.

    * Start Tomcat:
      cd <<Tomcat home>>/bin
      startup.sh

    * Browse to http://localhost:8080/pojo-minibank.

    * Shutdown Tomcat:
      shutdown.sh
