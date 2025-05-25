package ma.agilisys.devis.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.dtos.LoginRequest;
import ma.agilisys.devis.dtos.RefreshTokenRequest;
import ma.agilisys.devis.utils.KeycloakJwtUtil;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final KeycloakJwtUtil keycloakJwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Tentative de login pour l'utilisateur: {}", loginRequest.getUsername());
        try {
            AccessTokenResponse tokenResponse = keycloakJwtUtil.getToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            log.info("Login réussi pour l'utilisateur: {}", loginRequest.getUsername());
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            log.error("Erreur lors du login pour l'utilisateur {}: {}", loginRequest.getUsername(), e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            log.info("Refresh token request received for token: {}", refreshTokenRequest.getRefreshToken() != null ?
                    "[token present]" : "[token missing]");

            AccessTokenResponse tokenResponse = keycloakJwtUtil.refreshToken(
                    refreshTokenRequest.getRefreshToken()
            );
            log.info("Token refresh successful");
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            log.error("Token refresh failed in controller: {}", e.getMessage(), e);
            e.printStackTrace();
            // Return appropriate error response
            return ResponseEntity.badRequest().build();
        }
    }
}