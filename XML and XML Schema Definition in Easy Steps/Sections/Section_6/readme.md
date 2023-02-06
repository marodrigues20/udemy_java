# Section 6. XML Schema Definition Hands On

## 34. Patient Management Usecase

- In this lecture, I will introduce you to the Hospital Management or Patient Management usecase for which we'll be creating a XML schema.
- Let's say you and me are working as a team on integrating two applications namely, the Patient Financials application which deals with the patient personal information, his insurance information and all that when he is checked into a hospital. And then the Patient Clinical application which deals with his X-rays, blood tests and all that.
- What these two have in common is the patient information and they want to exchange that information using XML.
- Our job is to come up with a XML schema file which acts as a contract between these two applications.
- These two applications agree on this schema file.
- And when Patient Financials sends a patient XML to Patient Clinicals, it can use this schema to validate that XML and vice versa.
- Our business analyst has given us the following requirements to start with.
- We should have a name in this schema for the patient, his age, Email address, gender and a telephone number.
- These are the requirements she wanted us to get started with. And as a typical business analyst she also told us that we will have more requirements as we make progress.

## 35. XML Schema Data Types

- The XML schema inbuilt datatypes allow us to specify what type of data our XML elements and attributes can carry. These datatypes are defined by the W3C in the XML schema specification. All the major XML processors and parsers know about all these types. Just like other programming languages like C, C++, Java, Python, these datatypes start with the numeric types, byte which can carry 8 bit integer values, short which can take 16 bit integer values, integer that can take 32 bit integer values, long that can take 64 bit integer values and then decimal which can carry floating point numbers or decimal numbers.


```
| Number Types |            Lengh       |
|     byte     | A signed 8-bit integer |
|     short    | A signed 16-bit integer|
|     int      | A signed 23-bit integer|
|     long     | A signed 64-bit integer|
|     decimal  |                        |
```

- We have several other types like unsigned byte, etc, which we rarely use. if you want to check them out, you can simply google for numeric schema types or numeric XML schema types and you will find the entire list.
- The second important type we have is the date type:

| Date Types |          Describes            |
|   date     | Defines a data value          |
|   dataTime | Defines a date and time value |  
|   time     | Defines a time value          |

- And then we have several other values which we rarely use like the day, month etc, which can carry portions of the date like the day, month and so on.

- Finally we have the string types. Important here is the string.
- There are a few other types but again we rarely touch them.
- The string type represents a set of characters which we usually use to represent names and so on.
- All these inbuilt types can be used or will be used by us to come up with our own simple types using the XML schema restrictions.

### Summarize

- To summarize, the XML schema specification from W3C defines several inbuilt types that we use to mark our elements and attributes. Once we mark them, those elements and attributes can carry only those type of values in the XML document. They are numeric types, like byte, short, int, etc, date types and the string types.
- We use these types to come up with our simple and complex types.

## 36. Assigning Types to Fields

- In this lecture, we will assign the different inbuilt data types to our XML fields.
- We'll start with the Name. Name is a set of characters, so that's easy.
- We can use the inbuilt string type.
  - Age is numeric.
  - So let's use int here.
  - Email again is a set of characters, string again. Gender, for now we'll leave it as a string type.
  - We'll be extending this string type later on when you learn about simple types and restrict the value of Gender to just have a few values. Then the Phone number, let's have it as a string as well.
  - And we are all set.
  - So it's that simple.
 
 - We have assigned the different inbuilt types which we'll be using when we create our schemas for these fields.

## 37. Namespace Basics

- Namespaces uniquely identify the elements and attributes of a XML document. When we are creating a schema file, for example let's say we are working on creating schema files for amazon.com and ebay.com to handle their orders.
- The first step would be, when we define a schema we will assign a target namespace for all the elements in the Amazon's order by using the target namespace attribute from XML schema specification.
- And we will use a unique URL from Amazon.
- It is always a good practice to use a URL of the company because it is unique across the world and across the internet for a namespace. And then if we are defining a target namespace for ebay, it would look like wherein it'll use the ebay's domain name or the ebay's URL.
- Once we define a target namespace in their respective schema files, we can use a prefix. Instead of using this entire URL to qualify each element in the XML schema and XML later on, we can use a prefix like this. xmlns stands for XML namespace.
- This attribute is from XML specification, colon, whatever prefix we want to use. This prefix, from this point in time will refer to this URL. And then we can qualify all the elements using this amz instead of this entire URL.
- Similarly if we are defining a prefix for ebay, here is the xmlns:, ebay is the prefix.
- This could be xyz or anything and then the actual namespace. Once we define the target namespace and qualify all the elements that we create with these prefixes.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/XML%20and%20XML%20Schema%20Definition%20in%20Easy%20Steps/Sections/Section_6/Namespace_xsd.png?raw=true)


- when we create the XML document that XML document also should use the exact namespace. 
- For example the order for Amazon, the root element for order will have a xmlns:, a prefix defined for the namespace and all the elements in that order will be qualified with that prefix.
- Similarly the order for eBay will use a xmlns:ebay and then all the elements inside again will be qualified with the eBay's namespace. For some business
reason, if we have to use multiple orders from eBay and Amazon in a single XML document, looking at the namespace we know from which company or if the order is from Amazon or is the order from eBay.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/XML%20and%20XML%20Schema%20Definition%20in%20Easy%20Steps/Sections/Section_6/Namespace_xml.png?raw=true)


### Summarize

- To summarize, namespaces allow us to uniquely identify the elements and attributes in a XML document.
- We create target namespaces in the schema file which tells that all the elements in that schema file should follow certain namespace and then instead of using the entire URL to qualify each element in the schema we use a prefix.
- If you are from a object oriented or programming background, in the world of Java, namespace is nothing but a package wherein you create classes and put them into a certain package.
- And in the .Net world it is called namespace, where you create classes and put them into namespaces.
- You will learn some advanced namespace topics in sections later on.
- But for now this information is pretty good for you to get started with XML schema creation and you will be creating target namespaces, prefixing the elements in that schema file with those namespaces.

## 38. Schema Creation

