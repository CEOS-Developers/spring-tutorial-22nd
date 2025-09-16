# 1주차 미션

---
## 스프링이 지원하는 기술 (IoC/DI, AOP, PSA)

### IoC(Inversion of Control)
IoC는 Inversion of Control을 줄인 표현으로 제어의 역전을 뜻한다. 제어의 역전은 다른 객체를 직접 생성하거나 제어하는 것이 아니라 외부에서 관리하는 객체를 가져와 사용하는 것을 말한다.

아래와 같이 클래스 B의 객체를 직접 생성하는 예시에 IoC를 적용하면 외부에서 제공하는 객체를 b에 할당하는 것을 확인할 수 있다.

- 클래스 B 객체를 직접 생성
  ```java
  public class A{ 
      b = new B();
  }
  ```

- 외부에서 관리하는 객체를 사용
  ```java
  public class A{ 
      private B b;
  }
  ```

---

### DI(Dependency Injection)
DI는 의존성 주입을 뜻하며, 위에서 이야기한 제어의 역전을 구현하기 위해 사용하는 방법이다. DI는 객체가 필요로 하는 다른 객체를 자신이 직접 만드는 것이 아닌, 외부로부터 주입받아 사용하는 디자인 패턴이다. DI를 사용한다면 느슨한 결합으로 인해 각 컴포넌트가 독립적으로 존재해, 하나를 수정해도 다른 곳에 미치는 영향이 적다는 장점이 있다.

의존성을 주입하는 방법은 크게 세 가지가 있지만 그중 가장 추천하는 방법은 생성자 주입(Constructor Injection)이다. 생성자 주입은 객체가 생성되는 시점에 의존성이 주입되어야 하므로, 필수 의존성을 보장한다는 점에서 가장 권장되는 방식이다.

- 생성자를 통한 의존성 주입
  ```java
  public class Car {
      private final Tire tire;
  
      public Car(Tire tire) {
          s.tire = tire;
      }
  }
  ```

---

### AOP(Aspect Oriented Programming)
AOP는 관점 지향 프로그래밍을 뜻하며, 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 것이다. 주로 로깅, 트랜잭션 등 공통적으로 필요한 부가 기능들을 깔끔하게 분리해서 관리하기 위해 사용된다.

공통 관심 사항을 핵심 관심 사항과 분리시켜 핵짐 로직이 깔끔하다, 코드의 가독성과 유지 보수가 용이, 각 모듈에 대한 수정이 필요할 시 해당 로직만 변경하면 된다는 장점이 있다.

- 주요 개념
    - **Aspect**: 관심사를 모듈화 한 것으로, 주로 부가기능을 모듈화
    - **Target**: Aspect를 적용하는 곳(클래스, 메서드)
    - **Advice**: Aspect에서 실질적으로 어떤 일을 해야할 지에 대한 것, 실질적인 부가기능을 담은 구현체
    - **JointPoint**: Advice가 적용될 위치
    - **PointCut**: JointPoint의 상세 기능을 정의
    - **Proxy**: Target에 들어오는 요청을 대신 처리하는 Wrapping 오브젝트, Target 호출 시 프록시가 호출되어, Target 메서드 실행 전에 전처리, 실행 후 후처리를 실행시킬 수 있도록 구성

- 예시
  ```java
  // Aspect class (횡단 관심사)
  @Aspect // AOP 클래스
  @Component // 이는 스프링 빈으로 등록하는 과정
  public class MemberLogging {
      // MemberService 내부 모든 메서드 실행 전 동
      @Before("execution(* com.ceos22.service.MemberService.*(..))")
      public void logBeforeMember() {
          System.out.println("[멤버로그] 멤버 서비스 실행 시작");
      }
  }
  ```

---
### PSA(Portable Service Abstraction)
PSA는 이식 가능한 서비스 추상화를 뜻한다. 쉽게 풀어쓰자면, 세부 기술이 바뀌더라도, 개발자는 똑같은 방법으로 코드를 사용할 수 있게 해주자는 원칙이다.
PSA의 예시로, 우리는 WAS를 톰캣이 아닌 언더토우, 네티와 같은 다른 곳에서 실행해도 기존 코드를 그대로 사용할 수 있다.
---
## Spring Bean

- ### Spring Bean

  스프링 빈은 스프링 IoC 컨테이너가 직접 만들고, 관계를 설정하고, 관리하는 자바 객체를 의미한다. 즉, 스프링 빈은 스프링 컨테이너에서 관리되는 객체를 뜻한다.
- ### Bean의 라이프사이클
  Bean은 컨테이너 안에서 생성 -> 사용 -> 소멸의 생성주기를 따른다. 스프링 빈은 스프링의 IoC 컨테이너에 의해 관리되기 때문에, 스프링 객체의 생명주기 관리는 개발자가 아닌 스프링 컨테이너가 하게된다. 라이프 사이클의 구체적인 단계는 다음과 같다.
    1. 컨테이너 생성: 애플리케이션 시작과 함께 컨테이너가 생성된다.
    2. Bean 생성: 컨테이너는 애플리케이션 설정 정보를 바탕으로 Bean 객체를 생성한다.
    3. 의존성 주입: 컨테이너가 Bean과 Bean 사이의 관계를 연결한다.
    4. 초기화 콜백: 의존성 주입이 끝난 후 Bean이 사용되기 전에 초기화 작업을 수행한다.
    5. Bean 사용: 애플리케이션 코드에서 Bean을 사용한다.
    6. 소멸 전 콜백: 스프링이 종료되기 전, Bean이 소멸되기 전에 호출된다.
    7. 스프링 종료
- ### Bean Scope
  Bean Scope는 스프링 빈이 살아서 내 코드에 영향을 줄 범위를 지정하는 것을 뜻한다, 스프링은 다음과 같은 스코프를 지원한다.
    1. **싱글톤(singleton)**

       기본적으로 스프링의 빈은 싱글톤으로 만들어진다. 싱글톤은 스프링 컨테이너가 시작하면서 종료될 때까지 유지되는 아주 긴 스코프를 뜻한다. 싱글톤 빈은 스프링 컨테이너에서 한 번 생성되며, 여러 클라이언트가 사용을 요청했을 때 만들어놓은 하나의 동일한 빈을 사용할 수 있도록 반환해주는 방식이다. 기본적으로 모든 빈은 스코프를 따로 지정하지 않으면 싱글톤 빈이라고 이해하면 된다.

       싱글톤으로 적합한 객체: 상태가 없는 공유 객체, 읽기용으로만 상태를 가진 공유 객체, 공유가 필요한 상태를 지닌 공유 객체

       비싱글톤으로 적합한 객체: 쓰기가 가능한 상태를 지닌 객체, 상태가 노출되지 않은 객체
    2. **프로토타입(prototype)**

       프로토타입은 싱글톤과 달리 특정 빈에 대한 요청이 있을 때마다 새로운 빈을 생성한다. 따라서 프로토타입 빈 스코프는 컨테이너가 생성, 의존성 주입, 초기화까지만 처리를 해준다. 그렇기에 스프링 빈을 클라이언트에 반환한 이후로는 컨테이너가 따로 관리하지 않기에 소멸 메소드 같은 것들은 모두 클라이언트에서 자체적으로 관리해야 한다.

       프로토타입 스코프 이용이 적합한 경우: 여러 인스턴스를 검색해야 하는 경우, 인스턴스를 지연 혹은 선택적으로 찾아야 하는 경우 등

  아래를 통틀어 **웹 스코프**라고 이야기한다. 웹 스코프는 웹 환경에서만 동작하며, 프로토타입과 달리 스프링이 해당 스코프의 종료 시점까지 관리한다는 특징이 있다.
    3. **request**

       Http 요청 하나가 들어오고 나갈 때까지 유지되는 스코프이다. 각각의 HTTP 요청마다 별도의 빈 인스턴스가 생성되고 관리된다.
    4. **session**

       HTTP Session과 동일한 생명주기를 가지는 스코프이다.
    5. **application**

       서블렛 컨텍스트(ServletContext)와 동일한 생명주기를 가지는 스코프이다.

- ### 어노테이션(Annotation)
  코드 사이에 주석(@)처럼 쓰이며, 프로그램에게 추가적인 정보를 제공해주는 메타데이터라고 볼 수 있다. 여기서 메타 데이터는 컴파일 과정과 런타임에서 코드를 어떻게 컴파일하고 처리할 것인지에 대한 정보를 말한다. 스프링에서는 어노테이션을 활용하여 해당 클래스가 어떤 역할인지 정하기도 하고, 빈을 주입하기도 하는 등 다양한 역할을 수행할 수 있다.

  예시: `@Component`, `@Bean`, `@Controller`

    - 어노테이션의 구성

      어노테이션은 메타 어노테이션(커스텀 어노테이션을 구성할 때 시점, 위치 등을 지정하기 위한 어노테이션)을 사용하여 다음과 같은 구조를 가진다.
      ```java
      //커스텀 어노테이션의 예시
      @Target({ElementType.[적용대상]})
      @Retention(RetentionPolicy.[정보유지되는 대상])
      @Documented
      @Inherited
      @Repeatable
      public @interface [어노테이션명]{
          public 타입 elementName() [default 값]
          ...
      }
      ```
      메타 어노테이션의 종류는 아래와 같다.

      `@Target`: 어노테이션을 적용할 위치 선택

      `@Retention`: 컴파일러가 어노테이션을 다루는 방법을 기술, 어느 시점까지 영향을 미치는지를 결정

      `@Documented`: 해당 어노테이션을 Javadoc에 포함시킨다.

      `@Inherited`: 어노테이션의 상속을 가능하게 한다.

      `@Repeatable`: Java8부터 지원하며, 연속적으로 어노테이션을 선언할 수 있게 한다.

- ### 어노테이션을 통한 Spring의 Bean 등록 과정
  어노테이션을 통한 Bean의 등록 과정은 `@Bean`, `@Configuration`을 이용한 수동 등록, `@Component`를 이용한 자동 등록으로 나눌 수 있다.

    - 수동 등록(`@Bean`, `@Configuration`)

      JyResource란 클래스가 있고 이를 스프링 컨테이너에 등록하고자 할 때 다음과 같이 `@Bean`, `@Configuration` 어노테이션을 이용하여 빈을 등록할 수 있다.(메소드 이름 중복 주의!)
  
      스프링 컨테이너는 `@Configuration`이 붙어있는 클래스를 자동으로 빈으로 등록해두고, 해당 클래스를 파싱해서 `@Bean`이 있는 메소드를 찾아서 빈을 생성해준다. 이때 `@Bean`을 사용하는 클래스에는 반드시 `@Configuration` 어노테이션을 활용하여 해당 클래스에서 빈을 등록할 것임을 명시해야 한다.
      ```java
      @Configuration
      public class ResourceConfig {
    
          @Bean
          public JyResource jyResource() {
              return new JyResource();
          }
      }
      ```

    - 자동 등록(`@Component`)

      아래와 같이 `@Component` 어노테이션을 이용하면 MyComponent 클래스는 스프링 빈으로 자동 등록된다. 우리가 아는 `@Service`, `@RestContoroller` 등의 어노테이션 모두 내부에 `@Component` 어노테이션을 가지고 있기에 자동으로 등록이 된다.

        ```java
      @Component
      public class MyComponent {
      // 필드, 메서드, 생성자 등
      }
      ```

      `@ComponentScan`은 탐색 위치에 `@Component`가 붙은 모든 클래스를 스프링 빈으로 등록한다. 탐색범위의 지정은 `basPackages`(탐색할 패키지의 시작 위치 설정, 해당 패키지부터 하위 패키지까지 탐색), `basePackageClasses`(클래스가 속한 패키지를 탐색 시작 위치로 지정)로 이루어진다. 이때 구체적인 패키지가 정의되지 않았을 경우, `@ComponentScan` 어노테이션이 정의된 클래스의 패키지로부터 수행이되며, 스프링의 경우 일반적으로 `@SpringBootApplication`에 `@ComponentScan`이 포함되기에 `main()` 메소드가 존재하는 패키지부터 스캔을 하게 된다. `@Component`가 붙은 클래스를 발견하면 이 클래스로 객체를 어떻게 만들어야할지 설계하고, 설계를 바탕으로 실제 빈을 생성하고 컨데이너에 등록한다.
      스캔하고 등록하는 과정에서 빈 이름의 중복으로 인한 충돌이 발생할 수 있다. 이때 자동 빈 등록과 자동 빈 등록의 충돌일 경우, `ConflictingBeanDefinitionException` 예외를 발생시킨다. 수동 빈 등록과 자동 빈 등록이 충돌할 경우, 수동 빈 등록이 우선권을 가지게 되어 자동으로 등록된 빈을 오버라이딩 한다.
---

## Spring MVC

- ### MVC 패턴과 Spring MVC
MVC 패턴은 애플리케이션을 개발할 때 사용하는 '디자인 패턴'으로, 애플리케이션의 개발 영역을 `Model`, `View`, `Controller`로 구분하여 각 역할에 맞게 코드를 작성하는 방식을 뜻한다. 이때 모델은 애플리케이션의 데이터 및 비즈니스 로직, 뷰는 사용자 인터페이스 요소, 컨트롤러는 모델과 뷰 사이의 상호동작을 관리한다.

Spring의 MVC는 위의 MVC 패턴을 바탕으로 웹 에플리케이션을 효율적으로 만들 수 있도록 구현한 프레임워크이다.
Servlet과 JSP를 직접 사용하는 기존의 웹 개발 방식의 복잡함을 해결하고, MVC 패턴을 쉽게 적용(DispatcherServlet, 어노테이션, 계층 분리 등등)할 수 있도록 만든 웹 프레임워크라고 할 수 있다.

  - ### Servlet
  servlet은 클라이언트의 요청을 받고, 그에 맞는 동적인 처리를 한 뒤, 결과를 응답하는 웹 컴포넌트이다. 
  서블릿 컨테이너에 의해 관리되며, 스레드로 동작되어 효율적인 요청 처리가 가능하다. MVC 패턴의 Controller 역할로서 서블릿이 사용되며, `HttpServlet` 클래스를 상속 받아 `doGet()`, `doPost()` 같은 메서드로 요청을 처리한다,
  

  웹 요청 처리 과정은 아래와 같다
  1. **클라이언트의 HTTP 요청**
  2. **WAS**

     Servlet Container에게 요청 전달, 정적 파일은 그대로 반환되며 동적 요청은 Servlet으로 전달
  3. **Servlet 동작**

     Servlet Container가 URL 매핑에 맞는 Servlet 클래스 실행

     최초 요청이면 init()으로 초기화 후 객체 생성

     이후 요청마다 service() 실행(내부적으로 doGet(), doPost() 호출)
  4. **비즈니스 로직 처리**

     Servlet이 필요한 Service/Dao 호출하고 DB에서 데이터를 가져와 결과 생성
  5. **응답 반환**

     Servlet이 HttpServletResponse에 결과(HTML, JSON 등)를 담고 WAS가 응답을 클라이언트로 전송
  6. **응답받은 데이터를 렌더링**

  - ### 톰캣, WAS
    - **톰캣**

      톰캣은 Apache Software Foundation에서 개발한 오픈소스 `웹 애플리케이션 서버(WAS)`로, 자바 기반 웹 애플리케이션 실행 환경을 제공한다(Java Servlet, JSP 등). HTTP 요청을 받아 서블릿 컨테이너에서 자바 코드를 실행하고, 결과를 클라이언트에게 돌려준다. 가볍고 설정이 쉬운 특징이 있다.

    - **WAS(Web Application Server)**

      웹 애플리케이션을 실행하고 동적인 웹 페이즈를 제공하는 서버 소프트웨어를 말한다. 클라이언트의 요청을 받아 비즈니스 로직을 실행한 후, DB와 연동해서 결과를 HTML, JSON 등의 형태로 돌려준다. 정적인 파일의 경우 웹 서버에서 처리할 수 있으나, 동적인 콘텐츠는 WAS가 담당한다. 주요 기능으로는 서블릿/JSP 실행 환경 제공, 트랜잭션 관리, 보안, 세션 관리 등이 있다.

  - ### Dispatcher Servlet
    DispatcherServlet은 Spring MVC에서 가장 핵심적인 역할을 하는 컴포넌트로, 웹 애플리케이션에서 모든 요청을 가장 먼저 받는 프론트 컨트롤러다. Dispatcher Servlet은 모든 요청을 가장 먼저 받아 요청을 처리하기 적합한 컨트롤러에게 작업을 위임하는 역할을 수행한다. Dispatcher Servlet을 통해 요청-응답 흐름을 한 곳에서 중앙집중식으로 관리할 수 있으며, 알아서 적절한 컨트롤러에 연결해주기에 개발자는 컨트롤러나 뷰 개발에만 집중하면 된다는 장점이 있다.

- ### Dispatcher Servlet 동작 과정

  1. 요청 수신 및 HttpServlet 변환 
  
     클라이언트의 요청/응답은 `ServletRequest`, `ServletResponse` 형태로 들어온다.

     부모 클래스인 `HttpServlet`의 `service()` 메소드에서 이를 `HttpServletRequest`, `HttpServletResponse`로 변환한다.

  2. HTTP Method에 따른 분기
  
     요청의 HTTP 메서드(GET, POST, PUT, DELETE 등)를 확인한다.

     각 메서드는 `doGet`, `doPost`, `doPut`, `doDelete` 등의 메소드로 연결된다.

     **PATCH 메서드**는 자바 표준 HttpServlet에는 존재하지 않아, Spring의 `FrameworkServlet`이 별도로 처리한다.

  3. 공통 처리 (processRequest)
  
     모든 `doX` 메소드는 결국 `processRequest()` 메소드를 호출한다.

     여기서 로케일 처리, 예외 처리 등 공통 작업을 진행한다.

     이후 핵심 처리 메소드인 `doService()`를 호출한다.

  4. 실제 서비스 로직 실행 (doService)
  
     `DispatcherServlet`은 `doService()`를 오버라이딩하여 실제 요청 처리 로직을 구현한다.

     이 과정에서 요청 로깅, FlashMap 처리 등이 수행된다.

     마지막으로 `doDispatch()`를 호출한다.

  5. 디스패치 단계 (doDispatch)
  
     `HandlerMapping`을 통해 요청 URL과 매핑된 컨트롤러(Handler)를 탐색한다.

     `HandlerAdapter`를 조회하여 해당 컨트롤러를 실행할 방식을 결정한다.

     `HandlerAdapter`가 실제 컨트롤러 메소드를 호출한다.

     컨트롤러는 Service/Repository 등을 거쳐 비즈니스 로직을 수행하고, 결과(`ModelAndView` 또는 응답 객체)를 반환한다.

  6. 뷰 렌더링

     반환된 결과를 `ViewResolver`가 해석하여 JSP, Thymeleaf, JSON 등 실제 뷰 객체를 찾는다.

     `View` 객체가 모델 데이터를 기반으로 렌더링을 수행한다.

  7. 응답 반환

     DispatcherServlet이 렌더링된 응답을 최종적으로 클라이언트에게 전달한다.

     요청-응답 사이클이 종료된다.
