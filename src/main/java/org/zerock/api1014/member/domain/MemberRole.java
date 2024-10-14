package org.zerock.api1014.member.domain;

public enum MemberRole {

    USER("USER"),ADMIN("ADMIN");

    String role;

    //enum은 public 안됨
    MemberRole(String role) {
        this.role = role;
    }

}
