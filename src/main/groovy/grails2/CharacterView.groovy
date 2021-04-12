package grails2

import com.google.gson.Gson

import java.util.stream.Collectors

class CharacterView {
    static def g = new Gson()
    static def render(Closure c,Set<Characterx> l) {
        c(g.toJson(l.stream().map({ p -> new CharacterIR(p) }).collect(Collectors.toSet())))

    }
    static def render(Closure c,Characterx cx) {
        c(g.toJson(new CharacterIR(cx)))
    }
}
