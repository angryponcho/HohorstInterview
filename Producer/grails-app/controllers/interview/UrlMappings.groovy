package interview

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/loadBooks"(controller: 'interview', action: 'sendBooksToMQ')        
        "/showBooks"(controller: 'interview', action: 'showBookList')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
