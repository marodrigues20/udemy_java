# Section 5: XML Schema Definition Concepts


## 30. What is a XML Schema

- From this lecture, you will learn what a XML schema definition is. A XML schema defines the grammar or a blueprint for a XML document. That
is, we can use the XML schema to mention what elements can be there in a XML, what attributes can be there, what namespaces that XML can use, the order in which the elements should occur, the number of occurences of each element and also we can restrict the data inside the XML document only
to certain values. All that can be done using the XML schema definition.
- If a XML document follows the schema file, if it has a XML schema defined and if it follows that schema file it is called a valid XML document. A XML schema definition is also a XML file with a .xsd extension. Instead of xml it will have a .xsd which stands for XML schema definition.
- All the elements that we can use inside a schema file are provided by W3C, the World Wide Web Consortium.
- They define the specification of the XML schema.
- For example, if we have a order.xml, we can have a order.xsd which says what elements can be present and what attributes can be present and in which order in the order.xml.
- To summarize, a schema file, using a schema file we can define the grammar for our XML documents. A schema file is also a XML file and the elements
that can be used in the schema file are provided by the World Wide Web Consortium.
If a XML document has a schema file defined and if it follows that schema file, it is called a valid XML document.

## 31. Why a XML Schema

- Why do we need a XML schema file and where do we use it?
- A XML schema file is a contract between two XML users.
- That is, if Application1 and Application2 are exchanging XML messages for some data, without a contract defined for what elements and attributes should be there in this XML document, Application1 can send in any data in the XML which Application2 doesn't even care or doesn't even understand.
- Similarly Application2 can also do something similar by sending wrong data or additional data which Application1 doesn't care. That is where XML schema definition comes into picture.
- And these two applications right when they are being developed, can agree on a contract by defining all the elements and attributes inside the XML schema definition file for this XML. Similarly, let's say we have bought a new Employee Management software for our enterprise.
- And now this Employee Management software from a third party has to access our employee database and the third party software has asked us to use a XML configuration file so that we tell this software where our database is and how to access it inside that XML configuration file.
- This third party software will validate our XML that we come up with against this schema file and they provide us this schema file so that by looking at this schema file we will know what elements we can include inside that configuration file.
- So wherever and whenever you use XML, to make sure that the XML is valid, it is carrying valid data, we need a contract and that is where XML schema definition comes into picture and we can specify everything that a XML can have inside a XML schema definition.

## 32. XML Schema Types

- The XML schema definition specification gives us three different types that we can use in the XML schema to mention what type of data our XML documents carry. They are inbuilt types, these are defined by the W3C. They are integers, decimals, string, date, datetime and there are several other types which I will show you later on.
- You can use all these types when we build our schema document and mention that our element should be one of these types or our attributes should be one of these types.
- We can then even extend these inbuilt types by creating our own simple types. If we want to restrict, for example if we want to restrict a string to 15 characters we can use the inbuilt string type as the base and then we can use a restriction from the XML schema and we can create a simple type. And then we can apply that simple type when we define the elements inside our schema file like this. You will see all this in action, you will do this handson in the next few lectures.
- So far you have seen the inbuilt types and then to create simple types by extending those inbuilt types.
- Finally we also have complex types. Complex types are elements which can have other elements inside them. We can create complex types either by using the inbuilt types or simple types or other complex types. We can use the combination of these three.
- So in this lecture, you have learnt the three different types which we use in our schema document.
- The inbuilt types which are defined by the W3C.
- And then the simple types which we create, these are the custom data types.
- The syntax for creating a simple type is again provided by W3C. And finally the complex types. If you are from object oriented background then complex types are like creating your classes by using the available inbuilt types.


