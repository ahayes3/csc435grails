package grails2

import org.grails.web.errors.GrailsWrappedRuntimeException

import java.lang.reflect.InvocationTargetException

class ErrorController {

    def index() {
        GrailsWrappedRuntimeException e = request.getAttribute('exception') as GrailsWrappedRuntimeException
        if((e.cause as InvocationTargetException).getTargetException() instanceof CodeException) {
            def z = ((e.cause as InvocationTargetException).getTargetException() as CodeException)
            render(contentType: 'text/json','{"error": '+z.code+',"message": '+z.message+'}')
        }
        else {
            render(contentType: 'text/json','{"error": 500}')
        }
    }
}
