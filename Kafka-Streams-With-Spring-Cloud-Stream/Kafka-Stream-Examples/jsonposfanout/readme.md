# Section 6: Processin Kafka Stream

## 18. Processing JSON Message Stream

### SERDES
    - In this project, we will be using Kafka Streams API. We wont' be using Kafka Producer API.
    - The Kafka Streams API does not need a serializer. Instead, the Stream will be using a serde.
    - So, We add the kafka-stream-avro-serde    

### AVRO package
    - We use two files. 
        - Notification
        - Hadoop Record

### Requirements

    - 2. Select Invoices where CustomerType="PRIME" and create a notification event for the Loyalty Management Service.
        
            {
                "InvoiceNumber": "xx",
                "CustomerCardNo": "xx",
                "TotalAmount": "xx",
                "EarnedLoyaltyPoints": "xx"
            }
    - 3. Select all invoices, mask the personal information, create records for Trend Analytics. When the records are 
    ready, persist them to Hadoop storage for batch analytics.
            
            {
                "InvoiceNumber" : "xx",
                "CreatedTime": "xx",
                "StoreID": "xx"
                ...
            }
        - This Requirement needs two transformations:
            - Mask
            - Flatten

### Steps to Create a Listener
    - 1. Input/Output Channels
    - 2. Binding Interface
    - 3. Listeners Service