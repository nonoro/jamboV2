package backend.jambo.domain;

import lombok.Getter;

@Getter
public class SessionUser {

    private Long id;
    private String nickname;

    public SessionUser(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
