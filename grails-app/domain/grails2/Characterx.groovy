package grails2

class Characterx {
    static belongsTo = User
    static hasMany = [clazzes: Clazz,skillProfs:String,toolProfs:String,items:String,features:String,languages:String]
    static hasOne = [name:String, background:String,race:String,str:Integer,dex:Integer,con:Integer,intel: Integer,wis:Integer,cha:Integer,ac:Integer,init:Integer,
                     speed:Integer,maxHp:Integer,user:String]

    def update(Characterx c) {
        this.clazzes=c.clazzes
        this.skillProfs=c.skillProfs
        this.toolProfs=c.toolProfs
        this.items=c.items
        this.features=c.features
        this.languages=c.languages
        this.name=c.name
        this.background=c.background
        this.race=c.race
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
