package jpbook.jpashop.service;

import jpbook.jpashop.domain.Member;
import jpbook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입 () throws Exception {
        //given
        Member member = new Member();
        member.setName("testName01");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); // 트랜잭셔널로 롤백이 되어 쿼리문이 사라지기 전에 쿼리문을 찍어준다.
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외 () throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("testName01");

        Member member2 = new Member();
        member2.setName("testName01");

        //when
        memberService.join(member1);

        try {
            memberService.join(member2); //같은 이름이 들어갔을 시에 예외가 발생 해야 한다.
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외 발생 해야함"); //위의 join에서 예외가 발생하지 않으면 fail 작동
    }

}