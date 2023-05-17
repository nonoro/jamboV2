/*
package backend.jambo.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("jwt config 테스트")
@SpringBootTest
class JwtConfigTest {

    @Autowired
    private JwtConfig jwtConfig;

    @DisplayName("application-jwt.yml 파일에서 property를 가져온다")
    @Test
    void getJwtConfig() {
        String secretKey = "afakmnbnrioagenffewr!$!@$!!~~#noesingsaklgamslfksafmasfnafoiewnfalkfsdanklasghoasfjaoiwen";
        long accessExpiration = 3600000;
        long refreshExpiration = 1209600000;
        String accessHeader = "Authorization";
        String refreshHeader = "Authorization-refresh";

        assertAll(() -> {
            assertThat(secretKey).isEqualTo(jwtConfig.getSecretKey());
            assertThat(accessExpiration).isEqualTo(jwtConfig.getAccess().getExpiration());
            assertThat(refreshExpiration).isEqualTo(jwtConfig.getRefresh().getExpiration());
            assertThat(accessHeader).isEqualTo(jwtConfig.getAccess().getHeader());
            assertThat(refreshHeader).isEqualTo(jwtConfig.getRefresh().getHeader());
        });
    }
}
*/
