package interview

import groovy.transform.CompileStatic

@CompileStatic
class PostResult {
    int resultsCount
    public LinkedList<Book> books = []
}
