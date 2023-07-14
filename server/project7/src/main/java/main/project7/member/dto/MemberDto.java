package main.project7.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import main.project7.member.entity.Member;
import org.springframework.util.Assert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        @NotBlank
        private String phone;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private long memberId;

        private String name;

        private String password;

        private String phone;

        public Patch addMemberId(Long memberId) {
            Assert.notNull(memberId, "Member Id must not be Null");
            this.memberId = memberId;
            return this;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String email;
        private String name;
        private String phone;
        private String password;
    }

    public Response response(Member member) {
        return Response.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .password(member.getPassword())
                .build();
    }

}
