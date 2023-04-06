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