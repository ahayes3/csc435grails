package grails2

import java.util.stream.Collectors

//need plain objects for gson to work
class CharacterIR {
    ArrayList<Clazz> clazzes
    ArrayList<String> skillProfs,toolProfs,items,features,languages
    String name,background,race,user
    Integer str,dex,con,intel,wis,cha,ac,init,speed,maxHp
    CharacterIR(Characterx c) {
        this.clazzes = c.clazzes.stream().map({ p -> new ClazzIR(p) }).collect(Collectors.toSet())
        this.skillProfs = c.skillProfs.stream().map({p -> p.name}).collect(Collectors.toSet())
        this.toolProfs=c.toolProfs.stream().map({p -> p.name}).collect(Collectors.toSet())
        this.items=c.items.stream().map({p -> p.name}).collect(Collectors.toSet())
        this.features=c.features.stream().map({p -> p.name}).collect(Collectors.toSet())
        this.languages = c.languages.stream().map({p -> p.name}).collect(Collectors.toSet())
        this.name=c.name
        this.background=c.background
        this.race=c.race
        this.user=c.user
        this.str=c.str
        this.dex=c.dex
        this.con=c.con
        this.intel=c.intel
        this.wis=c.wis
        this.cha=c.cha
        this.ac=c.ac
        this.init=c.init
        this.speed=c.speed
        this.maxHp=c.maxHp
    }
}
