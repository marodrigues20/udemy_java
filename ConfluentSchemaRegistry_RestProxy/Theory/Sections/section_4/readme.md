# Section 4: Avro in Java

## Section Overview

    - In this section, we are going to view how to construct Avro records using Java.
    - We will explore different methods, all of which are valid for sending to Kafka afterwards.
    - We will visit schema evolution

## Generic Record

    - A GenericRecord is used to create an avro object from a schema, the schema being referenced as:
        - A file
        - A string
    - It's not the most recommended way of creating Avro objects because things can fail at runtime, but 
        it is the most simple way.
    - We'll learn to:
        - Create a Generic Record
        - Write it to a file
        - Read it from a file
        - Read the Generic Record

i.e: GenericRecordExamples.java


### Specific Record

    - A SpecificRecord is also an Avro object, but it is obtained using code generation from an Avro schema.
    - There are different plugins for different build tools (gradle, maven, sbt) etc, but in our example, 
        we'll use the official code generation tool shipping with Avro: Maven

                            Maven Plugin
            Avro Schema ======================> Generated Code
    
    - We'll perform the exact same tasks as the previous lecture, but all using a SpecificRecord now


i.e: SpecificRecordExamples.java


## 18. Check-in on now vs later in Kafka

### What we currently have achieved File

        
                                            Write the avro bytes                    Read the avro bytes
        Use Java to Create an Avro object --------------------------> Avro Files -------------------------> Use Java to Read an Avro object


### What we will achieve later Kafka!

                                       Write the avro bytes                              Read the avro bytes
    Use Java to Create an Avro object ------------------------> Kafka + Schema Registry -----------------------> Use Java to Read an Avro object



## 19. Avro Tools - Hands On

    - It is possible to read avro files using the avro tools commands

    - There are very handy when we want to display (print) data to our command line for a quick analysis of a content of an Avro file.

    - Let's learn how to use the Avro tools right now!

    - put this in any directory you like
        $ wget https://repo1.maven.org/maven2/org/apache/avro/avro-tools/1.11.0/avro-tools-1.11.0.jar

    - check the help
        $ java -jar avro-tools-1.11.0.jar
        
    - run this from our project folder. Make sure ~/avro-tools-1.11.0.jar.jar is your actual avro tools location
        $ java -jar avro-tools-1.11.0.jar tojson --pretty customer-generic.avro 
        $ java -jar avro-tools-1.11.0.jar tojson --pretty customer-specific.avro 

    - getting the schema
        $ java -jar avro-tools-1.11.0.jar getschema customer-specific.avro 


## 20. Avro Reflection

    - You can use Reflection in order to build Avro schemas from your class
    - This is a less common scenario but still a valid one! It's useful when you want to add classes to your Avro objects.

                                Reflection
    Existing Java Class --------------------------> Avro Schema and Object


    i.e: ReflectedCustomer.java
         ReflectionExamples.java


## 21. Schema Evolution - Theory

### Schema Evolution Business problem

    - Avro enables us to evolve our schema over time, to adapt with the changes from the business.

    - For example, today we're asking for the First Name and Last Name of our customer, and that's our v1 Avro schema, but tomorrow we may
        ask for their phone number. That would be our v2 of our schema.

    - We want to be able to make the shema evolve without breaking programs reading our stream of data.


### Schema Evolution High Level

    - There are 4 kinds of schema evolution:
        1. Backward: a backward compatible change is when a new schema can be used to read old data
        2. Forward: a forward compatible change is when an old schema can be used to read new data
        3. Full: which is both backward and forward


### Schema Evolutinos - 1. Backward Compatible

    1. Backward: a backward compatible change is when a new schema can be used to read old data.

    - We can read old data with the new schema, thanks to a default value. In case the field doesn't exist, Avro will use the default.
    - We want backward when we want to successfully perform queries
        (Hive-SQL for example) over old and new data using a new schema.

Note: Check the course to see the diagram.

### Schema Evolution - 2. Forward compatible

    2. Forward: a forward compatible change is when an old schema can be used to read new data

    - We can read new data with the old schema, Avro will just ignore new fields. Deleting fields without defaults is not forward compatible

    - We want forward compatible when we want to make a data stream evolve without changing our downstream consumers.

Note: Check the course to see the diagram.

### Schema Evolution - 3. Fully compatible

    3. Full: which is both backward and forward

    - Only add fields with defaults
    - Only remove fields that have defaults
    - When writing your schema changes, most of the time you want to target full compatibility (and it's not too hard, is it?)

### Schema Evolution - 4. Not compatible

    - Here are examples of change that are NOT compatible:
        - Adding / Removing elements from an Enum
        - Changing the type of a field (string => int for example)
        - Renaming a required field (field without default)

    DON'T DO THAT!!!
    DON'T!!!

### Advice when writing an Avro schema

    1. Make your primary key required
    2. Give default values to all the fields that could be removed in the future.
    3. Be very careful when using Enums as they can't evolve over time.
    4. Don't rename fields. You can add aliases instead (other names)
    5. When evolving a schema, ALWAYS give default values
    6. When evolving a schema, NEVER delete a required field

## 22. Schema Evolution

    - Let's go through a code demo of schema evolution and see how we go!

    - We'll see how Avro handles schema evolution seamlessly for us.

        Write with Old Schema (V1) ------------------> Read with New Schema (V2)  = Backward compatible change

        Write with New Schema (V2) ------------------> Read with Old Schema (V1)  = Forward compatible change

    i.e: SchemaEvolutionExamples.java


### Section Summary

    - We learned how to create an Avro Schema, using three methods
        - Generic Record
        - Specific Record (recommended way)
        - Reflection
    - We learned about Schema evolution
        - Backward
        - Forward
        - Full
    - We learned the rules to write a great Avro schema



