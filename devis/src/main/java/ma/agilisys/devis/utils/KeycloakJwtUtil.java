package ma.agilisys.devis.utils;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KeycloakJwtUtil {

    private final RestTemplate restTemplate;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public KeycloakJwtUtil() {
        this.restTemplate = new RestTemplate();
    }

    public AccessTokenResponse getToken(String username, String password) {
        try {
            System.out.println("Appel à Keycloak pour obtenir un token pour l'utilisateur: " + username);
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(username)
                    .password(password)
                    .build();

            AccessTokenResponse response = keycloak.tokenManager().getAccessToken();
            System.out.println("Token reçu de Keycloak pour l'utilisateur: " + username);
            return response;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'obtention du token pour l'utilisateur " + username + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Refresh an access token using a refresh token
     *
     * @param refreshToken the refresh token
     * @return AccessTokenResponse with new tokens
     */
    public AccessTokenResponse refreshToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, realm);

        try {
            System.out.println("Attempting to refresh token at URL: " + tokenUrl);

            ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(
                    tokenUrl,
                    request,
                    AccessTokenResponse.class
            );

            System.out.println("Token refresh successful, status: " + response.getStatusCode());

            return response.getBody();
        } catch (Exception e) {
            System.err.println("Token refresh failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to refresh token: " + e.getMessage(), e);
        }
    }
}