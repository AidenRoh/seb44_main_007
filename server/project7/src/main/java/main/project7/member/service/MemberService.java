package main.project7.member.service;

import main.project7.exception.BusinessLogicException;
import main.project7.exception.ExceptionCode;
import main.project7.member.entity.Member;
import main.project7.member.repository.MemberRepository;
import main.project7.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CustomBeanUtils customBeanUtils;


    public MemberService(MemberRepository memberRepository, CustomBeanUtils customBeanUtils) {
        this.memberRepository = memberRepository;
        this.customBeanUtils = customBeanUtils;
    }

    public Member createMember(Member member) {
        // check mail
        Member findMember = memberRepository.findByEmail(member.getEmail());
        Member.checkExistEmail(findMember);
        // Encoding Password

        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findMember(member.getMemberId());
        Member updateMember = findMember.changeMemberInfo(member, customBeanUtils);

        return memberRepository.save(updateMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    @Transactional(readOnly = true)
    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        Member findMember = findMember(memberId);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_WITHDRAW);
    }
}
