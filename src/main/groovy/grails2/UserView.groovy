package grails2

import com.google.gson.Gson

class UserView {
    static def g = new Gson()

    static def render(Closure c, User u) {
        c(g.toJson(new UserIR(u)))
    }
}
