package es.jesusvegapic.TFGstreaming.user.data.model;

public enum Role {
    ADMIN, MOD, CLIENT;

    public static final String PREFIX = "ROLE_";

    public static Role of(String withPrefix) {
        return Role.valueOf(withPrefix.replace(Role.PREFIX, ""));
    }

    public String withPrefix() {
        return  PREFIX + this.toString();
    }
}
