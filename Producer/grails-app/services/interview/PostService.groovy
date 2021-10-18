package interview

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.uri.UriBuilder
import com.fasterxml.jackson.databind.ObjectMapper

@CompileStatic
@Transactional
class PostService {

	//Call the API to get our json object
    PostResult retrievePosts() {
        String baseUrl = "https://jsonplaceholder.typicode.com/"
        HttpClient client = HttpClient.create(baseUrl.toURL())
        HttpRequest request = HttpRequest.GET(UriBuilder.of('/posts').build())
        HttpResponse<String> resp = client.toBlocking().exchange(request, String) 
        String json = resp.body()
        ObjectMapper objectMapper = new ObjectMapper()
        //load and return the data in our custom datatype
        PostResult postResult = new PostResult()
        postResult.books = objectMapper.readValue(json, LinkedList<Book>) 
        postResult;
    }

}
