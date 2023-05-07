package backend.jambo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
}
