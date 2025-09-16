# spring-tutorial-22nd
CEOS 백엔드 22기 스프링 튜토리얼

# Spring
자바 엔터프라이즈 개발을 편하게 해주는 애플리케이션 프레임워크
### 1. 오픈소스
- 누구나 무료로 사용할 수 있음.
- 안정적인 개발과 개선이 보장됨.
### 2. 경량급
- 개발자 관점에서 개발 부담이 적다는 의미
- 개발자 대신 프레임워크가 많은 부분을 담당 → 개발 범위와 분량의 경량화
### 3. 애플리케이션 프레임워크
- 특정 기술에 국한되지 않고, 애플리케이션 개발 사이클 전 범위에 필요한 기능과 뼈대를 제공
- 프레임워크란?
    - 클래스 묶음, 뼈대, 틀을 라이브러리 형태로 제공 → (1) 구현 시간 단축 + (2) 코드 재사용성 향상
        - 라이브러리 = 클래스의 나열

> cf) Spring이 없던 시절.. EJB 방식 기술을 사용 → 별도의 EJB 컨테이너 없이 기술을 구현하기 어려웠고, 무엇보다 *자동화된 테스트가 불가능*하다는 치명적인 단점이 있었음.


# Spring의 특징
### 객체 직접 관리
- 스프링이 직접 자바 객체를 관리
    - 객체의 생명 주기(생성, 소멸) 관리 → 스프링 컨테이너에 필요한 객체를 가져와서 사용
### POJO (Plain Old Java Object)
순수 Java 만을 통해서 생성한 객체를 지향
- 특별한 상속 또는 인터페이스 없이 평범한 자바 객체를 사용 → 프레임워크 의존성 최소화 → 재사용성 증가
```java
public class Car {
	private int speed;
	
	public Car(int speed){
		this.speed = speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getSpeed(){
		return this.speed;
	}
}
```
#### POJO의 장점
1. 특정 프레임워크 or 라이브러리에 종속적이지 않음 → 재사용성, 유연성, 깔끔한 코드
2. 환경 제약 X → 자동화 테스트 이점
3. 객체지향적 설계 자유도 보장

#### POJO 프레임워크
Spring => 비즈니스 로직 복잡성 & 엔터프라이즈 기술 복잡성 분리
> 1) 비즈니스 로직: 소프트웨어가 현실 세계의 문제를 해결하는 방법을 구체적으로 구현한 것
> 2) 엔터프라이즈 기술: 기업의 운영을 지원하고 비즈니스 목표 달성에 기여하기 위해 대규모 조직에 적용되는 기술과 솔루션 (JDBC, 트랜잭션 관리, 보안, …)

Spring → 엔터프라이즈 기술의 복잡함을 추상화/캡슐화하여 POJO 기반 코드에 사용할 수 있도록 함.
개발자 → POJO로 비즈니스 로직 개발에 집중

# Spring 삼각형

<img width="1149" height="1051" alt="image" src="https://github.com/user-attachments/assets/01f5ef2e-37c4-43a1-b960-84ccb9bad885" />

## IoC/DI
### IoC (Inversion of Control)
- 제어의 역전
    - 객체 생명주기 관리 권한을 *개발자가 아닌 외부 시스템 (=프레임워크, 컨테이너)* 에 위임.
- OOP에서 *유연성* + *유지보수성*을 높이는 핵심 철학

#### 예시 코드
1. 클래스가 직접 객체 생성
```java
public class Car(){
	private Tire tire;
	
	public Car(){
		this.tire = new KoreaTire();
	}
}
```
- 문제
    1. OCP 위배 → 변경에 열려 있음
        - 추후 새로운 Tire 구현체로 교체하고 싶으면 Car 클래스 생성자 내부 코드를 함께 수정해야 함.
    2. Mock 및 Test용 객체 주입 어려움
        - 어떤 타이어의 제동력이 가장 좋은지 테스트하고 싶을 때, 위 경우 Car 클래스 생성자 내부의 코드를 한 번씩 수정해가면서 성능을 측정해야함.
            - 타이어의 종류가 1억 개라면? 집에 갈 수 없음 💀
    3. 모든 자동차가 반드시 같은 타이어를 사용하게 됨 → 유연성 X
- 해결
    - 개발자가 직접 객체를 생성하지 않고, 객체의 생명 주기 관리를 외부(=Spring Container)에 위임
    - Spring Container의 역할
        - 빈 관리
            - 빈 = 객체
            - 개발자는 객체 생성 & 관리 신경 X
        - 의존성 주입
            - Bean 사이의 의존성 관리 및 주입
            - 객체 간 결합도를 낮추고 코드 재사용성 & 유지보수성 향상
        - 생명주기 관리
            - 초기화 & 소멀 시점에 [[콜백 메서드]] 호출 가능
            - 특정 설정 활성화, 리소스 정리, …
        - 설정 관리
            - xml, java 설정 클래스, 어노테이션 기반

### DI (Dependency Injection)
- 의존성 주입
    - IoC 방법론 중 하나
    - 외부 컨테이너가 생성한 객체를 주입받아 사용 → 사용할 객체를 클래스가 직접 생성하지 않음

#### 1. 생성자 (Spring 권장) ⭐
```java
public class Car(){
	private Tire tire;
	
	public Car(Tire tire){
		this.tire = tire;
	}
}
```
생성자를 통해 외부에서 Tire 구현 객체를 주입받는다.
```java
Tire aTire = new ATire();
Tire bTire = new BTire();
Car myCar = new Car(aTire);
Car yourCar = new Car(bTire);
```
- Car 클래스를 작성하는 동안 어떤 타이어를 사용하게 될지 신경쓰지 않아도됨.
    - 구현체에 의존하지 않고 추상화된 인터페이스에 의존하기 때문.
- Car - Tire 결합도 낮아짐

##### 1-1. Lombok (개발 편의성)
```java
@RequiredArgsConstructor
public class Car {
	private final Tire tire;
}
```
`@RequiredArgsConstructor`: `final` 또는 `@NotNull` 필드를 대상으로 생성자를 자동으로 생성
- `final`로 선언된 필드는 반드시 초기화 되어야 함 → 생성자를 통해 주입 가능
- Setter 필요 X
- 반복적인 생성자 코드를 작성하지 않아도 돼서 번거로운 중복 작업 최소화

```java
public class Car {
	private final Tire tire;
	
	public Car(Tire tire){
		this.tire = tire;
	}
}
```
- 위 코드와 동치
- 컴파일 과정에서 위 코드와 같이 생성자가 자동으로 생성됨.

#### 2. 필드 객체 선언
한 번 주입된 의존성을 변경하지 않고 유지하는 경우에 적합 → 덜 유연한 느낌
```java
@Bean
public class KoreaTire implements Tire {
	// ... 구현
}
```
- `@Bean`: Bean에 Tire가 등록됨
```java
public class Car(){
	@Autowired
	Tire tire;
}
```
- `@Autowired`: 스프링이 해당 필드에 필요한 객체를 자동으로 주입

#### 3. Setter (Bad Smell) ❌
```java
public class Car(){
	private Tire tire;
	
	public Car(){}
	
	// tire setter
	public void setTire(Tire tire){
		this.tire = tire;
	}
}
```

```java
Tire aTire = new ATire();
Car myCar = new Car(); // <- 이 시점에 myCar 객체는 불완전한 상태
myCar.setTire(aTire); // <- Tire 의존성 주입
```
- 불안정한 코드 → 누군가 setTire() 코드를 까먹는다면?
- 생성자를 통해 의존성을 주입하면 객체 생성 과정에 의존성 주입이 보장되기 때문에 더 안전함.

##### 참고 자료
- [Spring Dependency Injection Series](https://www.baeldung.com/spring-dependency-injection)

## AOP (Aspect-Oriented Programming)
관점 지향 프로그래밍 → 핵심 로직으로부터 횡단 관심사를 분리해서 하나로 묶어 사용하자
### 장점
- 깔끔한 핵심 로직 코드
- 가독성 및 유지보수성 확보
- 부가 기능 모듈에 대한 수정 → 해당 부분만 변경해도 부가 기능을 사용하는 모든 핵심 로직에 적용
- 공통 로직 적용 대상 선택 가능

### 용어
- Target
    - 부가 기능을 적용할 질제 객체
    - 핵심 비즈니스 로직이 담겨 있음
- Aspect
    - 부가 기능 모듈 (논리적 단위)
    - Aspect = Advice + PointCUt
    - Target에 부가되어 의미를 갖는 모듈

- Advice
    - Aspect에서 수행할 구체적 동작
    - Target에 종속 X => 순수 부가 기능 구현에만 집중 가능
    - Aspect가 무엇을 언제 할지를 정의
- JoinPoint
    - Advice가 적용될 수 있는 실제 실행 지점
    - 메서드 호출, 예외 발생 시점 등
- PointCut
    - 부가 기능이 적용될 대상(=JointPoint)을 정하는 방법
    - 어떤 JointPoint에 Advice가 적용될지 조건 정의
- Proxy
    - *Target을 감싸* 는 Wrapper 객체
    - Client로부터 Target에 들어오는 요청을 대신 받음 → Advice 실행 → Target 실행
    - Target 메서드에 대한 전처리 & 후처리 가능

### 사용 예시
1. 비즈니스 로직
```java
@Service
public class MemberService {
	public void join (Member member){
		System.out.println(member.getname() + "님이 참가함");
	}
	
	public void login (Member member){
		System.out.println(member.getname() + "님이 로그인함");
	}
}

```

2. Aspect 클래스 작성
```java
@Aspect
@Component
public class MemberLogging {
	@Before("execution(* com.ceos22.service.MemberService.*(..))") // -> 시점
	public void logBeforeMember(){
		System.out.println("LOG: 멤버 서비스 실행 시작");
	}
}
```
- `@Component` vs `@Bean`
    - `@Component`: POJO 클래스 레벨 어노테이션
        - 직접 만든 클래스
        - Spring이 자동 스캔 → 빈에 등록
    - `@Bean`: 메서드 레벨 어노테이션
        - `@Configuration` 어노테이션 밑에서 주로 사용
            - 개발자가 직접 Bean 생성 로직 구현 → return 객체를 Spring Container에 수동 등록
            - 라이브러리 객체, 커스텀 초기화 로직 등
        - 외부 클래스 또는 커스텀 생성 로직

- 메서드에 로직 주입가능한 위치
    - Around
    - Before
    - After
    - AfterReturning
    - AfterThrowing
## PSA (Portable Service Abstraction)
하나의 추상화로 여러 서비스를 묶어둔 것 → 구현 기술이 달라도 동일한 방식으로 사용할 수 있음.
- 추상화 계층의 핵심
- 비즈니스 로직을 수정하지 않고 여러 서비스를 교체할 수 있도록 서비스를 추상화

```java
// 1. 추상화
public interface PaymentService {
	void pay(String account, int amount);
}

// 2. 구현체
public class CreditCardPaymentService implements PaymentService{
	public void pay(String account, int amount){
		// 결제 로직
	}
}

// 3. Client (PSA 활용 주체)
@RequiredArgsConstructor
public class OrderService {
	private final PaymentService paymentService;
	
	public void checkout(String account, int amount){
		paymentService.pay(account, amount);
	}
}

// 사용
PaymentService payment = new CreditCardPaymentService();
OrderService orderService = new OrderService(payment);
orderService.checkout("9999-99-9999999", 10000);

```
- **실제 결제 서비스 구현체가 무엇이든, OrderService는 인터페이스에만 의존 → PSA**
    - 따라서 구현체가 바뀌더라도 영향을 받지 않고 안정적으로 동작할 수 있음

# Spring Boot
스프링 기반의 어플리케이션을 빠르게 개발하고 실행하기 위한 프레임워크
- Spring 기반 프로젝트를 즉시 수행할 수 있도록 도움
- 컴포넌트 코드 작성은 가벼운데 개발 구성, 설정이 무거웠던 문제의 대안

## 특징
- WAS
    - 웹 서버 내장: Tomcat
- 라이브러리 관리
    - 종속성 관리 및 라이브러리 버전 관리
- 자동 구성
    - 스프링 & 외부 라이브러리 빈 자동 등록
    - 스프링 애플리케이션에 공통으로 필요한 애플리케이션 기능 자동 구성
- 외부 설정
    - 환경에 따라 달라져야 하는 외부 설정 공통화
- 프로덕션 준비
    - 빈 관리
        - 스프링 애플리케이션 컨텍스트에 구성된 빈 확인 및 상태 모니터링
    - 환경 정보
        - 환경 변수, 시스템 프로퍼티, 구성 프로퍼티, …
    - 요청 정보
        - 최근 처리된 HTTP 요청 관련 메타데이터
    - 메트릭
        - 메모리 사용량, GC, 웹 요청 처리량, 등 다양한 성능 지표 제공


---
# Java Annotations
## 정의
- 코드에 대한 Metadata
- Java 코드에 덧붙이는 **특별한 기호**
    - 클래스, 메소드, 필드, 파라미터, 변수, 생성자, 패키지

## 어노테이션이 도입된 이유
- 어노테이션이 있기 전에는 XML이 메타데이터를 위해 사용됨. → 코드와 약하게 coupled 되어 있어서 관리에 어려움이 있었음.
    - xml은 코드와 설정을 분리하기 위해 도입 → 코드랑 loosely coupled → 불편 … (반복)
    - Annotation 이전에는 개발자마다 각자의 방식으로 Marker Interface, 주석, transient 키워드를 사용해서 메타데이터를 표현했음.
- 오늘날에는 XML과 어노테이션을 각자 필요에 따라 함께 사용함
    - 각각 장단점이 있음.
        - Application Wide Configuration → XML이 편함.
        - 어떤 메서드를 Service 레이어로 노출하고 싶음 → Annotation이 더 좋음 (코드랑 더 밀접하게 결합되어 있기 때문)
## 특징
- @ 기호로 시작
- 프로그램이 작동하는 방식을 변경하지 않음.
    - 다만, 컴파일러나 다른 도구들에 **부가 정보** 제공
- Built-in Annotations
    - `@Override`: 상위 클래스의 메서드를 오버라이드 함
        - 어노테이션이 없어도 정상적으로 Override 됨.
        - 하지만 어노테이션을 통해 명시하면 컴파일러가 부모 클래스에 해당 메서드가 존재하는지 확인해서 없으면 컴파일러 에러를 던짐
    - `@Deprecated`: Deprecated, 즉 더이상 사용하지 않는 메서드나 클래스 마킹
    - `@SuppressWarnings`: 컴파일러가 특정 경고를 무시하도록 함

## 동작 원리
핵심 전제: *어노테이션은 메타데이터 이외의 어떠한 비즈니스 로직도 포함하지 않는다.*
→ 따라서 메타데이터를 소비하고 실제로 무언가를 하는 *Consumer*가 존재
- 어노테이션: 클래스, 메서드, 패키지, 필드의 속성에 대한 정보만 제공
- Consumer: 정보를 읽어서 필요한 로직을 수행하는 코드 덩어리\
    - e.g) `@Override`의 Consumer = JVM → Bytecode 레벨에서 동작

### 구현 예시
`@Override`의 구현 예시 (실제 모습은 아님)
```java
import java.lang.annotation;

@Target(ElemnetType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {}
```
- `@Target`: 어노테이션을 붙일 수 있는 범위 (.METHOD = 메서드 선언에만 사용 가능)
- `@Retention`: 어노테이션이 유지되는 범위 (.SOURCE = 소스코드에서만 존대, 컴파일 시 사라짐)
    - 이때문에 `@Override`는 실행 시점에는 아무 영향이 없고 컴파일러가 검사하는 용도로만 사용됨.
- `@interface`: 어노테이션 정의

### 커스텀 어노테이션
`@Target`
- ElementType.TYPE(class, interface, enum)
- ElementType.FIELD(instance variable)
- ElementType.METHOD
- ElementType.PARAMETER
- ElementType.CONSTRUCTOR
- ElementType.LOCAL_VARIABLE
- ElementType.ANNOTATION_TYPE(on another annotation)
- ElementType.PACKAGE(remember package-info.java)

`@Retention`
- RetentionPolicy.SOURCE
    - 컴파일 과정에서 무시됨 → 바이트코드(.class)로 변환되지 않음
    - `@Override`, `@SuppressWarnings`
- RetentionPolicy.CLASS
    - 런타임에는 필요 없고, 컴파일 or 클래스 로딩 전 단계에서만 의미
    - .class까지만 기록됨 → 하지만 런타임에 JVM이 로드하지는 않음 → Reflection으로 읽을 수 없음.
        - 컴파일 이후 Lombok 같은 바이트코드 처리 도구가 이 정보를 일고 코드 변환 및 검증에 활용 가능
    - 기본 설정값
- RetentionPolicy.RUNTIME
    - .class 파일 기록 + JVM이 로딩한 후에도 메타데이터로 유지
    - 런타임 동안 Reflection을 위해 유효해야 함 → 무시되지 않음.
    - *일반적으로 커스텀 어노테이션에 사용"

`@Inherited`
- 하위 클래스에 영향을 줄지 여부

#### 예시
##### 1. 어노테이션 속성이 여러 개인 경우
1. 커스텀 어노테이션 `@Todo` 작성
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementtType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Todo {
	/* 각 속성이 가질 수 있는 값을 enumerate 형식으로 정의 */
	public enum Priority {LOW, MEDIUM, HIGH}
	public enum Status {STARTED, NOT_STARTED}
	
	/* 어노테이션의 모든 속성은 메서드로 정의. defualt 값을 부여할 수 있음. */
	String author() default "ba2slk";
	Priority priority() default Priority.LOW;
	Status status() default Status.NOT_STARTED;
}
```

2. 사용
```java
@Todo(Priority = Todo.Priority.MEDIUM, author = "ba2slk", status = Todo.Status.STARTED)
public void methodInProgress() {
	// 아직 작업 중인 메서드
}
```

##### 2. 어노테이션 속성이 한 개인 경우
이때 속성은 반드시 `value`라는 이름의 메소드로 정의되어야만 사용 시에 속성 이름 없이 사용 가능

1. 커스텀 어노테이션 `@Author` 작성
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
	String value() default "ba2slk";
}
```

2. 속성 이름 없이 값만 제공
```java
@Author("Itsme")
public void someMethod(){}
```

### Consumer 작성
[[Reflection]]을 사용해서 구현
- (+) Reflection이란?
    - 런타임에 메서드, 필드, 생성자 등의 메타데이터를 읽거나 조작할 수 있게 해줌 → 코드가 자기 자신(클래스/객체)의 구조를 탐색 및 변경
    - 클래스 이름만 알고 있어도 클래스 정보 획득 가능
    - 클래스 구조 조회 → 필드, 메서드 목록, 생성자, 어노테이션 확인
    - 동적 메서드 실행 → 컴파일 시점에 어떤 메서드를 호출할 지 몰라도 런타임에 호출 가능
        - `method.invoke(obj, args)`
    - private 필드/메서드로 `setAccessible(true)`로 접근 가능
    - `getAnnotation()`: 어노테이션 객체 반환
        1. `instanceOf()` 확인 후
        2. 해당 객체를 커스텀 어노테이션으로 캐스팅(형 변환)

#### 메타데이터를 소비하는 Consumer 로직 예시
```java
Class businessLogicClass = BusinessLogic.class;

// 0. BusinessLogic 클래스에 정의된 모든 메소드를 순회하면서
for (Method method : businessLogicClass.getMethods()) {

	// 1. 메서드에 붙은 @Todo 어노테이션 읽기
	Todo todoAnnotaion = (Todo)method.getAnnotation(Todo.class); 
	
	// 2. 만약 @Todo 어노테이션이 붙어 있었다면
	if (todoAnnotation != null) {
		System.out.println(" Method Name : " + method.getName());
		
		// 3. 메타데이터 출력
		System.out.println(" Author : " + todoAnnotation.author());
		System.out.println(" Priority : " + todoAnnotation.priority());
		System.out.println(" Status : " + todoAnnotation.status());		
	}
}
```
###### 참고 자료
- [Java Annotations](https://www.w3schools.com/java/java_annotations.asp)
- [How Do Annotations Work in Java?](https://dzone.com/articles/how-annotations-work-java)


# 스프링 빈(Bean)과 어노테이션
## 빈 선언과 등록 과정
### Step 1) Bean Definition 선언
#### Bean이란?
- `@Bean`은 메서드 레벨의 어노테이션
- *`@Configuration` 또는 `@Component` 어노테이션이 붙은 클래스 안에서* 사용할 수 있음.
    - +) `@Service`, `@Controller`, `@RestController`, `@Repository`
- 메서드에 `@Bean` 어노테이션 붙이기 → 메서드가 반환하는 객체를 스프링이 관리하는 Bean으로 등록
- 즉, 메서드가 반환하는 객체의 타입이 Bean의 타입이 됨.
- Bean 이름 = 메서드 이름 (default)

#### 1. 반환 타입을 구현체로 선언
```java
@Configuration
public class AppConfig {
	@Bean
	public TransferServiceImpl transferService() {
		return new TransferServiceImpl();
	}
}
```
→ 현재 `ApplicationContext` 내에서 유효한 `transferService`라는 이름의 Bean이 등록된 상태
> ApplicationContext란?
> : 스프링의 핵심 컨테이너로서 Bean관리, 의존성 주입, AOP 등 기능을 제공함.
- transferService → com.acme.TransferServiceImpl 형태로 매핑


#### 2. 반환 타입을 인터페이스로 정의
```java
@Configuration
public class AppConfig {
	@Bean
	public TransferService transferService() {  // TransferService는 인터페이스
		return new TransferServiceImpl();
	}
}
```
- Bean은 메서드의 반환 타입으로 등록되므로 스프링 컨테이너는 Bean을 인터페이스 기준으로만 인식
- `@Autowired TrasnferServiceImpl` 실패 → 구현체 타입으로 바로 매핑되지 않기 때문
- 인터페이스 기반으로 설계하면 유연하기는 함.

##### 그래서 어떻게 해야하나?
위의 각 구현 패턴에 대해서
1. Bean 정의를 구현체 타입으로 반환하기
2. `@Autowired` 쓸 때 인터페이스 타입으로 주입하기

#### 3. 인터페이스 default 메서드로 Bean 정의
```java
public interface BaseConfig {
	@Bean
	default TransferServiceImpl transferService() {
		return new TransferServiceImpl();
	}
}

@Configuration
public class AppConfig implements BaseConfig  {}
```
- BaseConfig를 구현하는 설정 클래스에서 Bean  구성 재사용 가능
    - 즉, 여러 설정 클래스에서 인터페이스를 구현하면 동일한 Bean 정의 공유 가능

### Step 2) Bean Definition 로드
- `BeanDefinitionReader`가 configuration을 파싱하여 `BeanDefinition` 객체를 생성함

### Step 3) Bean Definition 처리
- 실제 Bean이 생성되기 전에 `BeanDefinition`을 일부 수정하는 단계
- `BeanFactoryPostProcessor` 인터페이스가 `BeanDefinition`에 접근하고 수정하는 것을 가능하게 함.

### Step 4) Bean 인스턴스화
- `BeanFactory`가 각 Bean의 생성자를 호출해서 `FactoryBean` 객체 생성

### Step 5) 의존성 주입 및 Setters 호출
- 생성자에 의존성이 주입되는 경우
    - 의존성 빈들이 먼저 생성되고
    - 해당 의존성에 기대는 빈이 이후에 생성됨 (당연한 순서)

### Step 6) Bean 후처리
- `BeanPostProcessor`가, 완전히 생성됐지만 초기화되지 않은 빈들을 조정함.
- *init-method* 이전에 호출되며 후처리된 bean을 반환함.

### Step 7) Bean 초기화
- `@PostConstruct` 초기화 메서드가 트리거되고 bean이 초기화됨.

### Step 8) 초기화 이후의 Bean 후처리
- bean을 proxy로 감싸거나, 순환 의존성이 있는 경우
- `BeanPostProcessor`: step 6 이후에 한 번 더 접근.

### Step 9) ApplicationContext
- 이 시점에 와서야 Bean이 `ApplicationContext`에 살게 되어 접근 가능해짐.

### Step 10) Bean 파괴
- context가 파괴되고 bean이 버려지는 시점
- `@PostDestroy` 어노테이션이 붙은 destroy method가 실행됨.

##### 참고 자료
- [Using the @Bean Annotation](https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html#beans-java-declaring-a-bean)
- [Spring Bean Life Cycle이란?](https://cn-c.tistory.com/98)


## 스프링이 컴포넌트를 탐색하는 방법
`@ComponentScan` 은 어노테이션 기반 설정을 사용하는 클래스를 찾기 위해 어떤 패키지를 스캔할지 설정
- 앞서 `@Component`, `@Service` 등 스프링 어노테이션이 붙은 클래스를 감지해서 빈에 등록하기 위함.

### @ComponentScan 사용하기
1. `basePackages` 속성
```java
@Configuration
@ComponentScan(basePackages = "com.example.annotations")
class AppConfig {}
```
- `basePackages` 속성에 명시한 패키지와 하위 패키지를 모두 스캔해서 어노테이션이 붙은 클래스를 빈으로 등록
- 오타 위험

2. `basePackageClasses` 속성
```java
@Configuration
@ComponetScan(basePackageClasses = AppConfig.class)
class AppConfig {}
```
- 클래스 위치를 기준으로 패키지 스캔
    - AppConfig.class가 속한 패키지와 그 하위 패키지를 스캔
- 클래스를 직접 참조하기 때문에 문자열보다 안정적

→ 1, 2의 기능과 목표는 동일함.
→ 둘 다 배열이기 때문에 여러 개를 한 번에 넘겨줄 수 있음.

3. `@ComponentScan` 인자가 없는 경우
    - `@ComponentScan` 이 붙은 클래스가 존재하는 패키지에서부터 스캔 시작

4. `@ComponentScans` 로 `@ComponentScan` 여러 개를 한 번에 명시 가능
```java
@Configuration
@ComponentScans({
	@ComponentScan(basePackages = "com.example.annotations"),
	@ComponentScan(basePackageClasses = AppConfig.class)
})
class AppConfig {}
```

### @ComponentScan의 탐색 대상
#### @Component
- class-level
- Spring은 `@Component` 클래스를 자동으로 감지함
- bean 인스턴스는 소문자로 변환된 클래스 명으로 등록
    - `@Repository`, `@Service`, `@Configuration`, `Controller` => 모두 `@Component`의 meta-annotatino이므로 같은 네이밍 스킴 공유
    - 따라서 Spring이 컴포넌트를 스캔하는 과정에서 모두 선택됨.

#### @Repository
- DAO, Repository 클래스 → 데이터베이스 접근 레이어
- 사용하면 좋은 점?
    - DB 기술이 바뀌어도 Hibernate 같은 영속성 프레임워크가 던지는 예외를  `DataAccessException`으로 변환해서 일관된 처리 가능
        - 내부적으로 `PersistenceExceptionTranslationPostProcessor` 동작
#### @Service
- 비즈니스 로직
#### @Controller
- class-level
- Spring MVC에서 Contoller 역할 명시
#### @Configuration
- `@Bean`으로 표시된 Bean 정의 메서드를 포함하는 클래스
#### Stereotype 어노테이션과 AOP
- 로깅 실행 시간 측정 등 …
- e.g) `@Aspect`에서 stereotype 기준 pointcut 생성 (`@Repository` 어노테이션이 붙은 메서드를 모두 찾는 pointcut) → `@Around` advice 로 해당 pointcut 타겟

### @SpringBootApplication
- `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`

### 스캔 필터
`@ComponentScan.Filter()`
- FilterType.ANNOTATION
    - 컴포넌트 스캔 과정에서 특정 어노테이션 표시가 된 클래스를 포함/배제
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialComponent {}

@SpecialComponent
public class MyBean {}

@Configuration
@ComponentScan(
	basePackages = "com.example",
	includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = SpecialComponent.class)
)
public class AppConfig {}
```
- FilterType.ASSIGNABLE_TYPE
    - 특정 인터페이스의 구현 클래스를 스캔하여 포함/배제
- FilterType.ASPECTJ
    - 표현식을 사용해서 클래스의 복잡한 부분집합을 뽑아내고 싶은 경우
- FilterType.REGEX
    - 클래스 이름이 주어진 정규 표현식을 만족하는 경우를 포함/배제
- FilterType.CUSTOM
    - 개발자가 원하는 대로 커스텀 필터를 만들 수 있음.
    - `org.springframework.core.type.filter.TypeFilter`를 구현해서 만들 수 있음.

##### 참고 자료
- [Spring Bean Annotations](https://www.baeldung.com/spring-bean-annotations)
- [Spring @ComponentScan - Filter Types](https://www.baeldung.com/spring-componentscan-filter-type)

### Dynamic Dependency Injection
하나의 interface를 구현한 service가 여러 개 있을 때 주입하는 방법
#### 1. Map, List 이용
##### List
- 특정 인터페이스를 구현한 모든 빈을 자동으로 주입받을 수 있음
##### Map
- <Bean 이름, 값> 형태로 빈을 주입받을 수 있음

#### 2. @Qualifier
- 빈 이름을 `@Qualifier`의 값으로 사용해서 명확하게 지정 가능
```java
@Service
public class APaymentServiceImpl implements PaymentService {
	// 비즈니스 로직
}

@Service("BPayment")
public class BPaymentServiceImpl implements PaymentService {
	// 비즈니스 로직
}

@Service
public class Order {
	@Autowired
	@Qualifier("BPayment") // 의존성 명시
	private PaymentService paymentService;
	
	public void pay() {
		paymentService.pay()
	}
}
```

#### 3. @Primary
동일한 타입의 빈이 여러 개 있을 때, 우선적으로 주입될 default 빈 지정에 사용
```java
@Service
public class APaymentServiceImpl implements PaymentService {
	// 비즈니스 로직
}

@Primary // <- BPaymentServiceImpl을 기본 빈으로 사용하겠다.
@Service
public class BPaymentServiceImpl implements PaymentService {
	// 비즈니스 로직
}

@Service
public class Order {
	@Autowired
	private PaymentService paymentService; // BPaymentServiceImpl이 주입됨.
	
	public void pay() {
		paymentService.pay()
	}
}
```
- `@Primary`랑 `@Qualifier`를 함께 사용할 수 있음
    - `@Primary` → 기본 의존성 지정
    - `@Qualifier` → 직접 원하는 의존성 지정

---

# 3. Spring MVC 심층 분석
## MVC 패턴 vs Spring MVC
### MVC 패턴
- Application의 관심사를 분리하기 위한 디자인 패턴 (철학)
    - 어떤 물리적 실체라기 보다는 추상적, 구조적 이론에 가까움
- Model-View-Controller
    - 데이터 모델, 정보 표시, 정보 조작 → 각자 다른 Object에서 처리되어야 함.

### Spring MVC
- Servelet API 기반 웹 프레임워크의 원형
    - 태초의 Spring Framework에서부터 포함되어 있었음
- 공식 이름은 `spring-webmvc` 모듈 명을 따서 'Spring Web MVC'이지만, 보통 Spring MVC로 알려짐
>  Spring Framework → IOC, AOP, ORM, Test, Web MVC 등 전체 프레임워크 집합
>  Spring MVC → MVC 디자인 패턴에 기반해 웹 어플리케이션을 만들기 위해 Spring이 지원하는 기술

## Servlets과 & 요청 처리
### Servlets
- 자바 기반의 웹 & 어플리케이션 서버를 구동하는 자바 프로그램
- 클라이언트 요청을 처리하고 동적으로 응답 생성
- 효율성과 확장성으로 인해 server-side 자바 애플리케이션의 backbone임.
    - 각 요청에 대해 새로운 프로세스를 생성하지 않아 실행이 빠름
    - JDBC를 사용해서 쉽게 DB를 통합할 수 있음
    - 우수한 보안성
    - Apache Tomcat 같은 많은 웹 서버가 Java Servlet을 무료로 사용

### 웹 요청 처리
#### 구조
<img width="547" height="432" alt="image" src="https://github.com/user-attachments/assets/f665b35a-2fa6-4edf-a81a-97730325659e" />

##### 과정
1. 클라이언트가 웹 서버로 요청을 보냄
2. 웹 서버가 요청을 받음
3. 웹 서버가 상응하는 servlet으로 요청을 보냄
4. servlet이 요청을 처리하고 output 형태로 응답함
5. servelet이 웹 서버에게 응답을 보냄
6. 웹 서버가 클라이언트에게 응답을 보내고 브라우저가 이를 처리


#### Java Servlets Container (= Sevlet Engine)
Servelet 컴포넌트를 위한 런타임 환경을 제공하는 집합체.
Java Servelet Component를 관리하며 Web Server 위에서 클라이언트의 요청을 다룸.
- 로드 밸런싱
- 세션 관리
- 자원 할당
- 다수의 인스턴스에게 요청 분산 → 고성능

## Tomcat & WAS
### Apache Tomcat
자바를 위한 오픈 소스 웹 서버이자 Servlet Cotainer

#### Pros & Cons
Pros
- platform-independent
    - 자바가 설치되어 있다면 어디에서든 작동
- 배포의 용이성
  Cons
- 복잡성
- 커뮤니티에 국한된 support

#### 구성 요소
- Connector
    - 웹 서버 역할
    - HTTP 요청 받고 → 톰캣에 포워딩
- Container
    - Servelt API 실행, 동적 콘텐츠 생성, 요청 처리 (Servlet Engine)
- Application
    - 배포, 시작, 정지, 세션 관리 등 운영 기능
    - 웹 어플리케이션을 호스트 & 관리

### WAS (Web Application Server)
#### Web Server
- 인터넷을 기반으로 클라이언트에게 웹 서비스를 제공하는 주체
- http 요청에 대한 데이터를 만들어서 응답
    - 이때 처리할 수 있는 데이터는 *정적 데이터*로 한정
- 순수 Apache, Nginx 등
#### Web Application
- 웹에서 실행되는 응용프로그램
- 자바에서는 컨테이너라고 부름
#### Web Application Server
<img width="997" height="440" alt="image" src="https://github.com/user-attachments/assets/8900ba5f-b8a7-41aa-bfeb-26648504f738" />
- 웹 애플리케이션을 실행하여 필요한 기능을 수행하고 그 결과를 웹 서버에게 전달하는 일종의 미들웨어
- 동적인 페이지 생성 가능 + 비즈니스로직 처리
- **톰캣** → HTTP 요청 처리 (Web Server) + 동적 컨텐츠 생성 (WAS/Servelt Engine)

##### 참고 자료
- [Web Server와 WAS의 차이](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-%EC%9B%B9-%EC%84%9C%EB%B9%84%EC%8A%A4-%EA%B5%AC%EC%A1%B0-%EC%A0%95%EB%A6%AC)
- [아파치 톰캣 개념 구성 & 설정 정리](https://inpa.tistory.com/entry/TOMCAT-%E2%9A%99%EF%B8%8F-%EC%84%A4%EC%B9%98-%EC%84%A4%EC%A0%95-%EC%A0%95%EB%A6%AC)

# Dispatcher Servlet
### Front Controller 디자인 패턴
<img width="595" height="292" alt="image" src="https://github.com/user-attachments/assets/12d3d0e8-cd14-4842-b550-41bcfa5d883c" />
- 웹 애플리케이션에서 하나의 Front Controller가 모든 요청을 받음
- 그 요청의 종류에 따라 각각의 적합한 핸들러로 요청을 전달(dispatch)

#### 구성 요소
- Controller
    - Request가 도착하는 첫 번째 지점
    - 이 레이어에서 authentication, authorization 완수는 헬퍼가 담당할 수도 있음
- View
    - 클라이언트에게 정보 표시
    - 정보는 모델로부터 옴
    - 헬퍼들이 데이터 캡슐화 및 가공
- Dispatcher
    - 사용자가 보게 될 다음 view를 선택하고, 그 화면에 해당하는 리소스에 제어권을 넘겨주는 역할
- Helper
    - view나 controller를 도와서 작업 완료
    - 책임이 다양하고 넓음

#### Pros & Cons
Pros
- 중앙집중 제어
- Thread-Safe
  Cons
- 낮은 확장성

##### 참고 자료
[Front Controller Design Pattern](https://www.geeksforgeeks.org/system-design/front-controller-design-pattern/)


### Dispatcher Servlet 이란?
Spring Web Application의 Front Controller
- Entry Point → 모든 HTTP 요청은 가장 먼저 Dispatcher Servlet을 만나게 됨.
- 이후 해당 요청을 처리할 수 있는 `HandlerAdpater`를 찾아서 그 핸들러에게 요청 처리를 위임
    - `@Controller`, `@RequestMapping` → Handler Methods (컨트롤러 엔드포인트)
- [What is Dispatcher Servlet in Spring?](https://www.geeksforgeeks.org/java/what-is-dispatcher-servlet-in-spring/)

### DispatcherServlet 작동 흐름 (코드 분석)
#### init
- initStrategies
    - 아래의 모든 초기화 관련 메서드를 호출
- initMultipartResolver
- initLocalResolver
- **initHandlerMappings**
    - 요청 URL과 핸들러를 연결하는 `HandlerMapping` 전략 초기화
- **initHandlerAdapters**
    - HandlerMapping이 찾은 핸들러를 실제로 실행시킬 `HandlerAdapter` 전략 초기화
- initHandlerExceptionResolvers
- initRequestToViewNameTranslator
- **initViewResolvers**
    - Handler가 반환한 뷰 이름을 실제 뷰로 변환하는 `ViewResolver` 전략 초기화
- initFlashMapManager

#### get
필요한 객체나 정보를 반환하는 역할
- getMultipartResolver
- getHandlerMappings
- getDefaultStrategy
- **getDefaultStrategies**
    - `HandlerMapping`에게 현재 들어온 요청에 맞는 핸들러를 찾아달라고 요청
    - `doDispatch()`에서 가장 먼저 호출
        - `mappedHandler = this.getHandler(processedRequest);`
- **getHandlerAdapter**
    - `getHandler`를 통해 찾은 핸들러를 실행시키기 위해 적절한 `HandlerAdapter` 반환
- getDefaultViewName
- getRequestUri

#### create
- createDefaultStrategy

#### do
주요 로직을 실행하는 핵심
- **doService**
```java
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {  
	...  
    try {  
        this.doDispatch(request, response);  // doDispatch 호출
    } finally {  
        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted() && attributesSnapshot != null) {  
            this.restoreAttributesAfterInclude(request, attributesSnapshot);  
        }  
        
    ...
    
    }  
  
}
```
- 모든 HTTP 요청을 받아서 `doDispatch`로 전달

- **doDispatch**
```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ...
    try {
        ...
        try {
            ...
            // 요청에 맞는 핸들러 얻기
            mappedHandler = this.getHandler(processedRequest);
            if (mappedHandler == null) {
                this.noHandlerFound(processedRequest, response);
                return;
            }

            // 핸들러를 실행할 어댑터 얻기
            HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler());
            
            // 핸들러 실행 전 전처리
            if (!mappedHandler.applyPreHandle(processedRequest, response)) { return; }
            
            // 실제로 핸들러 실행 (핵심)
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
			
			// 핸들러 실행 후 전처리
			mappedHandler.applyPostHandle(processedRequest, response, mv);

        } catch (Exception ex) {
            dispatchException = ex;
        } catch (Throwable err) {
            dispatchException = new ServletException("...", err);
        }
        
        // ModelAndView 객체를 넘겨 받아서 응답 전 최종 가공
        this.processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);  
	} catch (Exception ex) {  
	    triggerAfterCompletion(processedRequest, response, mappedHandler, ex);  
	} catch (Throwable err) {  
	    triggerAfterCompletion(processedRequest, response, mappedHandler, new ServletException("Handler processing failed: " + String.valueOf(err), err));  
	}

    ...
}
```
- `mv`: ModelAndView 타입 객체로, 핸들러의 실행 결과를 받음
#### log
- logRequest

#### others
- applyDefaultViewName
- **processDispatchResult**
    - 핸들러 실행 결과로 받은 `ModelAndView` 객체를 처리해서 최종 응답 준비
    - `doDispatch` 메서드 안에서 가장 마지막 단계에서 호출됨
```java
private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {  
    boolean errorView = false;  
    if (exception != null) {  
        if (exception instanceof ModelAndViewDefiningException) {  
            ModelAndViewDefiningException mavDefiningException = (ModelAndViewDefiningException)exception;  
            this.logger.debug("ModelAndViewDefiningException encountered", exception);  
            mv = mavDefiningException.getModelAndView();  
        } else {  
            Object handler = mappedHandler != null ? mappedHandler.getHandler() : null;  
            mv = this.processHandlerException(request, response, handler, exception);  
            errorView = mv != null;  
        }  
    }  
  
    if (mv != null && !mv.wasCleared()) {  
        this.render(mv, request, response);  
        if (errorView) {  
            WebUtils.clearErrorRequestAttributes(request);  
        }  
    } else if (this.logger.isTraceEnabled()) {  
        this.logger.trace("No view rendering, null ModelAndView returned.");  
    }  
  
    if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {  
        if (mappedHandler != null) {  
            mappedHandler.triggerAfterCompletion(request, response, (Exception)null);  
        }  
  
    }  
}
```
- buildLocalContext
- checkMultipart
- hasMultipartException
- cleanupMultipart
- noHandlerFound
- processHandlerException
- **render**
    - ViewResolver를 통해 찾은 뷰에 데이터를 담아서 최종적으로 사용자에게 보여줄 응답 생성
    - `processDispatchResult`에서 호출됨
```java
protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {  
    Locale locale = this.localeResolver != null ? this.localeResolver.resolveLocale(request) : request.getLocale();  
    response.setLocale(locale);  
    String viewName = mv.getViewName();  
    View view;  
    if (viewName != null) {  
        view = this.resolveViewName(viewName, mv.getModelInternal(), locale, request);  
        if (view == null) {  
            String var10002 = mv.getViewName();  
            throw new ServletException("Could not resolve view with name '" + var10002 + "' in servlet with name '" + this.getServletName() + "'");  
        }  
    } else {  
        view = mv.getView();  
        if (view == null) {  
            String var9 = String.valueOf(mv);  
            throw new ServletException("ModelAndView [" + var9 + "] neither contains a view name nor a View object in servlet with name '" + this.getServletName() + "'");  
        }  
    }  
  
    if (view instanceof SmartView smartView) {  
        smartView.resolveNestedViews(this::resolveViewNameInternal, locale);  
    }  
  
    if (this.logger.isTraceEnabled()) {  
        this.logger.trace("Rendering view [" + String.valueOf(view) + "] ");  
    }  
  
    try {  
        if (mv.getStatus() != null) {  
            request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, mv.getStatus());  
            response.setStatus(mv.getStatus().value());  
        }  
  
        view.render(mv.getModelInternal(), request, response);  
    } catch (Exception var8) {  
        if (this.logger.isDebugEnabled()) {  
            this.logger.debug("Error rendering view [" + String.valueOf(view) + "]", var8);  
        }  
  
        throw var8;  
    }  
}
```
- **resolveViewName**
    - 논리적 뷰 → 실제 뷰 리소스 경로로 변환
```java
@Nullable  
protected View resolveViewName(String viewName, @Nullable Map<String, Object> model, Locale locale, HttpServletRequest request) throws Exception {  
    return this.resolveViewNameInternal(viewName, locale);  
}
```
- resolvViewNameInternal
- restoreAttributesAfterInclude


#### 구조 도식화
<img width="1426" height="359" alt="image" src="https://github.com/user-attachments/assets/8f851b57-ce74-446e-a5f6-28630ddf3deb" />

참고자료
- [https://mangkyu.tistory.com/216](https://mangkyu.tistory.com/216)
