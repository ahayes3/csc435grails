package grails2

class ErrorController {

    def index() {
        Exception e = request.getAttribute('exception') as Exception
        if(e instanceof CodeException) {
            render(contentType: 'text/json','{"error": '+e.code+',"message": '+e.message+'}')
        }
        else {
            render(contentType: 'text/json','{"error": 500}')
        }
    }
}
