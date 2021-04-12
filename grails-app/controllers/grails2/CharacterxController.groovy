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
-        CharacterView.render(r,myChars)
    }

    def getChar() {
        checkLogin()
        String id = params.id
        def cr = Characterx.findById(Integer.parseInt(id))
        CharacterView.render(r,cr)
    }
    def delete() {
        checkLogin()
        String i = params.id
        if(!i.isInteger()) {
            throw new CodeException(400,"Bad Request")
        }
        Integer id = Integer.parseInt(i)
        def cr = Characterx.get(id)
        if(cr.user != session["user"]) {
            throw new CodeException(403,"Forbidden")
        }
        cr.delete(flush:true)
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
        //def cr = parseChar(request)

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
        def languages  = makeList("languages",m).stream().map({p -> new Language(name: p)})
        def features = makeList("features",m).stream().map({p -> new Feature(name: p)})
        def tools = makeList("tools",m).stream().map({p -> new Tool(name: p)})
        def skills = makeList("skills",m).stream().map({p -> new Skill(name: p)})
        def items = makeList("items",m).stream().map({p -> new Item(name: p)})
        def classes = classes(m)

        def z = new Characterx(name: name,background: bg, race: race, str: str,dex: dex,con: con,intel: intel,wis: wis,cha: cha,ac: ac,init: init,speed: speed, maxHp: maxHp)



        z.user = session["user"]
        def u = User.findByName(session["user"] as String)
        u.characters.add(z)
        z.save(failOnError: true)

        tools.forEach({ p -> z.addToToolProfs(p)})
        languages.forEach({ p -> z.addToLanguages(p) })
        features.forEach({ p -> z.addToFeatures(p) })
        skills.forEach({ p -> z.addToSkillProfs(p) })
        items.forEach({ p -> z.addToItems(p) })
        classes.forEach({p -> z.addToClazzes(p)})
        z.save()
        z.clazzes.forEach({p -> p.save()})
        z.toolProfs.forEach({p -> p.save()})
        z.languages.forEach({p -> p.save()})
        z.features.forEach({p -> p.save()})
        z.skillProfs.forEach({p -> p.save()})
        z.items.forEach({p -> p.save()})
        z.save(flush:true)

        CharacterView.render(r,z)
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

        def z = new Characterx(clazzes: classes,skillProfs: skills,toolProfs: tools,items: items,features: features, languages: languages,
                name: name,background: bg, race: race, str: str,dex: dex,con: con,intel: intel,wis: wis,cha: cha,ac: ac,init: init,speed: speed, maxHp: maxHp)
        tools.forEach({ p -> z.addToToolProfs(p) })
        return z

    }
    private Set<Clazz> classes(Map m) {
        def c = (m["classes"] as List<Map<String,?>>)
        return c.stream().map({ p -> new Clazz(name: p["name"], level: (p["level"] as Double).intValue()) }).collect(Collectors.toSet())
    }
    private Set<String> makeList(String s, Map m) {
        return m[s] as Set<String>
    }

}
