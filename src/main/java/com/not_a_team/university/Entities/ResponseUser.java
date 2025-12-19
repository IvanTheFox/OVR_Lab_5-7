package com.not_a_team.university.Entities;

public class ResponseUser {
    public Long id;
    public String name;
    public int permLevel;
    public String avatar;
    public int loginCount;

    public ResponseUser() {}
    public ResponseUser(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.permLevel = user.getRole().getPermLevel();
        this.avatar = user.getAvatar();
        this.loginCount = user.getLoginCount();
    }
}
