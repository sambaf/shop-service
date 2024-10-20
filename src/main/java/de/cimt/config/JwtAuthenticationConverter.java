package de.cimt.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    Collection<GrantedAuthority> authorities =
        Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
            extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
    return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_username"));
  }

  @SuppressWarnings("unchecked")
  private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
    if (jwt.getClaim("realm_access") == null) {
      return Set.of();
    }
    Map<String, Object> realmAccess = jwt.getClaim("realm_access");
    Collection<String> roles = (Collection<String>) realmAccess.get("roles");
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

  }
}
