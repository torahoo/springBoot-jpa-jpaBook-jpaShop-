package jpbook.jpashop.repository;

import jpbook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em; // persistance 어노테이션이 있어야지 Entity가 작동을 하지만 스프링 부트는 자동 지원

    public void save (Member member) {
        em.persist(member);
    }

    public Member findOne (Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll () {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); //JPQL 사용
    } // JPA 기본변 참고

    public List<Member> findByName (String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList(); //JPQL 사용
    }
}
