package backend.jambo.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    REGISTER("가입"),
    WITHDRAWN("탈퇴");

    private final String name;
}
