package com.server.member.service;


import com.server.advice.BusinessLogicException;
import com.server.advice.ExceptionCode;
import com.server.auth.utils.CustomAuthorityUtils;
import com.server.member.entity.Member;
import com.server.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public Member createMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        Member.isExistEmail(findMember);                              // 동일한 이메일이 있는지 확인
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);                         // 비밀번호 암호화 하기
        member.setRoles(authorityUtils.createRoles(member.getEmail()));
        return memberRepository.save(member);
    }


    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> {
                    String encryptedPassword = passwordEncoder.encode(password);
                    findMember.setPassword(encryptedPassword);
                });
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getAddress())
                .ifPresent(address -> findMember.setAddress(address));
        Optional.ofNullable(member.getImageURL())
                .ifPresent(ImageURL -> findMember.setImageURL(ImageURL));
        Optional.ofNullable(member.getPremium())
                .ifPresent(premium -> findMember.setPremium(premium));

        return memberRepository.save(findMember);
    }

//    public Member updateMemberPassword(long memberId, String currentPwd, String newPwd) {
//        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        if(!passwordEncoder.matches(currentPwd, findMember.getPassword()))
//            throw new BusinessLogicException(ExceptionCode.PASSWORD_INCORRECT);
//
//        findMember.setPassword(passwordEncoder.encode(newPwd));
//        return memberRepository.save(findMember);
//    }


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

    public void deleteMember(Long memberId) {
        Member findMember = findMember(memberId);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_WITHDRAW);
    }


    public boolean isExistMember(String email){
        return memberRepository.findByEmail(email) == null;
    }


    //db에 저장되어 있는 맴버가 있는지 없는지 판별하는 기능
    private Member findVerifiedMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        return optionalMember.get();
    }



}
