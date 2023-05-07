package backend.jambo.domain.response;

import backend.jambo.domain.Member;
import backend.jambo.domain.Status;

public record MemberResponse(
        long id,
        String nickname,
        Status status
) {

    public static MemberResponse of(Member entity) {
        return new MemberResponse(entity.getId(), entity.getNickname(), entity.getStatus());
    }
}
