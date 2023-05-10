package backend.jambo.domain.request;

public record MemberRequest(
        long id,
        String nickname
) {
    public MemberRequest(String nickname) {
        this(0, nickname);
    }
}
