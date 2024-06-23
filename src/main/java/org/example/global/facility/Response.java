package org.example.global.facility;

import java.io.Serializable;

public class Response implements Serializable {
    private String message;
    private Object object;
    public Response(String message) {this.message = message;}

    public String getMessage() {
        return message;
    }
    
}
