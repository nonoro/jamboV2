package backend.jambo.domain;

public enum Status {
    REGISTER("가입"),
    WITHDRAWN("탈퇴");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
