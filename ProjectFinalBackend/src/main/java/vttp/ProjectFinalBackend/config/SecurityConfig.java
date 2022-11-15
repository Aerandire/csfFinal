package vttp.ProjectFinalBackend.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.core.convert.converter.Converter;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    interface Jwt2AuthoritiesConverter extends Converter<Jwt,Collection<? extends GrantedAuthority>> {}

    @SuppressWarnings("unchecked")
    @Bean
    Jwt2AuthoritiesConverter authoritiesConverter(){
        return jwt -> {
            final var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
            final var realmRoles = (Collection<String>) realmAccess.getOrDefault("roles", List.of());

            final var resourceAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("resource_access", Map.of());
            // We assume here you have "spring-addons-confidential" and
            // "spring-addons-public" clients configured with "client roles" mapper in
            // Keycloak
            final var confidentialClientAccess = (Map<String, Object>) resourceAccess
                    .getOrDefault("spring-addons-confidential", Map.of());
            final var confidentialClientRoles = (Collection<String>) confidentialClientAccess.getOrDefault("roles",
                    List.of());
            final var publicClientAccess = (Map<String, Object>) resourceAccess.getOrDefault("spring-addons-public",
                    Map.of());
            final var publicClientRoles = (Collection<String>) publicClientAccess.getOrDefault("roles", List.of());

            return Stream
                    .concat(realmRoles.stream(),
                            Stream.concat(confidentialClientRoles.stream(), publicClientRoles.stream()))
                    .map(SimpleGrantedAuthority::new).toList();
        };
    }

    interface Jwt2AuthenticationConverter extends Converter<Jwt, AbstractAuthenticationToken> {
        }

    

    
}
