package com.sportuniform.service;

import com.sportuniform.domain.Member;
import com.sportuniform.dto.MemberFormDto;
import com.sportuniform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(MemberFormDto dto) {
        Member member = Member.createMember(dto.getName());
        memberRepository.save(member);
    }
}
