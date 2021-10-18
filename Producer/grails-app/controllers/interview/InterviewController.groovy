package interview

import groovy.transform.CompileStatic
import static grails.async.Promises.*
import javax.jms.*
import org.apache.activemq.ActiveMQConnectionFactory

@CompileStatic
class InterviewController {

	int booksRead = 0

    def sendBooksToMQ(){
    	//call our service layer to get the data into our object
		PostService postServ = new PostService()
		PostResult postRes = postServ.retrievePosts()
		LinkedList<Book> books = new LinkedList<>() 

		//get the first 25 (or if less, all) of the results
		for(int count = 0; count < 25 && count < postRes.books.size(); count++){
			books.add(postRes.books.getAt(count)) 
		}

		System.out.println("here!")

		//process the list in parallel using Promises
		def tasks = books.collect { book ->
			task {
				//send a message to MQ	
				def connFactory = new ActiveMQConnectionFactory()
				connFactory.setBrokerURL("tcp://localhost:61616")
				def conn = connFactory.createConnection()		
				def sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
				def dest = sess.createQueue("BookQueue")
				def prod = sess.createProducer(dest)		 
				def msg = sess.createTextMessage("Test Message")					 
				prod.send(msg)				
				conn.close()
			}
		}

		boolean done = waitAll()

		render (done ? "Books read successfully!" : "I can't read!")
    }

    //Debugging endpoint to check out the data
    def showBookList(){
		PostService postServ = new PostService()
    	render postServ.retrievePosts().books 
    }
}
