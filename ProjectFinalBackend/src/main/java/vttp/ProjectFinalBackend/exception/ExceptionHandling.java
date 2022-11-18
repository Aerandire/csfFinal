package vttp.ProjectFinalBackend.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;

import vttp.ProjectFinalBackend.model.HttpReponse;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController{
    private final Logger LOGGER  = LoggerFactory.getLogger(getClass());
    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this end point";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled, please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    private static final String ERROR_PATH = "/error";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpReponse> accountDisabledException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpReponse> badCredentialsException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpReponse> accessDeniedException() {
        return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpReponse> lockedException() {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpReponse> tokenExpiredException(TokenExpiredException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpReponse> emailExistException(EmailExistException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpReponse> usernameExistException(UsernameExistException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpReponse> emailNotFoundException(EmailNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpReponse> userNotFoundException(UserNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpReponse> hmethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpReponse> internalServerErrorexception(Exception exception) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpReponse> ioException(IOException exception) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    private ResponseEntity<HttpReponse> createHttpResponse(HttpStatus httpStatus, String message){
        return new ResponseEntity<>(new HttpReponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()), httpStatus);
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpReponse> notFound404() {
        return createHttpResponse(HttpStatus.NOT_FOUND, "There is no mapping for this URL");
    }


}
