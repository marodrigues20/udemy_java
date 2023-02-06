# Section 4: XML Hands On


## 17. XML Concepts

- Every XML documents starts with a declaration also called a prolog statement. In this lecture you will learn the syntax
of XML declaration by starting working on the Drivers License Use Case.

- Xml Created "DriversLicense.xml" in DriversLicenceXML project.

- By default the eclipse create in xml the bellow declaration:
	- <?xml version="1.0" encoding="UTF-8"?>
	
- It starts with a less than symbol, question mark and it ends with a question mark and greater than symbol.
- These symbols tell the XML parser or the processor that this is a processing instruction and it is not the actual XML document or the elements and attributes in the XML document.
- We can have other types of processing instructions like stylesheets, etc.
- This XML tells the XML parser that this element is from the XML specification and not stylesheet. The version,
using the version attribute on this element we can specify what version of XML we are using, the current version of XML is
1.0. I will explain about the encoding and also as a standalone attribute in the next lecture.
- To summarize, the very first line in every XML document is a XML declaration. IDEs like Eclipse will create that line for you by default.
- The syntax for it is less than symbol, question mark and it ends with a question mark and greater than symbol.
- We have various attributes on it like version, encoding and standalone.

## 18. Encoding Attribute

- <?xml version="1.0" encoding="UTF-8"?>

- In this lecture, you will learn how to use the encoding attribute on the XML declaration or the prolog.
- Encoding is used to specify a character set that we are going to use to represent the text within our XML document.
- By default it is UTF-8.
- So if you take this encoding out, delete this attribute entirely, by default this XML parser will assume UTF-8.
- But we can also use other character sets like UTF-16 which can represent most of the characters from most of the languages on this planet. And we can also use other character sets like ISO, Shift_JIS, EUC-JP and so on.
- To summarise, by default the encoding for the characters in our XML is UTF-8 but you can use other character sets as well.


## 19. Standalone Attribute

- This standalone attribute tells the XML parser whether this XML document exists on its own or whether it can use documents from outside like XML Schema.
- So this standalone can have a value of yes or no.
- Yes tell the XML processor that there are no external documents required for parsing this document and this XML document exists on its own.
- The default value is no, which tells the XML processor that this XML document can use other documents like XML schema or XML stylesheets and so on.

- <?xml version="1.0" encoding="UTF-8" standalone="no"?> 

	- XML schema
	- XML stylesheets
	- and so
	
	
## 20. Root Element

- Elements are the heart of every XML document. A XML document is comprised of one or more elements nested inside each other.
- It should always start with a root element.
- Start and end tags of an element must be identical.
- Names are case sensitive
- An element name can contain any alphanumeric characters.
- The only punctuation mark allowed in names are the hyphen (-), under-score(_) and period (.).

## 21. Create Child Elements

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<DriversLicense>
	<Number>12345</Number>
	<FirstName>Bharath</FirstName>
	<LastName>Thippireddy</LastName>
	<DataOfBirth>18/may/1981</DataOfBirth>
	<VehicleType>Car</VehicleType>
	<DataOfIssue>11/june/2012</DataOfIssue>
	<ExpirtyDate>11/june/2015</ExpirtyDate>
</DriversLicense>
```


## 22. Attributes

- Attributes add some special meaning for the elements.
- Another example could be, we can have a unique ID or we can have a status on the drivers license which tells it's a 
active or whether its canceled or whether it was suspended for some beautiful reasons like you have driven 150 on a 50
limit road.

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<DriversLicense status="suspen">
	<Number>12345</Number>
	<FirstName>Bharath</FirstName>
	<LastName>Thippireddy</LastName>
	<DataOfBirth>18/may/1981</DataOfBirth>
	<VehicleType>Car</VehicleType>
	<DataOfIssue>11/june/2012</DataOfIssue>
	<ExpirtyDate>11/june/2015</ExpirtyDate>
	<Photo location="servername/filename"></Photo>
</DriversLicense>
```

- To summarize, attributes are name value pairs. The names always follow the same naming conventions as the elements
and the values should always be inside double quotes or single quotes.

## 23. Empty Elements

- Elements that do not enclose any child elements or text data are called empty elements. In our DriversLicense.xml, the element Photo does not include any textual data or child elements of its own.
- It's empty.
- It simply has attribute on it called location which tells about the location of the photograph.
- That is why they are useful.
- They add value only through their attributes. But these elements should always be closed either by using an end element or we can use the shorthand notation where you can type in the forward slash right here on the start element and we can delete the end element completely.

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<DriversLicense status="suspen">
	<Number>12345</Number>
	<FirstName>Bharath</FirstName>
	<LastName>Thippireddy</LastName>
	<DataOfBirth>18/may/1981</DataOfBirth>
	<VehicleType>Car</VehicleType>
	<DataOfIssue>11/june/2012</DataOfIssue>
	<ExpirtyDate>11/june/2015</ExpirtyDate>
	<Photo location="servername/filename"/>
</DriversLicense>
```

## 24. Comments

- Syntax for comments is: 

```
<!-- -->
```

- To summarise, comments are statements within a XML that are skipped by the XML parser.

## 25. Character Entities

- We can't use some symbols in the value data. Like: <, > and etc.

We have to use special codes bellow:

```
<  =   &lt;
>  =   &gt;
&  =   &amp;
'  =   &quot;
"  =   &apos;
```

## 26. CDATA Blocks

- We can include any text, any characters inside our XML document. The syntax for the CDATA block starts with a less
then symbol followed by NOT, square bracket, and then text CDATA.
- These CDATA blocks are usually used to give examples.
- We can include an intire XML document here or we can include a piece of our XML document like the DataOfIssue in our 
case. If you want to showcase the format of the date that should be used and how it should be used, we can give examples
like this:


```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<!-- This xml document holds the license information -->
<DriversLicense status="suspen">
	<Number>12345</Number>
	<FirstName>Bharath</FirstName>
	<LastName>Thippi&lt;reddy</LastName>
	<DataOfBirth>18/may/1981</DataOfBirth>
	<VehicleType>Car</VehicleType>
	<DataOfIssue>11/june/2012</DataOfIssue>
	
	<![CDATA[
	
	<DataOfIssue>11/june/2012</DataOfIssue>
	
	]]>
	
	
	<!-- For Citizens it is 4 years and for visa holders it is the visa expiry date -->
	<ExpirtyDate>11/june/2015</ExpirtyDate>
	<Photo location="servername/filename"/>
</DriversLicense>

```

- We can have any number of CDATA blocks within our document anywhere but we cannot nest one CDATA block inside
another CDATA block.


## 27. Nested Elements

- We will demonstrate how XML can have nested elements inside nested elements, multiples levels of nestedness.



```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<!-- This xml document holds the license information -->
<DriversLicense status="suspen">
	<Number>12345</Number>
	<FirstName>Bharath</FirstName>
	<LastName>Thippi&lt;reddy</LastName>
	<DataOfBirth>18/may/1981</DataOfBirth>
	<VehicleType>Car</VehicleType>
	<DataOfIssue>11/june/2012</DataOfIssue>
	
	<![CDATA[
	
	<DataOfIssue>11/june/2012</DataOfIssue>
	
	]]>
	
	
	<!-- For Citizens it is 4 years and for visa holders it is the visa expiry date -->
	<ExpirtyDate>11/june/2015</ExpirtyDate>
	<Photo location="servername/filename"/>
	
	
	<Address>
		<street>stevens dr</street>
		<city>king of prussio</city>
		<state>PA</state>
		<country>USA</country>
		<zipcode>19406</zipcode>
	</Address>
</DriversLicense>
```

## 28. Well-Formedness Recap

- Must have a root element
- XML eleemnts must have a closing tag
- XML elements must be properly nested
- XML tags are case sensitive
- XML attribute values must be quoted




















