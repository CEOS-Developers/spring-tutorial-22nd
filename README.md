# spring-tutorial-22nd
CEOS 백엔드 22기 스프링 튜토리얼

---

# 1. Spring이 지원하는 기술들
## 1) IoC/DI
### (1) IoC (Inversion of Control)
- xml파일, 어노테이션(@Bean)으로 스프링 컨테이너에 객체를 등록하면 스프링 컨테이너에서 객체의 생명주기를 관리
- 즉, 객체의 제어권이 컨테이너(스프링)으로 바뀌기 때문에 제어의 역전(IoC)라 부름
- IoC가 없다면 개발자가 직접 객체를 제어해야함 (객체 생성, 의존성 설정 등)

<br>

### (2) DI (Dependency Injection)
- 애플리케이션 실행 시점(런타임)에 스프링 컨테이너에서 객체를 생성하여 의존관계 맺는 과정
  - 객체 인스턴스를 생성하고, 그 참조 값을 전달해서 연결하는 과정
- 스프링의 DI 방식
  - DI 없을때
    ```java
    public class GroundService{
        private final Sport sport = new Basketball();
    }
    ```
  - Setter Injection (Setter 주입)
    ```java
    // set메서드를 사용하여 메서드를 public으로 열어둬야하기 때문에 의존성이 어디서든 변경될 가능성이 있다. 
    // 물론 의존성을 변경해야하는 상황도 존재하지만 거의 존재하지 않기때문에 지양한다.
    
    public class GroundService{
        private Sport sport;
    
        @Autowired
        public void setSport(Sport sport){
    	    this.sport=sport;
        }
    }
    ```
  - Field Injection (필드 주입)
    ```java
    // 필드 주입을 사용하면 IDE상에서 Field injection is not recommended라는 에러가 뜨는것처럼 사용을 지양한다.
    // 필드에 @Autowired만 붙히면 자동으로 의존성이 주입되어 깔끔하다는 장점이 있지만 의존성에 접근이 불가하다는 단점이 있다.
    
    public class GroundService{
        @Autowired
        private Sport sport;
    }
    ```
  - Construct Injection (생성자 주입)
    ```java
    // 스프링에서 가장 권장하는 방법
    /*
    생성자 주입의 장점
    1. 객체의 불변성
     : 생성자를 final로 선언할수 있고, 이로인해 setter주입과 같이 런타임시점에 의존성 주입받는 객체가 변경될 일이 없어진다(객체의 불변성).
    2. POJO를 사용하여 테스트 코드 작성
     : 테스트코드를 작성할때 @SpringBootTest, @DataJpaTest와 같이 스프링을 이용하여 테스트 코드를 짤수도 있지만 Java만으로 테스트를 작성할 수 있다는 장점이 있다.
    */
    
    public class GroundService{
        private final Sport sport;
    
        @Autowired
        public GroudService(Sport sport){
            this.sport = sport;
        }
        public void play(){
        }      
    }
    ```

<br><br>

## 2) AOP
### (1) AOP란?
객체지향 프로그래밍 패러다임을 보완하는 기술로, 메소드나 객체의 기능을 **핵심 관심사(Core concern)**와 **공통 관심사(Cross-cutting concern)**로 나누어 프로그래밍하는 것을 말한다.
- 어떤 로직을 기준으로 핵심 관심사와 공통 관심사로 나누고, 각각의 하나의 관점으로 보고 모듈화하겠다는 의미
  - 핵심 관심사 : 비즈니스 로직을 구현하는 과정에서 비즈니스 로직이 처리하려는 목적 기능
  - 공통 관심사 : 각각의 모듈들의 주 목적 외에 필요한 부가적인 기능들(로깅, 동기화, 오류 검사 등)로, 공통적으로 사용되는 기능

<br>

### (2) AOP의 장점
1. 공통 관심 사항을 핵심 관심 사항으로부터 분리시켜 핵심 로직을 깔끔하게 유지할 수 있다.
   - 코드의 가독성과 유지보수성 등을 높일 수 있음
2. 각각의 모듈에 수정이 필요하면 다른 모듈의 수정 없이 해당 로직만 변경하면 된다.
3. 공통 로직을 적용할 대상을 선택할 수 있다.

<br>

## 3) PSA
- 환경의 변화와 관계없이 일관된 방식의 기술 접근 환경을 제공하려는 추상화 구조를 말한다. 
- Spring은 다양한 기술에 추상화를 제공하고 있다.
- Spring Web MVC, Spring Transaction, Spring Cache 등

<br><br><br>

# 2. Spring Bean
## 1) Spring Bean이란?
- @Component, @Configuration, @Bean 등으로 등록된 객체
- 스프링이 직접 생성하고 관리하는 객체
- 등록된 빈은 getBean() 또는 @Autowired 등을 통해 주입됨

> **Annotation**
> - 코드 사이에 주석처럼 쓰이며 특별한 의미, 기능을 수행하도록  하는 기술이다. 즉, 자바 소스 코드에 추가하여 사용할 수 있는 메타데이터의 일종
> - Annotation 자체는 아무런 동작을 가지지 않는 단순한 표식일 뿐이지만, Reflection을 이용하면 Annotation의 적용 여부와 엘리먼트 값을 읽고 처리할 수 있다.
> - Reflection : 객체를 통해 클래스의 정보를 분석해내는 프로그램 기법이다. 자바의 API. 프로그램이 실행 중에 자신의 구조와 동작을 검사하고, 조사하고, 수정하는 것. Reflection을 사용하면 컴파일 타임에 인터페이스, 필드, 메소드의 이름을 알지 못해도 실행 중에 클래스, 인터페이션, 필드 및 메소드에 접근할 수 있다.

<br><br>
## 2) Bean 등록 방식
스프링 프로젝트를 생성하면 기본적으로 Application 클래스가 존재한다.

클래스에는 @SpringBootApplication가 붙어 있고, 해당 어노테이션의 메타 어노테이션으로 @ComponentScan이 포함되어 있어 @Component 계열 클래스들을 자동으로 컨테이너에 등록해준다.
@Configuration 역시 메타 어노테이션으로 @Component가 선언되어 있어 설정 클래스 자체가 빈으로 등록된다.

- @Component : 자동 등록 (컴포넌트 스캔 대상)
- @Bean : 수동 등록 (설정 클래스 내에서 직접 정의)
- @Configuration : 설정 클래스 지정 (내부적으로 @Component 포함)

<br><br>

## 3) Bean의 라이프사이클
스프링 IoC 컨테이너 생성 → 스프링 빈 생성 → 의존관계 주입 → 초기화 콜백 메서드 호출 → 빈 사용 → 소멸 전 콜백 메서드 호출 → 스프링 종료

<br><br>

## 4) Bean Scope
### (1) 스코프란?
> 빈이 생성되고 유지되는 범위를 지정하는 설정

<br>

### (2) 주요 스코프 종류
- singleton(기본값)
    - 컨테이너당 하나의 객체만 생성
    - 모든 요청에 재사용됨

![img2.png](https://github.com/URECA-BE-Study/URECA-CS-Study/raw/main/%EC%8A%A4%ED%94%84%EB%A7%81/IoC%EC%99%80DI_image/img2.png)

- prototype
    - 요청 시마다 새로운 객체 생성
      생성 이후 생명주기는 스프링이 관리하지 않음

![img3.png](https://github.com/URECA-BE-Study/URECA-CS-Study/raw/main/%EC%8A%A4%ED%94%84%EB%A7%81/IoC%EC%99%80DI_image/img3.png)

- request
    - HTTP 요청마다 별도 객체 생성
- session
    - HTTP 세션마다 객체를 유지
- application
    - 서블릿 컨텍스트 범위에서 객체 유지

<br><br><br>

# 3. Spring MVC
## 1) MVC 패턴과 Spring MVC
- MVC 패턴 : MVC 패턴은 UI 와 비즈니스 로직을 분리시키기 위해 고안된 디자인 패턴, Model/View/Controller로 분리
- Spring MVC : MVC 디자인 패턴에 기반해 웹 어플리케이션을 만들기 위한 Spring 기능 중 하나

<br><br>

## 2) Servlet
### (1) Servlet이란?
- 클라이언트의 요청을 처리하고 그 결과를 반환하는 Servlet클래스의 구현 규칙을 지킨 자바 웹 프로그래밍 기술
- 서블릿이 요구하는 구현 규칙을 지켜주며 서블릿을 정의해주면 HTTP 요청 정보를 쉽게 사용할 수 있고, 처리 결과를 쉽게 응답으로 변환할 수 있음.

![](https://velog.velcdn.com/images%2Ffalling_star3%2Fpost%2Fea1d422a-c7d1-476f-b6b6-cf308320f4ce%2F%EC%BA%A1%EC%B2%98.png)

<br>

### (2) Servlet 특징
- 클라이언트의 요청에 대해 동적으로 작동하는 웹 애플리케이션 컴포넌트
- 주로 HTML 사용하여 응답함 (XML, JSON, 일반 텍스트 모두 가능)
- JAVA의 스레드를 활용하여 동작
- HTTP 프로토콜 서비스를 지원하는 javax.servlet.http.HttpServlet 클래스를 상속받음
- Spring MVC에서 컨트롤러로 사용될 수 있음 (DispatcherServlet)
- 보안 기능 적용하기 쉬움

<br>

### (3) Servlet 동작 과정
![](https://velog.velcdn.com/images%2Ffalling_star3%2Fpost%2F4fabf50a-d3d7-4391-8eb5-0cb436379d71%2Fimage.png)

1. 클라이언트 요청
2. HttpServletRequest, HttpServletResponse 객체 생성
3. Web.xml이 어느 서블릿에 대해 요청한 것인지 탐색
4. 해당하는 서블릿에서 service() 메소드 호출
5. doGet() 또는 doPost() 호출
6. 동적 페이지 생성 후 ServletResponse 객체에 응답 전송
7. HttpServletRequest, HttpServletResponse 객체 소멸

<br>

**정적 웹 페이지와 서블릿 요청의 비교**

정적 웹 페이지 :
- 브라우저가 요청하면 WEB에서는 저장된 페이지를 제공함

동적 웹 페이지 :
- 과거 정적 웹페이지에서 업그레이드되면서 연산기능을 가지게됨
- 과거에 서버라 불리던 것은 **WEB**과 **WAS(Web Application Server)**로 나뉘게됨
- WEB 서버에서는 사용자 요청에 따라 정적 페이지를 제공하고, WAS는 사용자 요청 중에 '연산'이 필요한 부분만 맟아 결과를 제공함
- WAS는 연산 결과를 WEB서버에 제공, WEB서버는 정적 페이지를 만들어 사용자에게 전달함
  <br>이때 WAS에 연산을 담당하는 것이 **'Servlet'**
  <br>(서블릿은 WAS안에 있는 서블릿 컨테이너 혹은 웹 컨테이너 공간에서 활동함)

<br>

### (4) Servlet 생명 주기
- 서블릿 로딩부터 소멸될 때까지의 수명

![](https://velog.velcdn.com/images/hayong39/post/28ede403-a49a-4534-81dc-8edd8c333571/image.png)


1. 서블릿 로딩
2. 서브릿 인스턴스 생성
3. init() 메소드 호출
4. 각 클라이언트 요청에 대해 반복적으로 service() 메소드 호출
5. 소멸하기 위해 destroy() 메소드 호출

<br><br>

## 3) WAS와 톰캣
### (1) WAS (Web Application Server)
1. 개념
- DB 조회 및 다양한 로직 처리를 요구하는 동적 컨텐츠를 제공하기 위해 만들어진 Application Server다.
- HTTP를 통해 컴퓨턴자 장치에 어플리케이션을 수행해주는 미들웨어이다.
- 웹 컨테이너, 서블릿 컨테이너라고도 불린다. 웹 컨테이너란 JSP, Servlet를 실행할 수 있는 소프트웨어로, 웹서버에서 JSP를 요청하면 톰캣에서는 JSP파일을 서블릿을 변환하여 컴파일을 수행하고, 서블릿 수행결과를 웹 서버에게 전달한다.  JSP 컨테이너가 탑재되어 있는 WAS는 JSP페이지를 컴파일해 동적인 페이지를 생성한다. 즉, WAS는 JSP, Servlet 구동환경을 제공한다.

> **JSP**
> - HTML 코드에 JAVA코드를 넣어 동적 웹페이지를 생성하는 웹어플리케이션 도구
> - JSP가 실행되면 서블릿으로 변환되어 웹 어플리케이션 서버에서 동작되며 필요한 기능을 수행한다.
> - 위를 통해 생성된 데이터를 웹 페이지와 함께 클라이언트로 응답한다.

2. 역할
- Web Server기능들을 구조적으로 분리해 처리하는 목적으로 제시되었다.
- 현재 WAS가 가지는 Web Server도 정적 컨텐츠를 처리하는데 성능상 큰 차이가 없다.

3. 기능
- 프로그램 실행 환경과 데이터베이스 접속 기능 제공
- 여러 개의 트랜잭션 관리
- 업무를 처리하는 비즈니스 로직 수행
- Web Service 플랫폼으로서의 역할

4. 필요성
- 자원의 효율적 사용 : 요청에 맞는 데이터를 DB에서 가져와 비즈니스 로직에 맞춰 그때 그때 결과를 제공한다.

5. 예시
- Tomcat, Jetty, Undertow, Boss, Jeus, Web Sphere

> 위의 예시들은 자바 진영에서 사용하는 WAS로, 자바 외의 다른 진영에서는 WAS 역할을 명확히 구분해놓지 않고 있다.

6. Web Server과 WAS의 분리 이유
- 기능 분리로 서버 부하 방지
- 물리적 분리로 보안강화
- 여러 대의 WAS 연결이 가능하므로 로드밸런싱, fail over, fail back 처리에 유리
- 여러 웹 어플리케이션 서비스 가능

<br>

### (2) Tomcat
1. 개념
- Apache 재단에서 개발한 서블릿 컨테이너(WAS)
- JSP, 자바 서블릿과 같은 자바 기술을 실행하고 관리하는 데 사용

2. 구성
- Coyote(HTTP Connector) : 클라이언트와 서버간의 통신 처리를 하고 HTTP 프로토콜을 지원하는 톰캣의 HTTP Connector 역할을 담당한다. 아파치 웹서버와 함께 정적 파일 서비스한다.
- Catalina(Servlet Container) : JSP, Java Servlet을 호스팅하는 환경을 제공하고 서블릿의 라이프사이클을 관리하는 톰캣의 Servlet Container 역할을 담당한다.
- Jasper(JSP Engine) : JSP 페이지를 서블릿으로 변환하고 실행하는 역할을 하는 톰캣의 JSP 엔진을 담당한다. 클라이언트가 JSP페이지에 접근하면 이를 서블릿 코드로 전환하고 컴파일 후 실행한다. 

3. 동작

**1. 클라이언트 -> Coyote**
- 클라이언트가 HTTP 요청을 보내면 톰캣의 Coyote 커넥터가 수신한다.

**2. Coyote -> Catalina**
- 수신된 HTTP 요청은 톰캣의 Catalina 서블릿 컨테이너로 전달된다.

**3. Catalina -> Context**
- Catalina는 요청 URL과 설정파일(ex. WEB-INF/web.xml 등)을 참고하여 어떤 Context가 해당 요청을 처리할지 결정하고, 그 Context로 요청을 전달한다.

**4. Context -> 요청 처리(서블릿/필터 등)**
- Context내에서는 요청 URL 매핑에 따라 특정 서블릿 또는 필터가 선택된다.
- 만약 요청 대상이 JSP페이지라면, Context는 JSP요청을 해당 JSP파일과 매핑된 서블릿으로 연결한다.

**5. JSP요청 시, Jasper엔진에 의한 변환 및 컴파일**
- JSP 파일에 대한 요청인 경우, 톰캣은 먼저 해당 JSP가 이미 서블릿으로 변환되어 컴파일되어 있는지 확인한다.
- 최초 요청이거나 JSP파일이 변경된 경우 : Jasper(JSP Engine)가 JSP파일을 읽어 파싱한다. 파싱 결과를 기반으로 자바 서블릿 소스 코드를 생성한다. 생성된 자바 소스 코드를 컴파일하여 실행 가능한 서블릿 클래스(.class)를 생성한다. 그 후, 생성된 서블릿 클래스가 메모리에 로드되어 초기화되고, JSP의 역할을 대신한다.

**6. 서블릿(또는 변환된 JSP 서블릿)의 요청 처리**
- 선택된 서블릿(또는 변환된 JSP 서블릿)이 클라이언트의 요청을 처리한다.

**7. 응답 생성 및 전송**
- 서블릿이 생성한 응답 데이터는 HttpServletResponse객체에 작성된다.
- 이 응답은 다시 톰캣의 Coyote 커넥터로 전달되어, HTTP프로토콜에 맞게 포맷팅된다.
- 최종적으로, Coyote가 해당 응답을 네트워크를 통해 클라이언트로 전송한다.

<br><br>

## 4) Dispatcher Servlet
### (1) Dispatcher Servlet이란?
- 정의 :
  - 클라이언트의 HTTP 요청을 가장 먼저 받아 적합한 컨트롤러에 위임하는 프론트 컨트롤러
- 주요 역할 :
  - 모든 HTTP 요청의 공통 작업(요청 분석, 모델 준비, 뷰 선택 등)을 처리한다.
  각 요청에 맞는 컨트롤러로 요청을 위임한다.
- 효과 :
  - 모든 요청을 한 곳에서 관리하므로, 여러 서블릿을 별도로 관리할 필요가 없어진다.
- 상속 구조 :
  - DispatcherServlet ➡ FrameworkServlet ➡ HttpServletBean ➡ HttpServlet ➡ GenericServlet ➡ Servlet

<br>

### (2) Dispatcher Servlet 동작 과정
![](https://github.com/URECA-BE-Study/URECA-CS-Study/raw/main/%EC%8A%A4%ED%94%84%EB%A7%81/DispatcherServlet_images/img_1.png)

1. **요청 수신:** 클라이언트의 HTTP 요청이 DispatcherServlet에 도착합니다.

2. **핸들러 매핑:** 요청 URL에 따라, 적절한 컨트롤러를 조회합니다.

3. **핸들러 어댑터 조회:** 조회한 컨트롤러를 실행할 수 있는 핸들러 어댑터를 찾습니다.

4. **핸들러 실행:** 선택된 어댑터를 통해 실제 컨트롤러 메서드를 호출합니다.
    - 컨트롤러는 보통 `ModelAndView` 객체를 반환합니다.

5. **뷰 처리:** 반환된 View 이름을 기반으로 ViewResolver가 적합한 View를 찾고,
   모델 데이터를 View에 전달하여 최종 응답을 생성합니다.

6. **응답 전송:** 최종 생성된 View 또는 응답 데이터가 클라이언트에 전송됩니다.

<br>

### (3) Dispatcher Servlet 동작 과정 (@RestController)
Spring MVC에서 @RestController를 사용할 때의 요청 처리 과정을 단계별로 설명합니다.

1. **요청 수신:**  
   클라이언트의 HTTP 요청이 DispatcherServlet에 도착합니다.

2. **핸들러 매핑:**  
   요청 URL 및 HTTP 메서드에 따라, 적절한 컨트롤러(즉, @RestController의 메서드)를 조회합니다.

3. **핸들러 어댑터 조회:**  
   조회한 컨트롤러를 실행할 수 있는 핸들러 어댑터를 찾습니다.

4. **핸들러 실행:**  
   선택된 어댑터를 통해 실제 컨트롤러 메서드를 호출합니다.
    - @RestController의 메서드는 보통 `ModelAndView` 대신 객체(예: DTO, Map, List 등)를 반환합니다.

5. **응답 처리 (HttpMessageConverter):**  
   반환된 객체는 HttpMessageConverter에 의해 자동으로 직렬화되어,  
   클라이언트가 요청한 미디어 타입(보통 JSON 또는 XML)으로 변환됩니다.

6. **응답 전송:**  
   직렬화된 데이터가 HTTP 응답 본문에 작성되어 클라이언트에 전송됩니다.
