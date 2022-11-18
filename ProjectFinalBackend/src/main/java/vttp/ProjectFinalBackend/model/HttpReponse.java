package vttp.ProjectFinalBackend.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpReponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Singapore")
    private Date timestamp;
    private int httpStatusCode;
    private HttpStatus httpstatus;
    private String reason;
    private String message;

    public HttpReponse() {}

    public HttpReponse(int httpStatusCode, HttpStatus httpstatus, String reason, String message) {
        this.timestamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpstatus = httpstatus;
        this.reason = reason;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpstatus() {
        return httpstatus;
    }

    public void setHttpstatus(HttpStatus httpstatus) {
        this.httpstatus = httpstatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
   
}
