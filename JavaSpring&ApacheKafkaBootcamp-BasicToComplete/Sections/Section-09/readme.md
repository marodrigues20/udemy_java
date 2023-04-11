# Section 09. Scheduling Consumer

- We also can pause or resume consumer. For example, we use kafka to create a general ledger entry.
- This process is happened using this schema:
  - Source system receive financial transaction, for example, a payment.
  - After a transaction in place, source system publish a message to kafka, so a general ledger consumer will listen to kafka topic, take the transaction, and create a ledger entry. The problem is, every day at 11 PM to 12 PM, no ledger transaction allowed to be created. So we have two alternatives to do this. 
- First, block source system. Between 11 PM and 12 PM, no payment transaction happened.
- This is a very bad idea. If we say to customer : “Sorry, we can only receive payment at specific times”, it will surely upset them. In short, the payment system must live 24 hours, 7 days, with no downtime. Here is the second approach.
- The payment system still receive the payment, thus create transaction and publish message to kafka.
- But on 11PM to 12 PM, we will turn off kafka consumer for ledger.
- Note that the only affected thing is general ledger consumer. Kafka still up.
- Other consumers from other system still up. We separate general ledger application as separated spring boot application, so we can control it independently. By the way, this is a common architecture, separating a system according to its business domain.
- We still have several things happens on General Ledger, so we cannot turn it off entirely.
- We still have scheduler summarize daily ledger, or creating reports.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08/NonBlockingRetry.png?raw=true)