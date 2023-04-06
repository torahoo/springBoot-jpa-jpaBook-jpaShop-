package jpbook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) { //커맨드와 쿼리를 분리해라
        em.persist(member);
        return member.getId();
    }

    public Member find (Long id) {
        return em.find(Member.class, id);
    }
}
