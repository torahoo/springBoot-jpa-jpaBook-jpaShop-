package jpbook.jpashop.repository;

import jpbook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save (Item item) {
        if (item.getId() == null) {
            em.persist(item); // 새로 생성한 객체는 id값이 없기에 신규 등록
        } else {
            em.merge(item); // 이미 DB에 등록된 것을 변경
        }
    }

    public Item findOne (Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll () {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
