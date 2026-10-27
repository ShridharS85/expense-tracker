package com.expense.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@ConditionalOnExpression(
        "'${app.oauth2.google.client-id:}' != '' and '${app.oauth2.google.client-secret:}' != ''")
public class GoogleOAuth2Config {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("${app.oauth2.google.client-id}") String clientId,
            @Value("${app.oauth2.google.client-secret}") String clientSecret) {
        ClientRegistration registration = CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope("email", "profile")
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }
}
