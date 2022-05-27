# Section 3: Avro Schemas

## 6. What is Avro?

### AN Evolution of data Comma Separated Values (CSV)

    - Advantages
        - Easy to parse
        - Easy to read
        - Easy to make sense of

    - Disadvantages:
        - The data type of elements has to be inferred and is not a garantee
        - Parsing becomes tricky when data contains commas
        - Column names may or may not be there

### An Evolution of data Relational tables definitions

    - Relational table definitions add types:

    - Advantages:
        - Data is fully typed
        - Data fits in a table
    Disadvantages
        - Data has to be flat
        - Data is stored in a database, and data definition will be different for each database

### An Evolution of data JSON (JavaScript Object Notation)

    - JSON format can be shared across the network!

    - JSON format

    - Advantages:
        - Data can take any form (arrays, nested elements)
        - JSON is a widely accepted format on the web
        - JSON can be read be pretty much any language
        - JSON can be easly shared over a network
    - Disadvantages:
        - Data has no schema enforcing
        - JSON Objects can be quite big in size because of repeated keys


### An Evolution of data AVRO

    - Avro is defined by a schema (schema is written in JSON)
    - To get started, you can view Avro as JSON with a schema attached to it.

### An Evolution of data AVRO

    - Advantages:
        - Data is fully typed
        - Data is compressed automatically (less CPU usage)
        - Schema (defined using JSON) comes along with data
        - Documentation is embedded in the schema
        - Data can be read across any language
        - Schema can envolve over time, in a safe manner (schema evolution)

    - Disadvantages
        - Avro support for some languages may be lacking (but the main one is fine)
        - Can't "print" the data without using the avro tools (because it's compressed and serialised)


### Avro vs Protobuf vs Thrift vs Parquet vs ORC vs ....

    - Overall, all of these data formats achieve pretty much the same goal.
    - At Kafka's level, what we care about is one message being self explicit and fully described as we're dealing with streaming (so no ORC Parquet etc)
    - Avro has good support from Haddop based technologies like Hive
    - Avro has been chosen as the only supported data format from Confluent Schema Registry so we'll just go along with that!
    - There is no need to compare performance etc unless you can prove that. Avro is indeed a performance roadblock in your programs 
        (and that won't happen unless you reach insane volumes of 1 million messages per sec )


## 7. Avro Primitive Types

    - Primitive Types are support base types
        - null: no values
        - boolean: a binary value
        - int: 32-bit signed integer
        - int: 64-bit signed integer
        - float: single precision (32-bit) IEEE 754 floating-point number
        - double: double precision (64-bit) IEEE 754 floating-point number
        - bytes: sequence of 8-bit usigned bytes
        - string: unicode character sequece

## 8. Avro Record Schema Definition
    
    - Avro Record Schemas are defined using JSON
    - It has some common fields:
        - Name: Name of your schema
        - Namespace: (equivalent of package in Java)
        - Doc: Documentation to explain your schema
        - Aliases: Optional other names for your schema
        - Fields
            - Name: Name of the field
            - Doc: Documentation for that field
            - Type: Data type for that field (can be a primitive type)
            - Default: Default Value for that field


### Avro Schemas Practice 1

    - Let's practice defining an Avro schema
    - We have a Customer
        - First Name
        - Last Name
        - Age
        - Heigh (in cms)
        - Weight (in kgs)
        - Automated email turned on (Boolean, default true)

i.e: customer.avsc


### Complex Types

    - In Avro you have complex types, such as:
        - Enums
        - Arrays
        - Maps
        - Unions
        - Calling other schemas as types

    - Let's go over these types one by one

### Enums

    - These are for fields you know for sure that their values can be unumerated

    - Example: Customer status
        - Bronze
        - Silver
        - Gold

    

    - Note: once an enum is set, changing th enum values is forbidden if you want to maintain compatibility.
    - Note2: { "name":"CustomerStatus", "type": "enum", "symbols":["BRONZE", "SILVER", "GOLD"]}


### Arrays

    - Arrays are a way for you to represent a list of undefined size of items that all share the same schema

    - Example: Customer Emails (multiple emails)
    - ["john.doe@gmail.com", "jon92@hotmail.com"]

    - {"type":"array", "items":"string"}

    - Note: the schema can be anything you want so you can use any existing schemas for it


### Maps

    - Maps are a way to define a list of keys and values, where the keys are strings.

    - Examples: secrets questions
        - "What's your favorite colour?" : "green"
        - "where were you born?": "Paris"
        - "Name of first bet?": "Mr Snuggles"

    - {"type":"map", "values":"string"}

    - Note: don't stores secrets in Avro. This is just to illustrate the concepts of maps


### Unions

    - Unions can allow a field value to take different types.
    - Example: ["string","int","boolean"]

    - If defaults are defined, the default must be of the type of the first item in the union (so in this case "string").

    - The most common use case for unions is to define an optional value.
        - {"name":"middle_name", "type":["null","string"], "default":null}

    - Note: for the default don't write "null", write null


## 10. Practice Exercise: Customer & CustomerAddress

    - Let's practice defining a complex Avro schema

    - We have a Customer
        - First Name
        - Middle Name (optional)
        - Last Name
        - Age
        - Automated email (Boolean, default true)
        - Customer Emails (array)
        - Customer Address

    - We have a CustomerAddress
        - Address
        - City 
        - Postcode (number or string)
        - Type (Enum: PO BOX, RESIDENTIAL, ENTERPRISE)


## 11. Avro Logical Types

    - Avro has a concept of logical types used to give more meaning to already existing primitive types.

    - Type most commonly used are:

    - decimals (bytes - see next lecture)

    - data (int) - number of milliseconds after midnigh, 00:00:00:000

    - timestamp-millis (long) - the number of milliseconds from the unix epoch, 1 January 1970 00:00:00.000 UTC

    Note: Logical Types are interpretation of primitives types (a date is an int for example, and a timestamp-millis a long)


### How to use a logical type?

    - To use logical type, just add "logicalType":"time-millis" to the field name and it will help avro schema processors to inter a specific type.

    - Example: Customer Signup timestamp

        - {"name":"signup_ts, "type":"long", "logicalType":"timestamp-milles}

    - Note: logical types are new (1.7.7), not fully supported by all languages and don't play nicely with unions.
        Be careful when using them!

    


### The complex case of Decimals - Floats, Doubles and Decimals

    - Floats and Doubles are floating binary point types. They represent a number like this: 10001.10010110011

    - Decimal is a floting decimal point type. They represent a number like this: 12345.65789
      Some decimals cannot be represent accurately as floats or doubles!

    - People use floats and doubles for specific computations (imprecise computations) because these types are fast

    - People use decimals for money. That's why it got created in the first place.
        Use decimal when you need "exactly accurate" results
    

### The complex case of Decimals in Avro

    - Avro introduces a decimal logical type, but its underlying type is "bytes". That means that if you print an avro as json (we'll see in the next lectures),
        you won't see the decimal value, just some gibberish.

    - Additionally, transforming these bytes into a decimal is very error prone if the language library you're using didn't implement that feature.
        The official Avro library for Java didn't get it right the first time! (AVRO-1869)


### How to deal with decimals in Avro?

    - I would advise against using Decimals as a logical type for now. If you're using other languages than Java. Wait until the toolset matures and all
        using other languages than Java. Wait until the toolset matures and all library have solid support for it.

    - In the meantime, here are the alternatives:
        - Use "string" to represent the decimal value. It will look good when priting the data and is easy to parse and understand.
        - Create your own "Decimal" type with the following:
            - Integer part (long)
            - Decimal part (long)
        - Or create your own "Fraction" type with the following:
            - Numerator (long)
            - Denominator (long)







    










