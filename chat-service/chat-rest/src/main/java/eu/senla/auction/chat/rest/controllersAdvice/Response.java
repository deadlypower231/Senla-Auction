package eu.senla.auction.chat.rest.controllersAdvice;

public class Response {

    private String message;

    public Response(){

    }

    public Response(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}