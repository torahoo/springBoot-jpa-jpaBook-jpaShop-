package jpbook.jpashop.service;

import jpbook.jpashop.domain.Member;
import jpbook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 걸린 필드만 생성자로 엮어 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    } // 최근 스프링은 @AutoWired 어노테이션이 없어도 자동으로 인젠션 해줌

    /**
     * 회원 가입
     */
    @Transactional
    public Long join (Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(Member member) {
        //exeption
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers () {
        return memberRepository.findAll();
    }

    /**
     * 회원 하나 조회
     */
    public Member findOne (Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
