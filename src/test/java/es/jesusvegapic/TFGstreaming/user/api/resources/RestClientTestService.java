package es.jesusvegapic.TFGstreaming.user.api.resources;

import es.jesusvegapic.TFGstreaming.user.api.dtos.TokenDto;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.domain.services.JwtService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class RestClientTestService {

    @Autowired
    private JwtService jwtService;

    private String token;

    private boolean isRole(Role role) {
        return this.token != null && jwtService.role(token).equals(role.name());
    }

    private WebTestClient login(Role role, String email, WebTestClient webTestClient) {
        if(!this.isRole(role)) {
            return login(email, webTestClient);
        } else {
            return webTestClient.mutate()
                    .defaultHeader("Authorization", "Bearer " + this.token).build();
        }
    }

    public WebTestClient login(String email, WebTestClient webTestClient) {
        TokenDto tokenDto = webTestClient
                .mutate().filter(basicAuthentication(email, "7")).build()
                .post().uri(UserResource.USERS + UserResource.TOKEN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .value(Assertions::assertNotNull)
                .returnResult().getResponseBody();
        if(tokenDto != null) {
            this.token = tokenDto.getToken();
        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.token).build();
    }

    public WebTestClient loginAdmin(WebTestClient webTestClient) {
        return this.login(Role.ADMIN, "adm@gmail.com", webTestClient);
    }

    public WebTestClient loginMod(WebTestClient webTestClient) {
        return this.login(Role.MOD, "mod@gmail.com", webTestClient);
    }

    public WebTestClient loginClient(WebTestClient webTestClient) {
        return this.login(Role.CLIENT, "c1@gmail.com", webTestClient);
    }

    public String getToken() {
        return token;
    }

}
