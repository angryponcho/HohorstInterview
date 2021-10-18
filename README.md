# HohorstInterview
 My submission for this interview process

The producer directory contains part one of the assignment, which I implemented in a Grails 5 app, using a single endpoint at /loadBooks with one controller, service, and domain class. I used micronaut's httpClient tools to retrieve the data from the provided online endpoint, and I used grails Promises to parallelize the processing of the retrieved data. Unfortunatly, due to a configuration issue with the ActiveMQConnectionFactory class, I was not able to get the app to connect to activeMQ. 

The consumer directory is a single-file node app which subscribes to the activeMQ queue and inserts books into the DB as they arrive. I used 'mysql' package for the DB connections, and the 'stompit' package for connecting to activeMQ. I was able to successfully test the second half by manually sending the first half's data to the activeMQ queue.

I've included here the short SQL script I used to create my Book table in my MySQL DB. I've also included a screenshot of the DB after loading some of the books with my node app.

Overall, I'm happy with the result, and I suspect a second pair of eyes could resolve the issue with ActiveMQConnectionFactory in short order.