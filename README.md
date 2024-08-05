# 고급 엔티티 연관관계 매핑


## 일대일 

### ``@PrimaryKeyJoinColumn``
User -> Address 단방향 공유 기본키 일대일 연관관계 매핑
  
### ``optional=false``
FK에 not null 제약조건이 걸린 DDL 발생
``true``로 사용 시, FK가 nullable하므로 FK 값이 없는 데이터가 존재할 수 있음.

```sql
// true인 경우
// outer join 실행
SELECT *
// car의 user_id = null인 레코드는 제외된다.
FROM Car JOIN User ON Car.user_id = User.id 
WHERE Car.id = ?;

// false인 경우
// inner join 실행
select b1_0.id, a1_0.id
from b b1_0 join a a1_0 on a1_0.id=b1_0.fk
where b1_0.id=?
```
### 공유 기본키 엔티티 연관관계
공유 기본키는 지연 로딩에 문제가 있다?

-> 두 엔티티 중 하나가 항상 다른 엔티티보다 먼저 저장되고 기본키 소스 역할을 할 수 있는 경우 공유 기본키 연관관계를 사용할 수 있다.
-> 그 외 경우는 외래키 연관관계를 사용한다.

### 외래키 조인 컬럼 활용
외래키를 포함하는 경우, 테이블의 로우가 로드될 때 FK 값도 포함하고 있다.
따라서, 하이버네이트는 참조 테이블의 데이터가 있는지 알고 있고, 프록시를 통하여 필요에 따라 인스턴스를 지연 로딩할 수 있다.
FK 값이 고유해야 할 경우 ``unique=true`` 설정으로 고유한 값을 가질 수 있다.

-> 일대일 연관관계가 선택 사항인 경우, 링크 테이블을 사용한다.(null 허용 컬럼 문제)

### 조인 테이블 활용
참조 테이블의 로우가 있을 경우 포함하고, 없을 경우 로우를 포함하지 않는 링크 테이블을 사용하면 nullable 문제를 해결할 수 있다.


## 일대다

일대다 양방향 매핑은 bag이 가장 효율적인 특성을 가지고 있음.
List를 사용할 경우, 단방향 매핑이 됨.



## 다대다




## 실습 코드

1. 공유 기본키 엔티티 연관관계
```java
User john = new User("John Smith");
// address에 user 매핑
Address address = new Address(john, "Flower Street", "01246", "Boston");
// user에 address 매핑
john.setShippingAddress(address);
// 영속화 전이`
userRepository.save(john)
```
2. 외래키 조인 컬럼 활용
```java
User john = new User("John Smith");

Address address = new Address("Flower Street", "01246", "Boston");

john.setShippingAddress(address);

userRepository.save(john)
```
외래키 조인의 경우 양방향 연관관계 매핑이 간소화된다.

3. 