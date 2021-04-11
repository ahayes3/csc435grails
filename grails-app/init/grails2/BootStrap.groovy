package grails2

class BootStrap {

    def init = { servletContext ->
        def cl = [new Clazz(name: "Fighter",level: 4)] as Set
        def a = new Characterx(clazzes: cl,skillProfs: [],toolProfs: [],items: [],features: [], languages: (["Common","Elvish"] as Set),
                name: "Jeff",background: "Thief", race: "Elf", str: 10,dex: 11,con: 12,intel: 13,wis: 14,cha: 15,ac: 14,init: 12,speed: 30, maxHp: 45)
        a.save(true)
    }
    def destroy = {
    }
}
