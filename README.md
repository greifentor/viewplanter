# ViewPlanter
A tool for database view dependency visualization.


## What does it do?

The tool will be able to read view information via JDBC from a database, process this information into an internal 
structure and convert it to PlantUML code which shows the dependencies between the views and the accessed database 
tables.

**Note:** This will work for views only, which are referencing to other views or tables using FROM or JOIN. Other more 
'magical' references e. g. via procedure calls or similar stuff will not be respected.

**Note Also** It is planned to support PostgreSQL and MariaDB (and maybe HSQDB), but it should be easy to create links 
to other database systems.


## Requirements

* Java 11+
* Maven 3.5.+


## Build

Just type

```
mvn clean install
```

on your preferred shell tool.


## Run

THERE IS NOTHING TO START CURRENTLY !!! :)
