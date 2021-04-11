package grails2

class UrlMappings {

    static mappings = {
//        "/$controller/$action?/$id?(.$format)?"{
//            constraints {
//                // apply constraints here
//            }
//        }
        get "/characters"(controller: 'characterx',action: 'index')
        get "/characters/$id"(controller: 'characterx',action: 'getChar')
        put "/characters/$id"(controller: 'characterx',action: 'update')
        post "/characters"(controller: 'characterx', action: 'create')
        delete "/characters/$id"(controller: 'characterx',action: 'delete')

        post "/users"(controller: 'user',action: 'create')
        put "/users"(controller: 'user',action: 'login')

        "/"(view:"/index")
        "500"(controller: 'error')
        "404"(view:'/notFound')
    }
}
