package vttp.ProjectFinalBackend.filter;

import java.io.OutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.ProjectFinalBackend.model.HttpReponse;
import vttp.ProjectFinalBackend.util.SecurityConstant;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint{
    
    @Override
    public void commence(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, org.springframework.security.core.AuthenticationException arg2) throws java.io.IOException {
        HttpReponse httpResponse = new HttpReponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), SecurityConstant.FORBIDDEN_MESSAGE);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }


}
