# spring-tutorial-22nd
CEOS 백엔드 22기 스프링 튜토리얼

# 1주차

# 스프링의 이해

## Spring 이란?


: 오픈소스 경량급 애플리케이션 프레임워크

- 모두가 사용 가능
- 개발자가 작성해야 하는 코드가 단순
- 애플리케이션의 전체 구조

## Spring의 특징

### POJO (Plain Old Java Object)


: 특별한 프레임워크에 종속되지 않고 순수하게 Java만으로 작성된 객체

- 예전에는 특정 인터페이스(ex. EJB)를 구현해야 했음
- 특정 인터페이스를 구현할 필요 없이 Java 만으로 작성된 POJO는 프레임워크 교체에 유연함
- 특정 실행환경에 의존하지 않기 때문에 테스트에 용이함
- 객체지향 언어인 Java 본연의 특성을 살려 객체지향적 설계 가능

### POJO 프레임워크


: 특별한 규칙이나 API에 종속되지 않은 단순한 자바 객체(POJO)를 작성하면, 프레임워크가 이 객체를 컨테이너에 등록하고 관리해주면서 다양한 기능(DI, AOP, 트랜잭션 관리 등)을 제공하는 프레임워크

- 개발자는 순수 자바 객체만 만들고, 프레임워크가 그 객체를 애플리케이션에서 쓸 수 있도록 도와주는 방식
- Spring은 대표적인 POJO 프레임워크
    - IoC/DI, AOP, PSA 지원

## IoC/DI


: Inversion of Control(제어의 역전) / Dependency Injection(의존성 주입)

### IoC

- 개발자가 아닌 외부 시스템이 객체의 생성/흐름을 제어함
- IoC가 적용되지 않는 경우에는 개발자가 직접 객체를 생성(new)
- 스프링에서는 Spring Container에서 Bean(객체)의 생명 주기 관리

### DI

- IoC의 구현 방식 중 하나
- 개발자가 객체를 직접 생성하지 않고 외부 컨테이너가 생성한 객체를 주입하는 방식
- 의존성 주입 방법
    - 생성자 주입

    ```java
    @Service
    public class OrderService {
    
    		// final로 선언해서 불변성 보장
        private final PaymentService paymentService;
    
        // 의존성 주입을 사용하지 않으면
        // public OrderService() {
        //     this.paymentService = new PaymentService(); // 직접 생성
        // }
        
        // 스프링이 자동으로 컨테이너에 등록된 빈들 중 타입이 PaymentService 인 빈을 찾아서 주입
        public OrderService(PaymentService paymentService) {
            this.paymentService = paymentService;
        }
        
        
        // ...
        
    }
    ```

    ```java
    // PaymentService 정책 변경 시 의존성 주입을 사용하지 않으면
    // public OrderService() {
    //     this.paymentService = new KakaoPayPaymentService(); // 카카오페이로 교체
    // }
    ```

    - PaymentService 정책 변경 시 의존성 주입을 사용하지 않으면 OrderService 클래스 코드를 직접 수정해야 함

    ```java
    @Configuration
    public class AppConfig {
    
        @Bean
        public PaymentService paymentService() {
        // 스프링 컨테이너에 PaymentService 타입 빈에 KakaoPayPaymentService 객체 등록
            return new KakaoPayPaymentService();
        }
    }
    ```

    - DI를 사용하면 AppConfig 클래스만 수정하면 됨

    - 필드 주입

    ```java
    @Service
    public class OrderService {
    
        @Autowired
        private PaymentService paymentService; // 필드에 직접 주입
    
    		// ...
    		
    }
    ```

    - Setter 주입

    ```java
    @Service
    public class OrderService {
    
        private PaymentService paymentService;
    
        @Autowired
        public void setPaymentService(PaymentService paymentService) {
            this.paymentService = paymentService;
        }
    
    		// ...
    }
    ```


## AOP 란?


: Aspect-Oriented Programming (관점 지향 프로그래밍)

- 부가 기능(횡단 관심사)을 핵심 관심 사항(비즈니스 로직)과 분리함

### 주요 개념

- **Target** : 부가 기능을 적용할 대상
- **Aspect**: 부가 기능 모듈 (ex. 로깅)
- **Advice**: 언제 실행할지 정의된 코드 (`@Before`, `@After`, `@Around` , `@AfterThrowing` , `@AfterReturning`)
- **Join Point**: AOP가 적용될 수 있는 지점 (메서드 호출 시점 등)
- **Pointcut**: 실제로 AOP를 적용할 위치를 지정 (ex. `service` 패키지의 모든 메서드)

## PSA 란?


: Portable Service Abstraction

- 서비스 추상화를 통해 여러 기술을 같은 방식으로 사용할 수 있게 해 주는 것
- 구현체가 달라도 같은 방식으로 코드 작성 가능
- ex) JPA(Hibernate), JDBC 어떤 걸 쓰던 상관 없이 추상화된 인터페이스 EntityManager 사용

```java
@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
```

## Spring Boot 란?


: Spring 프레임워크를 더 쉽게 Boot (시동)할 수 있도록 만든 프레임워크

### 전통적인 Spring

- 개발자가 일일이 설정(XML, DispatcherServlet, Bean 등록 등)
- WAS 설치 후 실행
- 의존성 관리 (JPA, JDBC, Security 등)

### Spring Boot

- 자동 설정 (설정, Bean 등록 등 자동화)\
- 내장 WAS (Tomcat 등)
- Starter 의존성으로 관리

## 어노테이션 이란?


: Java 코드가 어떤 메타데이터를 갖는지 프레임워크나 컴파일러에 알려주는 역할 수행

## Spring Bean 이란?


: Spring IoC 컨테이너가 관리하는 객체

- 스프링 컨테이너(ApplicationContext)가 스프링 빈을 생성, 초기화, 소멸까지 관리함
- `@Component`, `@Service`, `@Repository`, `@Controller` 등 붙이면 스프링 빈으로 등록됨
- `@Bean` 붙여도 스프링 빈으로 등록됨
- 
### Bean 등록 과정

1. 컨테이너 초기화
    1. 내부적으로 ApplicationContext (IoC 컨테이너) 생성
2. Component Scan 시작nd
3. 어노테이션 인식
4. BeanDefinition 생성
    1. BeanDefinition 이라는 메타데이터 객체 생성
5. Bean 생성 및 의존성 주입
    1. BeanDefinition 을 바탕으로 실제 Bean 인스턴스 생성
    2. 의존성 주입 처리
6. Bean 초기화
    1. @PostConstruct, afterPropertiesSet 메서드 실행
7. 컨테이너에 Bean 등록 완료
    1. IoC 컨테이너의 BeanFactory에 저장됨

## 같은 인터페이스를 구현한 Service가 여러 개 존재하는 경우


: Spring은 어떤 Service를 주입해야 할지 몰라서 `NoUniqueBeanDefinitionException` 발생

```java
@Service
public class KakaoPayService implements PaymentService {
    public void pay() { System.out.println("카카오페이 결제"); }
}

@Service
public class CardPayService implements PaymentService {
    public void pay() { System.out.println("카드 결제"); }
}

```

- @Primary : 기본 주입 대상에 붙임
- @Qualifier : 주입 시 구체적으로 이름 지정

    ```java
    @Service
    @Qualifier("kakao")
    public class KakaoPayService implements PaymentService { ... }
    
    @Service
    @Qualifier("card")
    public class CardPayService implements PaymentService { ... }
    
    @Service
    public class OrderService {
        private final PaymentService paymentService;
    
        @Autowired // 필요
        public OrderService(@Qualifier("kakao") PaymentService paymentService) {
            this.paymentService = paymentService; // KakaoPayService 주입
        }
    }
    ```

- Bean 이름으로 주입
- 여러 구현체를 한번에 주입 → 런타임에 선택

## 컴포넌트 스캔 이란?


: 스프링이 특정 어노테이션이 붙은 클래스를 자동으로 스프링 빈으로 등록하는 기능

- 스캔 대상 어노테이션
    - `@Component`, `@Service`, `@Repository`, `@Repository`, `@Controller`, `@RestController`

## MVC 패턴 이란?


: Model-View-Controller 패턴

- 소프트웨어 아키텍처 패턴
- 구조
    - Model : 데이터와 비즈니스 로직
    - View : 사용자에게 보여지는 화면 (UI)
    - Controller : 사용자의 요청을 받고 처리 흐름을 제어

## Spring MVC 란?


: MVC 패턴을 실제 웹 프레임워크로 구현해 놓은 구현체

- Spring MVC 흐름
    1. DispatcherServlet (Front Controller) : 모든 요청의 진입점 → 컨트롤러를 찾아 실행
    2. Controller : 요청을 받아 비즈니스 로직 호출, 응답 생성
    3. Model : 데이터 처리
    4. ViewResolver : 어떤 View(HTML, Thymeleaf 등)를 쓸지 결정
    5. View : 최종적으로 사용자에게 응답 화면을 렌더링

## Servlet 이란?


: HTTP 요청을 받아 처리하고, 응답을 만들어 반환하는 자바 클래스

- WAS(Tomcat) 위에서 동작하고, 자바로 동적인 웹 페이지를 만들 수 있게 해줌

### 웹 요청 흐름

1. 클라이언트 요청
    1. HTTP 요청이 생성되어 서버(WAS)로 전송
2. WAS에서 서블릿 찾기
    1. 요청 URL과 매핑된 서블릿 클래스 찾음
3. 서블릿 실행
4. 응답 생성
    1. 서블릿이 HttpServletResponse 객체에 상태 코드, 헤더, HTML/JSON 데이터 작성
    2. WAS가 이를 HTTP 응답 메시지로 변환
5. 클라이언트 응답
    1. 브라우저가 응답을 받아 화면에 렌더링

## WAS 란?


: Web Application Server

= 동적인 웹 애플리케이션 로직(JSP/Servlet, Spring MVC 같은 자바 코드)을 실행해 결과를 클라이언트에게 응답하는 서버

- 정적 리소스 처리
    - HTML, CSS, JS 파일 제공
- 동적 리소스 처리
    - DB와 연동, 로직 수행 후 결과를 동적으로 만들어서 응답함
- Spring Boot 내장 WAS는 Tomcat

## Dispatcher Servlet 이란?


: Spring MVC의 프론트 컨트롤러(Front Controller)

- 모든 HTTP 요청의 진입점 역할을 하는 서블릿
- 요청을 받아서 적절한 컨트롤러로 위임하고, 응답을 만들어 클라이언트에 반환
