package backend.jambo.domain.response;

import backend.jambo.domain.Member;

public record MemberResponse(
        long id,
        String nickname
) {

    public static MemberResponse of(Member entity) {
        return new MemberResponse(entity.getId(), entity.getNickname());
    }
}
