package com.server.member.mapper;

import com.server.member.dto.MemberDto;
import com.server.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-17T15:43:53+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.18 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostDtoToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.email( requestBody.getEmail() );
        member.name( requestBody.getName() );
        member.password( requestBody.getPassword() );
        member.phone( requestBody.getPhone() );

        return member.build();
    }

    @Override
    public Member memberPatchDtoToMember(MemberDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.memberId( requestBody.getMemberId() );
        member.name( requestBody.getName() );
        member.password( requestBody.getPassword() );
        member.phone( requestBody.getPhone() );

        return member.build();
    }

    @Override
    public MemberDto.Response memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response.ResponseBuilder response = MemberDto.Response.builder();

        response.memberId( member.getMemberId() );
        response.email( member.getEmail() );
        response.name( member.getName() );
        response.phone( member.getPhone() );

        return response.build();
    }

    @Override
    public List<MemberDto.Response> membersToMemberResponseDtos(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.Response> list = new ArrayList<MemberDto.Response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToMemberResponseDto( member ) );
        }

        return list;
    }
}
