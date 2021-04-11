package grails2

import com.google.gson.Gson

class CharacterView {
    static def g = new Gson()
    static def render(Closure c,Set<Characterx> l) {
        c(g.toJson(l))

    }
    static def render(Closure c,Characterx cx) {
        c(g.toJson(cx))
    }
}
