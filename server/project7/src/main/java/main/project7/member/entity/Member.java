package main.project7.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.project7.exception.BusinessLogicException;
import main.project7.exception.ExceptionCode;
import main.project7.utils.CustomBeanUtils;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String phone;
    @Column
    private String imageURL;
    @Column
    private boolean premium;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {

        MEMBER_ACTIVE("활동중"),

        MEMBER_SLEEP("휴먼 계정"),

        MEMBER_WITHDRAW("계정 탈퇴");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    public Member(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    //Util
    public static void checkExistEmail(Member targetMember) {
        if(targetMember != null) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public Member changeMemberInfo(Member sourceMember, CustomBeanUtils<Member> beanUtils) {
        return beanUtils.copyNonNullProperties(sourceMember, this);
    }


}
