package backend.jambo.application;

import backend.jambo.config.JwtConfig;
import backend.jambo.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class JwtService {

    private final MemberRepository memberRepository;
    private final JwtConfig jwtConfig;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer";

    /*
    * AccessToken 생성 메서드
    * 따로 넣을 커스텀 정보(claim)가 없으므로 withClaim 생략
    * */
    public String createAccessToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + jwtConfig.getAccess().getExpiration()))
                .sign(Algorithm.HMAC512(jwtConfig.getSecretKey()));
    }

    /*
     * RefreshToken 생성
     * 따로 넣을 커스텀 정보(claim)가 없으므로 withClaim 생략
     * */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + jwtConfig.getRefresh().getExpiration()))
                .sign(Algorithm.HMAC512(jwtConfig.getSecretKey()));
    }

    /*
    * AccessToken 헤더에 넣어 보내기
    * */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(jwtConfig.getAccess().getHeader(), accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    /*
    * AccessToken과 RefreshToken 헤더에 넣어 보내기
    * */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(jwtConfig.getAccess().getHeader(), accessToken);
        response.setHeader(jwtConfig.getRefresh().getHeader(), refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }


    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식: Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"라는 type을 삭제하고 나머지 credentials을 저장
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(jwtConfig.getRefresh().getHeader()))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식: Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"라는 type을 삭제하고 나머지 credentials을 저장
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(jwtConfig.getAccess().getHeader()))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 처음 회원가입시엔 RefreshToken이 존재하지 않음 따라서 두번째 로그인할때 RefreshToken을 생성하여 저장
     * RefreshToken DB 저장
     */
    public void updateRefreshToken(Long id, String refreshToken) {
        memberRepository.findById(id)
                .ifPresentOrElse(
                        member -> member.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtConfig.getSecretKey())).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

}
