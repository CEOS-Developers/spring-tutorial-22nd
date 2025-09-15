# spring-tutorial-22nd

## spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)

### IoC/DI

IoC: 제어의 역전이라는 뜻으로, 원래는 개발자가 생성, 관리 등을 주도했으나 이는 프레임워크가 역으로 주도권을 가진다는 뜻이다. -> 즉, 개발자들은 이제 필요한 객체들을 선언만 해두면, 나머지는 프레임워크가 알아서 적절한 객체를 주입해주고, 관리해준다는 개념
이를 통해 애플리케이션을 구성하는 객체 간의 낮은 결합도를 유지할 수 있다.

그렇다면 **DI**는 무엇일까?
-> 의존성 주입으로, 스프링 컨테이너에서 Bean을 먼저 생성해두면, 객체 간의 관계를 주입해주는 **디자인 패턴**이다. (외부에서 두 객체간의 관계를 결정해준다.)

🤚의존성을 왜 외부로부터 받아야하는걸까?
  만약 내부에서 직접 의존성을 주입한다면, 이후에 다른 클래스를 변경할 때 두 클래스 모두 코드를 변경해야한다.
  예시)
  ```java
public class Cafe{
	Coffee coffee;
	
	public Coffee(){
		coffee = new Bean();
	}
}
```
이럴 경우, 이 카페에서는 모든 커피가 일반 원두로만 나오고, 만약 디카페인 커피가 먹고싶은 경우에는 코드를 **직접** 수정해야한다
  ```java
public class Cafe{
	Coffee coffee;
	
	public Coffee(){
		coffee = new DecaffeineBean();
	}
}
```
  이렇게 특정 클래스에 의존하게 되므로, 이런 문제를 해결하기 위해 외부에서 의존성을 주입받아야한다.

의존성 주입에는, 3가지가 있는데, 주로 생성자를 통한 의존성 주입을 많이들 사용한다.
```java
public class Cafe{
	Coffee coffee
	
	public Coffee(Bean bean){
		this.bean = bean;
	}
}
```
이렇게 구현할 경우, 이전처럼 코드를 직접 수정할 필요가 없이, 원하는 원두를 넣으면, 그에 맞게 커피가 나온다.

주로 편의성을 위해서 최종적으로는 아래와 같이 구현한다.
```java
@RequiredArgsConstructor
public class Cafe{
	private final Bean bean;
}
```
`@RequiredArgsConstructor`: 필수 인자를 가진 생성자를 자동으로 생성해주는 Lombok에서 제공하는 어노테이션이다. 해당 어노테이션이 붙을 경우, final 필드나 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 자동으로 생성해준다.

공식문서 : https://projectlombok.org/api/lombok/RequiredArgsConstructor

### AOP (Aspect Oriented Programming)
: 관점 지향 프로그래밍으로, 객체를 기준으로 하는 OOP와 달리, 관점을 기준으로 기능들을 분리하는 프로그래밍 기법이다. 여기서 관점(Aspect)이란, 여러 객체에 공통적으로 사용되는 기능들(-> 이를 횡단관심사 라 한다)을 분리하여 모듈화한 단위이다.

그렇다면 이미 OOP 방식이 있는데, AOP 방식이 왜 필요한걸까?
1. 횡단 관심사를 모듈화하여 코드 중복을 줄일 수 있다.
2. 비즈니스 로직과 공통된 로직을 분리하여 가독성, 유지보수성이 향상된다.
3. 후에 모듈을 수정해야할 때 한 곳에서만 수정하면 된다.

중요한 건 AOP는 OOP를 대체하는 게 아닌 보완하는 방식으로, OOP로는 클래스 단위로, AOP로는 기능 단위로 모듈화를 하여 코드를 더 깔끔하게 작성할 수 있다.

### PSA (Portable Service Abstraction)
: 환경의 변화와 상관없이 일관된 방식으로 기술에 접근할 수 있는 환경을 제공하는 추상화 구조

서비스 추상화(Serivce Abstraction)는 추상화 계층을 사용하여, 어떤 기술은 내부에 숨기고 개발자에게 편의성을 제공해주는 것이다. 

그렇다면 Portable은? 휴대하기 쉬운 이라는 뜻도 있는데, 포터블 프로그램은 설치없이도 가능한 프로그램이라는 뜻이다. 즉 PSA는 환경이 변화되어도, 이를 따로 설치하거나 세팅할 필요없이 하나의 추상화로 여러 서비스에 접근가능하다는 뜻이 아닐까?

PSA에 해당하는 예시로는 Spring Web MVC, Spring Transaction 등이 있다 (이에 대해서는 뒤에서 더 자세히 설명하겠다)


## Spring Bean 이 무엇이고, Bean 의 라이프사이클과 Bean Scope에 대해 조사해요

### Spring Bean
: 스프링 컨테이너가 관리하는 자바 객체로, 컨테이너에 의해 Life cycle(생명 주기)도 관리된다

Bean의 Life cycle은 다음과 같다

스프링 컨테이너 생성 → 빈 생성 → 의존관계 주입 → 콜백(초기화) @PostConstruct 등 → 사용 → 콜백(소멸전)@PreDestroy 등 → 종료

✅콜백: 객체를 생성한 후, 의존관계까지 주입되었으니 사용이 가능하다고 알려주는 것

Bean Scope: 빈이 존재할 수 있는 범위이다.

프로젝트 규모가 커질수록, Bean을 직접 등록하는 건 어려워서 주로 어노테이션을 활용하여 등록한다.

### Annotation
: 주석과 비슷한 의미이나, 어노테이션은 프로그램의 컴파일, 배포, 실행 과정에서 다양한 기능을 수행하도록 도움을 준다.

어노테이션은 다양한 역할을 가지는데,
1. 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보를 제공
2. 소프트웨어 개발 툴이 빌드나 배치 시 코드를 자동으로 생성할 수 있도록 정보를 제공
3. 실행할 때 특정 기능을 실행하도록 정보를 제공

위와 같은 역할을 가진다.

어노테이션을 기반으로 할 때 Bean 등록 흐름은 다음과 같다.

앱 시작 → Context 생성 → 구성 클래스 + 애노테이션 읽기 → 스캔/파싱 → BeanDefinition 생성 → 빈 정의 등록 → 빈 인스턴스 및 의존성 주입 → 초기화 콜백 → 빈 사용 → 종료 시 소멸 콜백

`@ComponentScan`을 통해 스프링이 컴포넌트를 탐색하는 과정❓
: `@ComponentScan`은 탐색 위치에 `@Component`가 붙은 모든 클래스를 스프링 빈으로 등록하는 어노테이션이다. 

기존에는 스프링 빈을 등록할 때, 설정 정보를 담고있는 클래스(config)에 @Configuration 태그를 사용하고, 스프링 빈으로 등록할 객체에 @Bean 태그를 사용해서 생성자 형태로 의존성을 주입한다.
그러나 이런 방법은, 등록해야할 스프링 빈의 수가 많아질수록 하나하나 등록하기 힘들며, 실수가 생길 가능성도 크다. 

이러한 방법 대신 `@ComponentScan`을 설정 파일에 추가하고, 스프링 빈으로 등록할 클래스에 `@Component`를 추가하면, 스캔 대상이 되어, 스프링 빈으로 자동으로 등록한 후, 의존성 주입도 @Autowired로 해결할 수 있다.

## 🔥Spring MVC를 심층 분석해요🔥
### MVC패턴 vs Spring MVC
결론부터 말하자면, MVC 패턴은 소프트웨어 개발에서 역할 분리를 위해 만들어진 **디자인 패턴**이고, Spring MVC는 이 패턴을 웹 애플리케이션에 적용하여 구현한 **프레임워크**이다.

### Servlet
: 웹 애플리케이션에서 클라이언트의 요청을 처리하고, 그에 대한 응답을 생성하는 구성요소 -> 즉, 클라이언트와 HTTP 요청과 응답을 주고받는 웹 환경을 구성하는 역할을 맡은 것이다.

<img width="1068" height="462" alt="image" src="https://github.com/user-attachments/assets/3eada75b-35f4-42da-be96-f06d54109146" />
서블릿의 동작 흐름이다. 보다시피, 서블릿을 관리하는 컨테이너에서, httpRequest를 받아 HttpServletRequest, HttpServletResponse로 변환하여 우리에게 넘겨주면, 우리는 해당 요청을 처리하고 응답을 생성하는 과정만 담당하게 된다.

✅Servlet Container: Java 웹 애플리케이션의 핵심 구성 요소로 Servlet의 생명 주기를 담당한다.

스프링에서는 내장 스프링 컨테이너 Tomcat이 관리하는 Servlet 중, Dispatcher Servlet이 대표적이다.
✚Tomcat : WAS(Web Application Server)라고도 하며, JSP와 Servlet을 구동하기 위한 서블릿 컨테이너 역할을 수행한다
✚WAS : 동적 리소스를 처리하는 서버로, 기존 Web 서버는 정적 리소스만 처리가 가능하기에, Web Server는 주로 간단한 요청에 대한 일을 처리하고, WAS는 더 복잡한 로직을 처리한다.
<img width="673" height="269" alt="image" src="https://github.com/user-attachments/assets/511a3108-6790-4f5c-a569-438ae5468f02" />


### Dispather Servlet
<img width="1416" height="690" alt="image" src="https://github.com/user-attachments/assets/6a153393-035d-42a7-a5a9-2a4fdda1ad30" />


**1. 클라이언트 요청** - 사용자의 HTTP 요청이 들어옴

**2. DispatcherHandler가 요청 수신** - WebHandler 인터페이스 구현체로서, 요청을 수신하고 처리 시작

**3. HandlerMapping 탐색** - 등록된 HandlerMapping 중 가장 먼저 매칭되는 핸들러를 찾음

**4. HandlerAdapter 실행** - 매핑된 핸들러를 실제로 실행시킬 수 있는 HandlerAdapter를 통해 호출함

**5. HandlerResult 생성** - 핸들러의 실행 결과를 HandlerResult로 감쌈 (컨트롤러 리턴값 포함)

**6. HandlerResultHandler 실행** - 적절한 HandlerResultHandler가 HandlerResult를 해석하고 처리 (뷰 렌더링 포함)

**7. ViewResolver 처리** - 뷰 이름을 논리적으로 해석하여 실제 View 객체로 매핑함

**8. 응답 View 렌더링**- View 객체가 HTML 등을 렌더링하여 클라이언트에게 최종 응답 전송

이때, 컨트롤러로 요청을 위임하는 doDispatch메소드가 있는데, 요청에 매핑되는 HandlerMapping을 조회해서 매칭되는 핸들러를 찾은 후에, 이 요청을 처리할 HandlerAdapter를 조회하며, 조회한 HandlerAdapter를 통해 컨트롤러 메소드를 호출하여 실행한다.

참고 블로그: https://jake-seo-dev.tistory.com/380

