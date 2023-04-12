인프런 강의를 들으며 만든 프로젝트 입니다.
2023/03/29 작성

===2023-04-06===
도메인 분석 설계
 - 요구사항 분석
 - 도메인 모델과 테입르 설계
 - 엔티티 클래스 개발
 - 엔티티 설계시 주의점

HELLO SHOP
 - 회원 기능 : 회원 등록, 회원 조회
   - 회원 가입
     - 이름
     - 도시
     - 거리
     - 우편번호
   - 회원 목록
     - 등록한 회원 목록
 
 - 상품 기능 : 상품 등록, 상품 수정, 상품 조회
   - 상품 등록
     - 상품 명
     - 가격
     - 수량
     - 저자
     - ISBN
   - 상품 목록
     - 등록한 상품 목록
 
 - 주문 기능 : 상품 주문, 주문 내역 조회, 주문 취소
   - 상품 주문
     - 주문 회원
     - 상품 명
     - 주문 수량
   - 주문 내역
     - 회원의 주문 내역
     - 간단한 검색 기능
       - 회원의 주문 목록
       - 취소 상품
       - etc...
 
 - 기타 요구사항
   - 상품은 재고 관리 필요
   - 상품의 종류는 도서, 음반, 영화
   - 상품을 카테고리로 구분 할 수 있음
   - 상품 주문시 배송 정보를 입력할 수 있음


===2023-04-06===
도메인 모델과 테이블 설계
 - 회원 엔티티 분석
   - Member
     - id           : Long
     - name         : String
     - address      : Address
     - orders       : List

   - Order 
     - id
     - member       : Member
     - orderItems   : List
     - delivery     : Delivery

   - Delivery
     - id
     - order        : Order
     - address      : Address
     - status       : DeliveryStatus

   - OrderItem
     - id
     - item         : Item
     - order        : Order
     - orderPrice
     - count

   - Item
     - id
     - name
     - price        : int
     - stockQuanity
     - categories   : List

   - Category
     - id
     - name
     - items        : List
     - parent       : Category
     - child        : List

   - Address <<Value Type>>
     - city
     - street
     - zipcode

   - Album
     - artist
     - etc

   - Book
     - author
     - isbn

   - Movie
     - director
     - actor

[참고 : 회원이 주문을 하기 때문에, 
회원이 주문리스트를 가지는 것은 얼핏 보면 잘 설계한 것 같지만, 
객체 세상은 실제 세계와는 다르다.
실무에서는 회원이 주문을 참조하지 않고,
주문이 회원을 참조하는 것으로 충분하다.
여기서는 일대다, 다대일의 양방향 연관관계를 설명하기 위해서 추가했다.]

회원 테이블 분석
 - MEMBER
   - member_id     (pk)
   - name
   - city
   - street
   - zipcode
 
 - ORDERS
   - order_id      (pk)
   - member_id     (fk)
   - delivery_id   (fk)
   - orderdate
   - status

 - ORDER_ITEM
   - order_item_id (pk)
   - order_id      (fk)
   - item_id       (fk)
   - orderprice
   - count

 - DELIVERY
   - delivery_id   (pk)
   - status
   - city
   - street
   - zipcode

 - CATEGORY
   - category_id   (pk)
   - parent_id     (fk)
   - name

 - CATEGOTY_ITEM
   - category_id   (fk)
   - item_id       (fk)

 - ITEM
   - item_id       (pk)
   - name
   - price
   - stockquantity
   - dtype
   - artist
   - etc
   - author
   - isbn
   - director
   - actor

[참고 : 외래 키가 있는 곳을 연관관계의 주인으로 정해라.
연관관계의 주인은 단순히 외래 키를 누가 관리하냐의 문제이지 
비지니스상 우위에 있다고 주인으로 정하면 안된다.
예를 들어서 자동차와 바퀴가 있으면, 일대다 관계에서 항상 
다쪽에 외래 키가 있으므로 외래 키가 있는 바퀴를 연관관계의 
주인으로 정하면 된다. 물론 자동차를 연관관계의 주인으로 
정하는 것이 불가능 한 것은 아니지만, 자동차를 연관관계의 
주인으로 정하면 자동차가 관리하지 않는 바퀴 테이블의 
외래 키 값이 업데이트 되므로 관리와 유지수가 어렵고, 
추가적으로 별도의 업데이트 쿼리가 발생하는 성능 문제도 있다.
자세한 내용은 JPA 기본편을 참고]



===2023-04-12===
엔티티 클래스 개발
[참고 : 이론적으로 Getter, Setter 모두 제공하지 않고, 
꼭 필요한 별도의 메서드를 제공하는게 가장 이상적이다. 
하지만 실무에서 엔티티의 데이터는 조회할 일이 너무 많으므로,
Getter의 경우 모두 열어두는 것이 편리하다.
Getter는 아무리 호출해도 호출 하는 것 만으로 어떤 일이 발생하지는 않는다.
하지만 Setter는 문제가 다르다. Setter를 호출하면 
데이터가 변한다. Setter를 막 열어두면 가까운 미래에 엔티티가 
도대체 왜 변경되는지 추적하기 점점 힘들어진다. 그래서 엔티티를 
변경할 때는 Setter대신에 변경 지점이 명확하도록 변경을 위한 
비즈니스 메서드를 별도로 제공해야 한다.]

[참고 : 엔티티의 식별자는 id를 사용하고 pk컬럼명은 member_id를 사용했다.
엔티티는 타입(여기서는 Member)이 있으므로 id필드만으로 쉽게 
구분할 수 있다. 테이블은 타입이 없으므로 구분이 어렵다. 그리고 테이블은 
관례상 {테이블명+id}를 많이 사용한다. 참고로 객체에서 id대신에 
memberId를 사용해도 된다. 중요한 것은 일관성 이다.]

[참고 : 실무에서는 @ManyToMany를 사용하지 말자.
@ManyToMany는 편리한 것 같지만, 중간테이블(Category_item)에 컬럼을 
추가할 수 없고, 세밀하게 쿼리를 실행하기 어렵기 때문에 
실무에서 사용하기에는 한꼐가 있다. 중간 엔티티(CategoryItem)를 만들고 
@ManyToOne, @OneToMany로 매핑해서 사용하자. 정리하면 다대다 매핑을 
일대다, 다대일 매핑으로 풀어내서 사용하자.]

[참고 : 값 타입은 변경 불가능하게 설계해야 한다.(ex. Address 클래스)
@Setter를 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 
클래스를 만들자. JPA스펙상 엔티티나 임베디드 타입(@Embeddable)은 
자바 기본 생성자(default constructor)를 public 또는 protected로 
설정해야 한다. public으로 두는 것 보다는 protected로 설정하는 것이 
그나마 더 안전하다. JPA가 이런 제약을 두는 이유는 JPA 구현 
라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용할 수 있도록 
지원해야 하기 때문이다.]