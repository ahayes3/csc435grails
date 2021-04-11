package grails2

class CodeException extends Exception{
    Integer code
    CodeException(Integer code,String message) {
        super(message)
        this.code=code
    }
}
