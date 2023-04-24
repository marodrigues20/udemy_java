# Section 14: Feedback Stream

## 85. Are You Good Enough?

- The quality service department provides customer feedback form.
- The customer will fill rating and free-text feedback for certain branch location.
- Everybody loves 5 star rating and feedback, but in the commodity company, rating is not enough.
- The quality service team wants to analyze customer emotion from feedback text.
- The criteria is simple: We will have list of “good words”, and the more “good word” received, the better that branch will be.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_01.png?raw=true)


 - For this, we will create a feedback stream with this topology. The source is from feedback topic, and we will have stream processor that analyze feedback text to find out what are the “good words” in each feedback text.
  

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_02.png?raw=true)

