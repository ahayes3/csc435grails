package grails2

class User {

    static hasMany = [characters:Characterx]
    static hasOne = [name:String,pass:String]
}
