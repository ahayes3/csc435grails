package grails2

class ClazzIR {
    String name
    Integer level
    ClazzIR(Clazz c) {
        this.name=c.name
        this.level=c.level
    }
}
