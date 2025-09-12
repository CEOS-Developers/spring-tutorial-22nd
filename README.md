# 1주차 과제

## Spring MVC 심층분석

---

### 1. Tomcat과 WAS는 무엇일까요 ?

우선 Web Server에 대해서 알아보겠습니다.

Web Server는 인터넷을 기반으로 클라이언트에게 웹 서비스를 제공하는 컴퓨터입니다.
클라이언트가 웹 서버의 주소로 요청을 하면 웹 서버는 요청에 대한 응답을 만들어서 응답합니다.
하지만 Web Server는 비즈니스 로직 처리, DB 접근 등을 하지 못하고, 동적인 컨텐츠만 반환할 수 있습니다.

이를 해결하기 위해 등장한 것이 WAS (Web Application Server)입니다 !

WAS는 Web Application을 실행하여 필요한 기능을 수행하고 그 결과를 WebServer에게 전달하는 일종의 미들웨어입니다. 

WebServer에게 들어오는 요청 중 동적 컨텐츠를 요청 받으면 WAS에게 해당 요청을 넘기고, WAS가 해당 요청을 처리한 결과를 WebServer에게 넘겨줍니다. WAS는 동적인 컨텐츠를 제공하기 위해서 JSP, Servlet 구동환경을 제공하는 Web Container를 포함합니다.

정적인 컨텐츠까지 WAS에서 처리하게 되면 서버 부하가 커지고 응답 지연이 길어지며, WebServer에서 바로 처리할 수 있는 컨텐츠들은 WebServer가 바로 처리하는 것이 안정적입니다. 이런 이유로 WebServer와 WAS를 함께 사용하게 됩니다.

대표적인 WAS으로 Tomcat이 있습니다. 구체적으로는 Servlet Container라는 자바 표준 기술을 구현한 구현체입니다. 최근에는 Tomcat보다 웹 서버 Apache의 이름을 합친 Apace Tomcat이라고 부르고 있는데, Tomcat 5.5 버전부터 Tomcat에서 정적 컨텐츠를 처리하는 기능이 추가되었고 이 기능의 성능이 순수 Apache를 사용하는 것과 성능적 차이가 거의 없어 Tomcat이 내부적으로 Apache를 포함하게 되었습니다. 

---

### 2. Servlet은 무엇일까요 ?

Tomcat이 구현하는 Servlet은 무엇인지 알아보겠습니다.

Servlet은 사용자의 동적 컨텐츠 요청을 처리하고, 결과를 반환하는 자바 프로그램입니다.

- Servlet의 동작방식
1. 사용자가 URL을 입력하면 HTTP Request가 Servlet Container으로 전달됩니다.
2. ServletContainer는 HttpServletRequest, HttpServletResponse를 생성합니다.
3. 사용자가 요청한 URL이 어느 Servlet에 대한 요청인지 찾습니다.
4. 해당 Servlet에서 Service 메소드를 호출한 후, doGet(), doPost()를 호출합니다.
5. doGet(), doPost()는 동적 페이지를 생성한 후, HttpServeltResponse 객체에 응답을 보냅니다.
6. 응답이 끝나면 HttpServletRequest, HttpServletResponse 객체를 소멸시킵니다.

이러한 Servlet을 관리해주는 Container가 Servlet Container입니다 ! ( = Tomcat ) 

- ServletContainer의 역할
1. 웹서버와의 통신 지원
2. Servlet 생명주기 관리
3. 멀티스레드 지원 및 관리

---

### 3. MVC 패턴 SpringMVC

: 애플리케이션의 아키텍처를 세 가지 핵심 컴포넌트로 분리하여 개발하는 디자인 패턴입니다.

각 컴포넌트는 독립적인 역할을 수행하고, 이를 통해 개발과 유지보수의 효율성을 극대화 할 수 있습니다.

1. Model : 애플리케이션의 데이터와 데이터를 처리하는 비즈니스 로직 담당
- 데이터베이스와의 상호작용, 데이터 처리 및 유효성 검사
- Model은 독립적으로 작동하며 뷰/컨트롤러와 직접 통신하지 않습니다.

1. View : 애플리케이션의 UI 부분
- Model에서 데이터를 받아 사용자에게 표시하는 역할
- Model이 가지고 있는 데이터를 미리 저장하면 안됩니다.

1. Controller : 사용자 입력을 처리하고, 애플리케이션 흐름을 제어
- View에서 전달된 사용자 입력을 분석하고, 적절한 Model을 호출하여 데이터를 조작/변경
- 결과를 다시 View에 전달합니다.

MVC의 상호작용

1. 사용자가 애플리케이션에서 작업을 수행
2. View가 사용자의 입력을 감지하고, Controller에게 전달
3. Controller는 적절한 Model을 찾아 호출
4. Model은 데이터와 관련된 비즈니스 로직을 수행, 작업이 완료되면 Controller에게 전달
5. Controller는 Model의 결과를 받아 View에게 전달
6. View는 이 데이터를 이용하여 사용자에게 보여지는 화면을 업데이트

MVC 패턴의 장점

1. 컴포넌트 역할의 분리로 결합도를 낮출 수 있습니다
2. 코드의 재사용성 및 확장성을 높일 수 있습니다
3. 서비스의 유지보수성과 테스트 용이성이 좋아집니다

Spring MVC

Spring MVC는 MVC 디자인 패턴을 구현하고, Front Controller를 사용한다는 점에서 순수 MVC 패턴과 차이가 있습니다.

순수 MVC에서는 각 컨트롤러가 사용자의 요청을 받는 과정이 필요한데, 모든 컨트롤러마다 요청을 받는 과정이 중복됩니다. 이를 해결하기 위해 SpringMVC에서는 모든 요청을 받는 FrontController를 도입했습니다.

FrontController는 모든 요청을 받아 적절한 Controller을 찾아 호출해줍니다.

---

### 4. Dispatcher Servlet

Spring MVC의 FrontController 패턴을 구현하는 DispatcherServlet에 대해 알아보겠습니다.

DispatcherServlet은 모든 HTTP Request을 매핑된 Controller으로 전달하는 역할을 수행하는 중앙 서블릿입니다. 추가적으로 요청에 대한 Controller을 찾지 못하면 정적인 요청으로 인식하고, 설정된 자원 경로를 탐색합니다. 

- DispatcherServlet의 동작 과정
1. 클라이언트이 요청을 DispatcherServlet이 받음
    - 서블릿 컨텍스트에서 Filter을 통과하고 가장 먼저 DispatcherServlet이 요청을 받음
    - 서블릿 컨텍스트 : 컨테이너가 할당하는 웹 애플리케이션 1개가 사용하는 웹 애플리케이션의 공간
2. HandlerMapping : 요청 정보를 통해 위임할 컨트롤러를 찾음
    - 최근에는 컨트롤러에 @RequestMapping 어노테이션을 사용하기 때문에 이를 기반으로 요청 매핑 정보를 관리하고, 요청이 왔을 때 컨트롤러를 찾아주는 RequestMappingHandlerMapping를 사용
3. HandlerAdapter : 
    - 다양한 컨트롤러 구현 방식에 대응하기 위해 DispatcherServlet이 요청을 직접 컨트롤러에게 전달하지 않고, HandlerAdapter라는 어댑터 인터페이스를 통해 어댑터 패턴을 적용합니다.
    - HandlerAdapter가 컨트롤러로 요청을 위임한 전/후로 공통적인 전/후처리 과정이 일어납니다
        - @RequestParam, @RequestBody를 처리하기 위한 ArgumentResolver
        - ResponseEntity의 Body를 Json으로 직렬화하는 ReturnValueHandler …
4. 비지니스 로직 처리
5. 컨트롤러가 반환값을 반환
6. HandlerAdapter가 반환값을 처리
    - 컨트롤러가 ResponseEntity  반환 : 응답 상태 설정, 응답 객체 직렬화
    - 컨트롤러가 View 이름을 반환 : View Resolver을 통해 View를 반환
7. 서버의 응답을 클라이언트로 반환
    - 다시 서블릿 컨텍스트의 필터들을 거쳐 클라이언트에게 반환됨
    - 응답이 데이터라면 그대로 반환되지만, 응답이 View라면 ViewResolver가 적절한 화면을 반환

- Dispatcher Servlet의 요청을 Controller으로 전달하는 세부 과정
1. Request/Response를 HttpServletRequest/HttpServletResponse으로 캐스팅

```java
public abstract class HttpServlet extends GenericServlet {

    ...

    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {
				// -> 파라미터가 ServletRequest, ServletResponse
				
        HttpServletRequest  request;
        HttpServletResponse response;

        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException(lStrings.getString("http.non_http"));
        }
        service(request, response); // -> FrameworkServlet.service() 호풀
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        ...
    }
    
    ...
}
```

---

1. HTTP Method에 따른 처리 작업

```java
public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware {

    ...

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

				HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
				if (httpMethod == HttpMethod.PATCH || httpMethod == null) {
			    processRequest(request, response);
				}
				else {
			    super.service(request, response); // -> HttpServlet.service()
				}
	
	...
    }
}
```

- 과거 HTTP 표준을 따른 HttpServlet에는 PATCH가 구현되어 있지 않아서 PATCH 요청을 처리하고자 Spring에서는 FrameworkServlet에서 PATCH 요청을 핸들링하고, 그렇지 않은 요청은 HttpServlet이 처리하도록 설계하였습니다.

---

1. PATCH가 아닌 요청에 대한 처리

```java
public abstract class HttpServlet extends GenericServlet {

    ...

    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        
        // 파라미터가 HttpServletRequest, HttpServletResponse

        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            ... // lastModifed에 따른 doGet 처리(요약함)
            doGet(req, resp);

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req,resp);

        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req,resp);

        } else {
            ... // 에러 처리
        }
    }

    ...
}
```

- HttpMethod에 따라 FrameworkServlet에서 오버라이딩된 doX 메서드를 호출

❓그냥 처음부터 FrameworkServlet에서 처리하면 되는거 아님?

```java
public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware {

    ... 

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
    
    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    ...
}
```

---

1. 요청에 대한 공통 처리 작업

```java
protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

    ... // LocaleContextHolder 처리 등 생략

    try {
        doService(request, response); // ***********
    } catch (ServletException | IOException ex) {
        failureCause = ex;
        throw ex;
    } catch (Throwable ex) {
        failureCause = ex;
        throw new NestedServletException("Request processing failed", ex);
    } finally {
        ... // 후처리 진행
    }
}

protected abstract void doService(HttpServletRequest request, HttpServletResponse response)
        throws Exception;
```

- 공통 작업을 한 후에 DispatcherService의 doService() 호출

---

1. 컨트롤러로 요청을 위임

```java
public class DispatcherServlet extends FrameworkServlet {

    ...

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logRequest(request);

        ... // flash map 등의 처리 진행

        try {
            doDispatch(request, response); // ****************
        } finally {
            ... // 후처리 진행
        }
    }

    ...

}
```

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    boolean multipartRequestParsed = false;

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

    try {
        ModelAndView mv = null;
        Exception dispatchException = null;

        try {
            processedRequest = checkMultipart(request);
            multipartRequestParsed = (processedRequest != request);

            // 1. 요청에 매핑되는 HandlerMapping (HandlerExecutionChain) 조회
            mappedHandler = getHandler(processedRequest);
            if (mappedHandler == null) {
                noHandlerFound(processedRequest, response);
                return;
            }

            // 2. 요청을 처리할 HandlerAdapter 조회
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

            ...

            // 3. HandlerAdapter를 통해 컨트롤러 메소드 호출(HandlerExecutionChain 처리)
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

            ... // 후처리 진행(인터셉터 등)
        } catch (Exception ex) {
            dispatchException = ex;
        } catch (Throwable err) {
            // As of 4.3, we're processing Errors thrown from handler methods as well,
            // making them available for @ExceptionHandler methods and other scenarios.
            dispatchException = new NestedServletException("Handler dispatch failed", err);
        }
        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    } catch (Exception ex) {
        triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
    } catch (Throwable err) {
        triggerAfterCompletion(processedRequest, response, mappedHandler,
            new NestedServletException("Handler processing failed", err));
    } finally {
        ... // 후처리 진행
    }
}
```

---

---

## Spring Bean

---

### 1. 어노테이션이란 ?

어노테이션은 주석처럼 프로그래밍 언어에 영향을 주지 않으면서 미리 약속된 형식으로 다른 프로그램에게 유용한 정보를 제공하는 메타데이터입니다.

```java
@Override
public String toString() {
    return "This is an object.";
}
```

@Override 어노테이션은 해당 메서드가 부모 클래스의 메서드를 재정의했다는 사실을 컴파일러에게 알려줍니다. 
이 어노테이션이 없어도 잘 작동하지만, 부모 클래스에 해당 메서드가 없거나 오타가 있다면 컴파일러는 @Override를 보고 재정의할 대상이 없다고 컴파일 시점에 알려줍니다. 

이처럼, 어노테이션은 컴파일러, 프레임워크, 또는 다른 프로그램에게 정보를 제공하는 역할을 합니다.

예시를 통해 어노테이션이 구현되는 방법을 알아보겠습니다.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 3. 메타 어노테이션 설정
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
// 1. @interface 키워드로 어노테이션 선언
public @interface MyTask {

    // 2. 속성 정의
    String name(); // name 속성. 반드시 값을 지정해야 함
    int priority() default 5; // priority 속성. 기본값은 5
}
```

1. 어노테이션 선언 @interface
    
    → 위 예시의 경우 @MyTask()으로 어노테이션이 등록됩니다.
    
2. 속성 정의
    - 타입 속성이름() 형태로 선언합니다.
    - default 키워드를 통해 어노테이션 사용 시 해당 속성을 생략할 수 있습니다.
    
    → 위 예시의 경우 @MyTask(name = "Validation") 
    
                             @MyTask(name = "Migration", priority = 10) 형식으로 사용합니다.
    
3. 메타 어노테이션 설정
    - @Target : 어노테이션이 어디에 붙을 수 있는지 정의합니다.
        - ElementType.PACKAGE : 패키지 선언
        - ElementType.TYPE : 타입 선언
        - ElementType.ANNOTATION_TYPE : 어노테이션 타입 선언
        - ElementType.CONSTRUCTOR : 생성자 선언
        - ElementType.FIELD : 멤버 변수 선언
        - ElementType.LOCAL_VARIABLE : 지역 변수 선언
        - ElementType.METHOD : 메서드 선언
        - ElementType.PARAMETER : 전달인자 선언
        - ElementType.TYPE_PARAMETER : 전달인자 타입 선언
        - ElementType.TYPE_USE : 타입 선언
- @Retention : 어노테이션의 정보를 언제까지 유지할지 지정합니다.
    - RetentionPolicy.SOURCE: 소스 코드에만 존재하고, 컴파일 시 사라집니다. 컴파일러에게 정보를 주기 위해 사용합니다
    - RetentionPolicy.CLASS: 컴파일된 클래스 파일(.class)까지는 남지만, 런타임에는 사라집니다.
    - RetentionPolicy.RUNTIME: 런타임 시점까지 정보가 유지됩니다.

→ 위 예시의 경우 어노테이션을 런타임 시점까지 유지하고, 클래스와 메서드를 선언하는 지점에 붙일 수 있습니다.

### 2. 리플렉션을 통한 런타임 어노테이션 활용

리플렉션은 클래스나 멤버에 대한 정보를 런타임에 조사하고, 정보를 바탕으로 객체를 생성하거나 메서드를 호출하는 동적인 작업을 할 수 있게 해주는 기능입니다.

리플렉션을 통해 클래스에 정의된 메서드, 필드, 생성자 정보를 가져오고
isAnnotationPresent()을 통해 어노테이션이 적용되어 있는지 확인합니다.

메서드의 경우 invoke()를 통해 메서드를 실행할 수 있습니다.

---

### 3. Spring Bean

스프링 빈은 IoC 컨테이너가 생성하고, 생명주기를 관리하고, 의존성을 주입해주는 객체입니다.

@Component, @Service, @Controller 같은 어노테이션을 클래스에 붙여주면, Spring 컨테이너는 이 어노테이션을 보고 해당 클래스의 객체를 컨테이너에 보관합니다. 이후 필요한 위치에 빈을 컨테이너가 주입해줍니다.

### 4. BeanScope

빈 스코프는 빈이 존재할 수 있는 범위를 뜻합니다.

1. 싱글톤 : 디폴트 스코프로, 컨테이너의 시작부터 종료까지 유지되는 가장 넓은 범위의 스코프
2. 프로토타입 : 컨테이너가 빈의 생성과 의존성 주입까지만 관여하고 더는 관리하지 않는 짧은 범위의 스코프
3. 웹 관련 스코프
    1. request : 웹 요청이 들어오고 나갈 때 까지 유지되는 스코프
    2. session : 웹 세션이 생성되고 종료될 때까지 유지되는 스코프
    3. application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프 

빈의 스코프는 @Scope(”singleton”) 을 통해 지정할 수 있습니다. 

### 5. Bean LifeCycle

IoC 컨테이너 생성 → 스프링 빈 생성 →  의존관계 주입 → 초기화 콜백→ 사용 → 소멸 전 콜백→ 스프링 종료

- 초기화 콜백 (init) : @PostConstruct, 의존성 주입 완료된 후 호출
- 소멸 전 콜백(destroy) : @PreDestroy, 빈 소멸 직전 호출

→ @Bean(initMethod = "...", destroyMethod = "…”) 의 방법으로 내부 코드를 수정할 수 없는 경우 사용

### 6. 빈 등록 과정

1. @SpringBootApplication.run() 호출
2. ApplicationContext 생성
    - SpringBootApplication.run()은 ApplicationContext를 생성하고 초기화합니다.
    - 이 컨텍스트는 빈의 정의, 생성, 관리를 담당합니다
3. @SpringBootApplication의 @ComonentScan
    - 이 어노테이션을 통해 지정된 패키지 내에서 컴포넌트를 스캔하고 빈으로 등록합니다.
4. ClassPathBeanDefinitionScanner
    - 이 클래스는 클래스 패스를 스캔하여 빈 정의를 찾고 등록하는 역할을 합니다
    - 빈 정의는 IoC 컨테이너가 관리하는 빈의 메타데이터를 담고 있습니다.]
5. BeanFactory에서 생성자 찾기
    - 빈을 생성하기 전에 빈의 생성자를 결정해야합니다
    - 이 과정에서 빈의 생성자와 빈에 주입할 의존성을 찾아냅니다.
    - 실제로는 BeanFactory의 구현체인, DefaultListenableBeanFactory에서 이루어집니다.
6. 생성자를 통한 빈 인스턴스 생성 및 주입
    - 생성자를 통한 인스턴스 생성 및 주입은 AbstractAutowireCapableBeanFactory에서 처리됩니다
    - 이 클래스는 DefaultListenableBeanFactory에 의해 사용됩니다.
    - 빈의 생성과 의존성 주입 초기화를 관리합니다.
7. autowireConstructor 메서드를 통해 생성자 주입을 수행
    - 빈의 생성자를 찾고 필요한 의존성들을 ConstructorResolver 클래스에 선언된 resolveConstructorArguments 메서드를 통해 해결하고 이들을 생성자를 통해 주입하면서 빈 인스턴스를 생성한다.
8. 빈의 생성 및 의존성 주입이 완료됩니다.
    - 애플리케이션에서 빈을 사용할 준비가 끝났습니다

@ComponentScan의 동작과정

1. @SpringBootApplication의 @ComonentScan을 통해 컴포넌트 스캔이 시작됩니다.
2. ClassPathBeanDefinitionScanner가 프로젝트의 classPath를 스캔하면서 빈으로 등록할 클래스를 찾습니다
3. ClassPathBeanDefinitionScanner.scan.doScan은 지정된 패키지 내의 클래스를 스캔하고
이를 BeanDefinition으로 변환하여 등록합니다.
    
    ```java
    public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    	
        ...
        ...
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) { // 파라미터로 basePackages
    		Assert.notEmpty(basePackages, "At least one base package must be specified"); // 1. 패키지 검증
    		Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
    		for (String basePackage : basePackages) {
    			Set<BeanDefinition> candidates = findCandidateComponents(basePackage); // 2. 클래스 식별
    			for (BeanDefinition candidate : candidates) {
    				ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
    				candidate.setScope(scopeMetadata.getScopeName());
    				String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
    				if (candidate instanceof AbstractBeanDefinition) {
    					postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
    				}
    				if (candidate instanceof AnnotatedBeanDefinition) {
    					AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
    				}
    				if (checkCandidate(beanName, candidate)) {
    					BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
    					definitionHolder =
    							AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
    					beanDefinitions.add(definitionHolder);
    					registerBeanDefinition(definitionHolder, this.registry);
    				}
    			}
    		}
    		return beanDefinitions;
    	}
        ...
        ...
    }
    ```
    

1. 지정된 패키지 내의 후보 클래스를 찾습니다
2. 각 후보에 대하여 BeanScope와 빈 이름을 생성합니다.
3. BeanDefinition을 생성하고 적합성을 검증한 후 Set<BeanDefinitionHolder>을 반환합니다 
1. IoC 컨테이너는 BeanDefinitionHolder에 포함된 BeanDefinition을 사용하여 빈을 생성하고 관리합니다

+) BeanDefinition은 클래스 타입, 빈 스코프, 빈 라이프사이클, 콜백 메서드 등 빈의 생성과 관리에 필요한 정보가 포함되어 있다.

[https://curiousjinan.tistory.com/entry/spring-boot-constructor-injection-part1-componentscan-analysis#4. ClassPathBeanDefinitionScanner를 사용한 빈 등록 과정%3A doScan 메서드 호출-1](https://curiousjinan.tistory.com/entry/spring-boot-constructor-injection-part1-componentscan-analysis#4.%C2%A0ClassPathBeanDefinitionScanner%EB%A5%BC%20%EC%82%AC%EC%9A%A9%ED%95%9C%20%EB%B9%88%20%EB%93%B1%EB%A1%9D%20%EA%B3%BC%EC%A0%95%3A%C2%A0doScan%20%EB%A9%94%EC%84%9C%EB%93%9C%20%ED%98%B8%EC%B6%9C-1)

### 7. 하나의 인터페이스를 구현한 서비스가 여러 개 있을 때 어떻게 주입해야할까?

하나의 인터페이스를 구현한 여러 개의 서비스가 있을 때, IoC 컨테이너는 어떤 빈을 주입해야할지 결정하지 못하고, NoUniqueBeanException이라는 예외를 던지며 서버 구동이 멈추게 됩니다.

 

해결책 1 : @Qualifier 사용

- 각 빈에 이름을 부여합니다 : @Service(”A”) , @Service(”B”)
- 주입할 때 @Qualifier으로 주입할 빈의 이름을 명시합니다 : @Qualifier(”A”)

해결책 2 : @Primary 사용

- 주입할 빈에 @Primary를 붙이면 해당 어노테이션이 붙어있는 빈을 우선적으로 주입합니다.

---

## 스프링 삼각형

POJO를 기반으로하는  IoC/DI, AOP, PSA 의 스프링 3대 프로그래밍 모델을 스프링 삼각형이라고 합니다

### POJO

Plain Old Java Object이라는 뜻으로, 객체 지향적인 원리에 충실하면서 환경과 기술에 종속되지 않고, 필요에 따라 재활용될 수 있는 방식으로 설계된 자바 오브젝트를 의미합니다.

POJO를 3대 프로그래밍 모델에 맞게 관계를 맺으며 동작하도록 설계하고, 그러한 정보를 설계정보에 정의해놓는 것이 스프링의 핵심입니다.

### IoC/ DI

IoC는 제어의 역전이라는 의미로 제어권을 개발자가 외부로 넘기는 설계 패턴입니다.

객체의 생성, 생명주기 관리를 프레임워크/컨테이너로 위임합니다. Spring에서는 IoC 컨테이너가 그 역할을 합니다. 개발자가 필요한 객체를 정의해두고 어노테이션을 통해 객체에 대한 정보를 제공하면, 스프링 컨테이너가 자동으로 객체를 생성/관리합니다.

DI는 의존성 주입이라는 뜻으로 IoC 컨테이너가 의존성이 필요한 곳에 적절한 빈을 찾아서 자동으로 주입해줍니다. 이를 통해 개발자는 객체를 생성하여 직접 의존성을 설정하지 않아도 되고, 필요한 의존성을 외부에서 주입받아 객체간의 의존도를 낮추게 됩니다. 객체간의 의존성이 낮아짐으로써, 유연성 및 확장가능성이 좋아지고 각 객체가 독립적으로 작동하여 단위 테스트 및 객체 재사용이 용이해지는 장점이 있습니다.

### AOP

Ascpect Oriented Programming의 약자로 관점 지향 프로그래밍이라는 뜻입니다.

AOP는 다수의 모듈에 공통적으로 나타나는 공통 관심사와, 모듈별로 다른 핵심 관심사를 구분하는 프로그래밍 방법입니다. 보안, 인증, 로깅과 같은 공통 기능들을 분리하여 각 모듈이 각자의 책임에 집중할 수 있는 환경을 제공합니다.

AOP를 구현하는 대표적인 두가지 방법으로 SpringAOP와 AspectJ가 있습니다.

SpringAOP는 SpringBean에 대해서만 적용할 수 있고 런타임에 프록시를 사용하여 동작하기 떄문에 애플리케이션 성능에 영향이 있을 수 있습니다. 하지만, 별도 설정이 필요하지 않고 충분한 기능을 가지고 있으며 충분한 기능을 제공합니다.

AspectJ는 설정이 복잡하지만 모든 Java 객체에 적용할수 있고 더 많은 JoinPoint를 지원합니다. 또한 컴파일 타임, 로딩 시점에 위빙하므로 성능이 좋습니다.

최근에는 Spring AOP를 많이 사용하는 추세이기 때문에 SpringAOP에 대해서만 알아보겠습니다.

### SpringAOP

- Aspect : 공통 관심사를 모듈화 한 것. (Advice + PointCut : 무엇을 언제 어디서)
    - @Aspect
- Advice : 공통 관심사가 어떤 작업을 언제 수행하는지
    - @Before : 작업 수행 전
    - @After : 핵심 기능 수행 이후
    - @AfterRetruning : 핵심 기능이 정상적으로 수행된 이후
    - @AfterThrowing : 핵심 기능 실행 중 예외가 발생한 이후
    - @Around : 핵심 기능 실행 전과 후
- Target : Aspect를 적용하는 대상
- JointPoint : Aspect 적용이 가능한 지점들
- PointCut : JoinPoint의 상세 스펙을 정의한 것, 구체적으로  Advice가 호출되는 시점을 정의

```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

		// com.example.service 패키지 아래의 모든 클래스의 모든 메서드에 적용
    @Pointcut("execution(* com.example.service..*.*(..))")
    private void allServiceMethods() {}

		// Pointcut이 지정한 메서드가 실행되기 이전에 Advice가 실행
    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("===> Executing method: {}", methodName);
    }
}
```

### PSA

Portable Service Abstraction의 약자로, 환경의 변화와 상관없이 일관된 방식으로 특정 기술에 대한 접근 환경을 제공하는 추상화 구조를 뜻합니다. 특정 클래스가 추상화된 상위 클래스를 참조하여 하위 클래스의 기능을 사용하도록 하는 것이 PSA의 기본 개념입니다.

예를 들어 JdbcConnector 하위에는 OracleJdbcConnector, MariaDBJdbcConnector….들이 구현되어 있고 JdbcConnector의 getConnect()를 각각 오버라이딩 한다면 JdbcConnector의 getConnection()을 사용하면서 하위 구현체만 갈아끼우면 됩니다.

---

## CGV Clone ERD
<img width="1949" height="1401" alt="cgv_model_v2" src="https://github.com/user-attachments/assets/02501926-928f-4a6e-9fa4-38d01798d0a2" />


| MEMBER | member_id | 멤버 ID | 
| --- | --- | --- | 
|  | email | 이메일 |  
|  | password | 비밀번호 |  
|  | name | 이름 |  
|  | phone_number | 전화번호 |  
|  | created_at | 계정 생성 시간 |  
| REGION | region_id | 지역 ID  | 
|  | region_name | 지역 이름 : 서울/경기/인천 |
| THEATER | theater_id | 영화관 ID  |
|  | region_id | 지역 ID  | 
|  | name | 영화관 이름 : 용산아이파크몰 CGV |
|  | address | 주소 |
| MOVIE | movie_id | 영화 ID | 
|  | title | 영화 제목 | 
|  | director | 감독 이름 |
|  | genre | 장르 |
|  | running_time | 상영 시간 |
|  | release_date | 개봉일 | 
|  | poster_url | 포스터 사진 URL | 
| ACTOR | actor_id | 배우 ID |
|  | name | 배우명 |
|  | profile_image | 배우 사진 URL |
| MOVIE_ACTOR | movie_id | 영화 ID | 
|  | actor_id | 배우 ID | 
|  | role | 역할 : 주연/조연 |
| SCREEN | screen_id | 상영관 ID | 
|  | theater_id | 영화관 ID | 
|  | name | 상영관 이름 : 1관  / IMAX관 | 
|  | type | 상영관 타입 : 일반관 / IMAX | 
| SEAT | seat_id | 좌석 ID | 
|  | screen_id | 상영관 ID | 
|  | row | 좌석 행 : A, B | 
|  | col | 좌석 열 : 1, 2 | 
| SCHEDULE | schedule_id | 스케쥴 ID |
|  | movie_id | 영화 ID | 
|  | screen_id | 상영관 ID | 
|  | start_time | 상영 시작 시간 | 
|  | end_time | 상영 종료 시간 | 
| RESERVATION | reservation_id | 예매 ID |
|  | member_id | 예매 회원 ID | 
|  | schedule_id | 스케쥴 ID | 
|  | seat_id | 좌석 ID | 
|  | status | 예매 여부 | 
|  | created_at | 예매 시각 |  
| PRODUCT_CATEGORY | category_id | 카테고리 |
|  | name | 카테고리 이름 : 음료 |
| PRODUCT | product_id | 상품 |
|  | category_id | 카테고리 |
|  | name | 상품 이름 : 콜라M |
|  | price | 가격 |
|  | is_combo | 콤보 여부 : 콤보 1, 단품 0 |
|  | description | 상품 설명 |
|  | image_url | 상품 이미지 url |
| COMBO_PRODUCT | combo_product_id | 콤보 상품 ID |
|  | item_id | 단품 상품 ID |
|  | quantity | 콤보에 포함되는 단품 수량 |
| THEATER_STOCK | theater_id |
|  | product_id | 단품 상품 ID |
|  | stock | 단품 상품 재고 |
| ORDERS | order_id | 주문 ID |
|  | member_id | 주문자 ID |
|  | theater_id | 영화관 ID |
|  | total_price | 총 주문 금액 |
|  | created_at | 주문 시간 |
| ORDER_DETAIL | order_detail_id | 주문 세부사항 ID |
|  | order_id | 주문 ID |
|  | product_id | 상품 ID |
|  | quantity | 상품 수량 |
| WISH | wish_id | 찜 ID | 
|  | member_id | 사용자 ID | 
|  | movie_id | 영화 ID | 
|  | theater_id | 영화관 ID |
|  | type | 영화관/영화 찜 : THEATER/MOVIE |
