package com.not_a_team.university.Enums;

public enum Role {
    User(0),
    Moderator(1),
    Admin(2);

    int permLevel;
    private Role(int permLevel) {
        this.permLevel = permLevel;
    }

    public int getPermLevel() {
        return this.permLevel;
    }

    public static Role getRoleFromPermLevel(int level) {
        for (Role role : Role.values()) {
            if (role.permLevel == level)
                return role;
        }
        return Role.User;
    }
}
