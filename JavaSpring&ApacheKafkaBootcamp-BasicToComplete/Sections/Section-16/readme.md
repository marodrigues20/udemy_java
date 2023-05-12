# Section 16. Kafka Stream - Flash Sale Vote

## 94. Most Recent Data Feed

- For marketing program, we will give customer list of item as flash sale candidate.
- Customer can vote from this list candidate during certain time range. The next flash sale will be held based on most voted item. One customer can only select one candidate, but they can change selection as long as still in time range.
- Since one customer can only select one flash sale candidate, but can change her choice, means we have to track latest selected item. This is a good candidate for Kafka Stream Table.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_01.png?raw=true)

