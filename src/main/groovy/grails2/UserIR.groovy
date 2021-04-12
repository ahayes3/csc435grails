package grails2

class UserIR {
    String user
    String pass
    UserIR(User u) {
        this.user=u.name
        this.pass=u.pass
    }
}
