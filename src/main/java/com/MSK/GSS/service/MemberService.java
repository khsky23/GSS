package com.MSK.GSS.service;


import com.MSK.GSS.config.Role;
import com.MSK.GSS.dao.MemberRepository;
import com.MSK.GSS.domain.Member;
import com.MSK.GSS.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username).get();
    }

    /**
     * 회원 중복 체크
     *
     * @param loginId
     * @param nickname
     * @param email
     */
    public void isDuplicateMember(String loginId, String nickname, String email) {

        if (memberRepository.existsByLoginId(loginId)) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다");
        } else if (memberRepository.existsByNickname(nickname)) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다");
        } else if (memberRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
     * 회원가입
     *
     * @param memberSaveForm
     */

    @Transactional
    public void save(MemberSaveForm memberSaveForm) throws IllegalStateException {

        isDuplicateMember(
                memberSaveForm.getLoginId(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail()
        );

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail(),
                Role.MEMBER
        );

        memberRepository.save(member);
    }

    public Member findByLoginId(String loginId) throws IllegalStateException {

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다")
        );

        return memberOptional.get();
    }
}



