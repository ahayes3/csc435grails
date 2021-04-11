package grails2

import com.google.gson.Gson

import javax.servlet.http.HttpServletRequest
import java.util.stream.Collectors

class CharacterxController {
    def r = {c ->
        render(contentType: 'text/json',c)
    }
    static g = new Gson()
    def index() {
        checkLogin()
        println(session["user"])
        Set<Characterx> myChars = Characterx.findAllByUser(session["user"].toString())
        CharacterView.render(r,myChars)
    }

    def getChar() {
        checkLogin()
        Integer id = params.id
        def cr = Characterx.findByIdAndUser(id, session["user"] as String)
        CharacterView.render(r,cr)
    }
    def delete() {
        checkLogin()
        Integer id = params.id
        def cr = Characterx.findByIdAndUser(id, session["user"] as String)
        if(cr.user != session["user"]) {
            throw new CodeException(403,"Forbidden")
        }
        cr.delete()
        CharacterView.render(r,cr)
    }
    def update() {
        checkLogin()
        Integer id = params.id
        def cr = Characterx.findByIdAndUser(id, session["user"] as String)
        if(cr==null) {
            throw new CodeException(404,"Not Found")
        }
        else if(cr.user!= session["user"]) {
            throw new CodeException(403,"Forbidden")
        }
        def n = parseChar(request)
        cr.update(n)
    }
    def create() {
        checkLogin()
        def cr = parseChar(request)
        cr.save()
        def u = User.findByName(session["user"] as String)
        u.characters.add(cr)
        CharacterView.render(r,cr)
    }
    private def checkLogin() {
        if(session["user"]==null) {
            throw new CodeException(401,"Unauthorized")
        }
    }
    private Characterx parseChar(HttpServletRequest req) {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()))
        Map m = g.fromJson(body,Map.class)["character"]
        def str= (m["str"] as Double).intValue()
        def dex = (m["dex"] as Double).intValue()
        def con= (m["con"] as Double).intValue()
        def intel= (m["intel"] as Double).intValue()
        def wis= (m["wis"] as Double).intValue()
        def cha= (m["cha"] as Double).intValue()
        def ac=(m["ac"] as Double).intValue()
        def init= (m["init"] as Double).intValue()
        def speed= (m["speed"] as Double).intValue()
        def maxHp= (m["maxHp"] as Double).intValue()
        def name = m["name"] as String
        def bg = m["background"] as String
        def race = m["race"] as String
        def languages  = makeList("languages",m)
        def features = makeList("features",m)
        def tools = makeList("tools",m)
        def skills = makeList("skills",m)
        def items = makeList("items",m)
        def classes = classes(m)

        return new Characterx(clazzes: classes,skillProfs: skills,toolProfs: tools,items: items,features: features, languages: languages,
                name: name,background: bg, race: race, str: str,dex: dex,con: con,intel: intel,wis: wis,cha: cha,ac: ac,init: init,speed: speed, maxHp: maxHp)

    }
    private Set<Clazz> classes(Map m) {
        def c = (m["classes"] as List<Map<String,?>>)
        return c.stream().map({ p -> new Clazz(name: p["name"], level: (p["level"] as Double).intValue()) }).collect(Collectors.toSet())
    }
    private Set<String> makeList(String s, Map m) {
        return m[s] as Set<String>
    }

}
