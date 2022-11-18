package vttp.ProjectFinalBackend.filter;

import java.io.OutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.ProjectFinalBackend.model.HttpReponse;
import vttp.ProjectFinalBackend.util.SecurityConstant;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{
    
    @Override
    public void handle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws java.io.IOException, javax.servlet.ServletException{
        HttpReponse httpResponse = new HttpReponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(), SecurityConstant.ACCESS_DENIED_MESSAGE);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
    
}
