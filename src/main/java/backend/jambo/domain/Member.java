package backend.jambo.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Entity
public class Member {

    @Id
    private long id;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;
    private String refreshToken;

    @CreatedDate
    private LocalDateTime joinDate;

    private Member(long id, String nickname, Status status) {
        this.id = id;
        this.nickname = nickname;
        this.status = status;
    }

    public static Member of(long id, String nickname) {
        return new Member(id, nickname, Status.REGISTER);
    }

    public void updateStatus() {
        this.status = Status.WITHDRAWN;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
