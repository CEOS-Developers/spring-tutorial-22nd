# spring-tutorial-22nd

## 2️⃣ spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)

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
