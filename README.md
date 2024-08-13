# 고급 엔티티 연관관계 매핑


## 일대일 

주 테이블이나 대상 테이블 둘 중 어느 곳이나 외래 키를 참조할 수 있음.

### 일대일 단방향
다대일 단방향과 비슷하다.
외래 키를 가지는 쪽에 ``@OneToOne`` 대상 테이블에 ``mappedBy``

### 대상 테이블에 외래 키
JPA에서는 일대일 방향 관계에서 대상 테이블에 외래 키가 있는 경우를 지원하지 않는다.
 => 연관관계를 수정하거나, 양방향으로 만들고 대상 테이블 엔티티를 연관관계의 주인으로 설정해야함. 
     ex) 주 테이블 ``@OneToOne(mappedBy="")``, 대상 테이블 ``@OneToOne @JoinColumn``



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

### 다대일 
INSERT문 한 번에 엔티티 저장과 연관관계를 처리할 수 있음(FK를 참조하고 있기 때문).

### 일대다
일대다 단방향 매핑을 사용할 경우, INSERT문 한 번으로 엔티티의 저장과 연관관계 처리를 끝내지 못하고 1:N 참조를 확인해서 FK를 업데이트 해주어야한다. 
 -> 1 -> N UPDATE
 -> 일대다 단방향 매핑 대신 다대일 단방향 매핑을 사용해야함.

#### mappedBy

``@JoinColumn`` 애너테이션이 있는 쪽이 연관관계의 주인.
mappedBy가 있는 쪽은 매핑을 참조하는 거울이다.
``@ManyToOne``: @JoinColumn
``@OneToMany``: mappedBy 




## 다대다

### DB
관계형 DB는 정규화된 테이블 2개로 다대다를 표현할 수 없으므로 연결 테이블을 사용한다.
 => 양 측 모두 FK를 넣을 수 있는 컬럼은 한개이기 때문.
양 측 테이블의 ID들이 담긴 연결 테이블을 사용한다.

### 객체
객체는 서로 컬렉션을 참조하는 방식으로 다대다 매핑이 가능하다.``@ManyToMany``
엔티티는 연결 테이블을 신경쓰지 않아도 된다.
연관관계 매핑 시 연결 테이블에 INSERT가 영속 전이
엔티티 컬렉션을 통한 조회 시, 연결 테이블과 조회 대상을 조인하는 쿼리 발생



### 단방향
다대다 단방향에서는 ``@JoinColumn``이 아닌, ``@JoinTable``을 사용한다.
 => 컬럼에 FK를 매핑할 수 없어서 연결 테이블을 사용해야한다.

- ``@JoinTable.name``: 연결 테이블을 지정
- ``@JoinTable.joinColumns``: 매핑할 조인 컬럼 정보를 지정
예를 들어 Customer에 @JoinTable을 사용했으면 joinColumns속성은 CUSTOMER_ID로 지정
@JoinTable.inverseJoinColumns: 반대 방향으로 매핑할 조인 컬럼 정보를 지정
Address에 @JoinTable을 사용했으면 ADDRESS_ID로 지정
```java
    @ManyToMany(cascade = {CascadeType.PERSIST, })
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "TB_CUSTOMER_ADDRESS",
               joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    private Set<Address> addresses = new HashSet<>();
```


### 양방향
역방향에도 @ManyToMany 사용
연관관계의 주인이 아닌 쪽에 mappedBy 속성 사용
연관관계의 주인 쪽은 단방향과 마찬가지로 @JoinTable 사용하면 됨


```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {   

 @Id
 @GeneratedValue
 @Column(name = "item_id")
 private long id;

 ...
 @ManyToMany(mappedBy = "items")
 private List<Category> categories = new ArrayList<>();
 ...


}

@Entity
@Getter @Setter
public class Category {

 ...
 @ManyToMany
 @JoinTable(name = "category_item",
         joinColumns = @JoinColumn(name = "category_id"),
         inverseJoinColumns = @JoinColumn(name = "item_id")   //category_item 테이블에 item쪽으로 들어가는 값을 매핑해줌
 )
 private List<Item> items = new ArrayList<>();
...
}
```

다대일 양방향과 마찬가지로 연관관계 편의 메서드를 사용하는 것이 유리
=> 연관관계 편의 메서드?


### 링크 테이블이 없는 다대다
우회는 가능하나, 한 쪽은 읽기 전용으로만 사용이 가능하다.
```java
User user = new User();

Address address = new Address();

user.setAddress(address);

address.setUser(user);

userRepository.save(user);

addressRepository.save(address);
```






### 다대다 매핑의 한계와 연결 엔티티
실무에서 사용하기에 한계가 있다.

- 제어 한계: 연결 테이블이 숨겨져 있으므로 예상하지 못한 쿼리가 생길 수 있음
- 확장 한계: 
  - 연결 테이블에 추가 정보가 들어가면 @ManyToMany사용 불가능 ex)주문 수량, 날짜, 시간 등의 정보 추가 
  - 추가한 컬럼들을 엔티티에 매핑할 수 없음

  - 확장 한계 극복:
    - 연결 테이블을 아예 엔티티로 만드는 방법
 
연결 테이블처럼 MEMBER_ID와 PRODUCT_ID를 복합키로 하는 기본 키를 사용하려면 @IdClass를 사용해야 함.
```java
// IdClass
public class MemberProductId implements Serializable {
 private Long member;
 private Long product;

 // equals and hashcode...
}

@Entity
@IdClass(MemberProductId.class)
public class MemberProduct {
    // 회원상품(MemberProduct)엔티티에 회원, 상품을 각각 다대일 관계로 매핑
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    ...
}

```
#### @IdClass, @EmbeddedId
복합키로 사용할 식별자 클래스를 만들어서 매핑해준다.
 - Serializable을 구현해야한다.
 - equals, hashcode를 오버라이딩 해야한다.
 - 기본 생성자가 있어야한다.
 - 접근 제어가 public이어야 한다.(리플렉션)





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

@JoinColumn
https://ksh-coding.tistory.com/105

2-2-2. OneToMany 단방향 : Target Entity(Table)에 FK 위치
Copy
public class Line {

	...
    
    @OneToMany
    @JoinColumn
    private List<Station> stations = new ArrayList<>();
}
이렇게 @OneToMany 단방향에 @JoinColumn이 붙어있으면,

FK는 Target Entity(Station)에 위치한다.

이때, 조심해야할 점은 @JoinColumn에서 FK의 name을 지정해주지 않으면 기본값이 설정되는데

기본 값으로 참조하는 엔티티(Line)의 필드명(stations) + "_" + 참조된 엔티티(Station)의 PK(id)로 지정되므로

위의 예시에서는 FK의 이름으로 stations_id가 지정된다.



따라서 @OneToMany 단방향에 @JoinColumn을 사용한다면, name 속성으로 올바른 FK 이름을 지정해야한다.

(성능 이슈 및 불편함이 있어서 @OneToMany 단방향은 잘 사용하지 않는다.)





## GPT

JPA (Java Persistence API)에서 다대다(N
) 연관관계는 두 개의 엔티티가 서로 다수의 인스턴스를 가질 수 있는 관계를 의미합니다. 예를 들어, 학생과 강의 관계를 생각해보면, 한 학생이 여러 강의를 수강할 수 있고, 한 강의에도 여러 학생이 수강할 수 있습니다. 이와 같은 관계를 JPA에서 어떻게 모델링하는지에 대해 자세히 설명하겠습니다.

다대다 연관관계의 기본 개념
다대다 관계:
두 엔티티 간의 관계가 다대다로 설정됩니다.
각 엔티티의 인스턴스가 서로 여러 개의 인스턴스와 연결됩니다.
다대다 연관관계 모델링
JPA에서 다대다 연관관계를 매핑하려면, 중간에 연관관계를 표현하는 테이블(조인 테이블)을 사용해야 합니다. 이 테이블은 두 엔티티의 기본 키를 외래 키로 사용하여 다대다 관계를 표현합니다.

1. @ManyToMany 애너테이션
정의: @ManyToMany 애너테이션을 사용하여 다대다 관계를 설정합니다.
조인 테이블: JPA는 자동으로 조인 테이블을 생성합니다. 이 조인 테이블은 두 엔티티의 외래 키를 포함합니다.
2. 예제 코드
예제 시나리오: Student와 Course 엔티티 간의 다대다 관계를 모델링합니다.

Student.java

java
코드 복사
import javax.persistence.*;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course", // 조인 테이블 이름
        joinColumns = @JoinColumn(name = "student_id"), // 이 엔티티의 외래 키
        inverseJoinColumns = @JoinColumn(name = "course_id") // 상대 엔티티의 외래 키
    )
    private Set<Course> courses;

    // getters and setters
}
Course.java

java
코드 복사
import javax.persistence.*;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "courses") // 매핑된 필드의 이름을 명시
    private Set<Student> students;

    // getters and setters
}
@JoinTable 애너테이션
name: 조인 테이블의 이름을 지정합니다.
joinColumns: 현재 엔티티의 외래 키를 지정합니다.
inverseJoinColumns: 상대 엔티티의 외래 키를 지정합니다.
주의사항
기본 키 설정: 조인 테이블의 기본 키는 보통 두 엔티티의 외래 키로 구성됩니다. JPA는 이 조인 테이블의 기본 키를 자동으로 설정합니다.
중복 데이터: 중복된 관계를 피하기 위해 적절한 중복 방지 로직을 구현해야 할 수 있습니다.
성능: 다대다 연관관계는 조인 테이블을 통해 많은 양의 데이터와 연관될 수 있으므로 성능을 고려해야 합니다. 예를 들어, 대량의 데이터를 다룰 때 쿼리 최적화가 필요할 수 있습니다.
대안
JPA에서 다대다 관계를 직접적으로 매핑하기보다는, 다대일 및 일대다 관계를 사용하여 중간 엔티티를 통해 간접적으로 다대다 관계를 표현하는 것이 일반적입니다. 이를 통해 더 세밀한 제어와 확장성을 제공할 수 있습니다.

예제: 중간 엔티티를 사용한 다대다 관계

StudentCourse.java (중간 엔티티)

java
코드 복사
import javax.persistence.*;

@Entity
public class StudentCourse {
    @EmbeddedId
    private StudentCourseId id = new StudentCourseId();

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    // getters and setters
}
StudentCourseId.java (복합 키 클래스)

java
코드 복사
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StudentCourseId implements Serializable {
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    // equals and hashCode methods
}
이 방법은 Student와 Course 간의 관계를 StudentCourse라는 중간 엔티티를 통해 모델링합니다. 이 방식은 추가적인 정보를 조인 테이블에 저장할 수 있는 장점이 있습니다.

이와 같이 JPA에서는 다대다 관계를 다양한 방식으로 모델링할 수 있으며, 필요에 따라 적절한 방법을 선택하여 사용하면 됩니다.



