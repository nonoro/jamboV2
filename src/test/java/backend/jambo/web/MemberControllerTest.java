package backend.jambo.web;

import backend.jambo.domain.Status;
import backend.jambo.domain.request.MemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("멤버 인수 테스트")
class MemberControllerTest extends AcceptanceTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveMember() {
        MemberRequest request = new MemberRequest(20230501, "김영철");

        var response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/member")
                .then().log().all()
                .extract();

        assertAll(() -> {
            assertThat(response.jsonPath().getLong("id")).isEqualTo(20230501);
            assertThat(response.jsonPath().getString("nickname")).isEqualTo("김영철");
        });
    }

    @DisplayName("요청 id에 해당하는 회원 한명을 조회한다.")
    @Test
    void  findMember() {
        MemberRequest request = new MemberRequest(20230501, "김영철");

        var response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/member")
                .then().log().all()
                .extract();

        var findMemberResponse = given().log().all()
                .pathParam("id", 20230501)
                .when().get("/member/{id}")
                .then().log().all()
                .extract();

        assertThat(findMemberResponse.jsonPath().getLong("id")).isEqualTo(response.jsonPath().getLong("id"));
    }

    @DisplayName("전체 회원을 조회한다.")
    @Test
    void findMembers() {
        List<MemberRequest> members = List.of(
                new MemberRequest(1111111, "김영철"),
                new MemberRequest(1111112, "최동우"),
                new MemberRequest(1111113, "성준혁"),
                new MemberRequest(1111114, "권규정"),
                new MemberRequest(1111115, "노영록"),
                new MemberRequest(1111116, "이예찬")
        );

        for (MemberRequest member : members) {
            given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(member)
                    .when().post("/member")
                    .then().log().all()
                    .extract();
        }

        ExtractableResponse<Response> findMembers = given().log().all()
                .when().get("/members")
                .then().log().all()
                .extract();

        assertThat(members.size()).isEqualTo(findMembers.jsonPath().getList("id").size());
    }

    @DisplayName("회원 탈퇴시 회원의 상태를 탈퇴로 변경한다.")
    @Test
    void deleteMember() {
        List<MemberRequest> members = List.of(
                new MemberRequest(1111111, "김영철"),
                new MemberRequest(1111112, "최동우"),
                new MemberRequest(1111113, "성준혁"),
                new MemberRequest(1111114, "권규정"),
                new MemberRequest(1111115, "노영록"),
                new MemberRequest(1111116, "이예찬")
        );

        for (MemberRequest member : members) {
            given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(member)
                    .when().post("/member")
                    .then().log().all()
                    .extract();
        }

        long memberId = 1111111;

        ExtractableResponse<Response> deletedMember = given().log().all()
                .when().delete("/member/" + memberId)
                .then().log().all()
                .extract();

        assertThat(deletedMember.jsonPath().getString("status")).isEqualTo(String.valueOf(Status.WITHDRAWN));
    }
}
