package grails2

import com.google.gson.Gson

import javax.servlet.http.HttpServletRequest
import java.util.stream.Collectors

class UserController {
    def g = new Gson()
    def r = {c ->
        render(contentType: 'text/json',c)
    }
    def create() {
        Tuple2<String,String> req = parseUser(request)
        def a = User.findByName(req[0] as String)
        if(a!=null) {
            throw new CodeException(403,"Username "+req[0] +" already taken.")
        }
        def u = new User(name:req[0].toString(),pass:req[1].toString(),characters:new HashSet())
        u.save()
        UserView.render(r,u)
    }
    def login() {
        def req=parseUser(request)
        def a = User.findByName(req[0] as String)
        if(a==null) {
            throw new CodeException(404,"User not found")
        }
        else if(a.pass != (req[1] as String)) {
            throw new CodeException(403,"Passwords don't match")
        }
        session["user"]=(req[0] as String)
    }

    private Tuple2<String,String> parseUser(HttpServletRequest req) {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()))
        Map m = g.fromJson(body,Map.class)
        if(!m.keySet().containsAll(["user","pass"])) {
            throw new CodeException(400,"Bad Request")
        }
        String user = m["user"]
        String pass = m["pass"]
        return new Tuple2(user,pass)

    }
}
