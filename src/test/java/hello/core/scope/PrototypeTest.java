package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    /**
     * '프로토타입 스코프 빈'
     * 1. 프로토타입 빈은 요청 시 새로운 빈을 생성하여 반환한다
     * 2. 프로토타입 빈은 스프링 컨테이너에서 빈을 조회할 때 빈이 생성되고 초기화 메서드 역시 호출 된다
     * 3. 프로토타입 빈은 스프링 빈의 생성과 의존관계 주입까지만 관여하기에 @Predestroy와 같은 메서드는 호출이 되지 않음 -> 직접 호출해야함
     * @throws Exception
     */
    @Test
    void prototypeBeanFind() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // 프로토타입 스코프 빈은 컨테이너에서 빈을 '조회'할때 빈이 생성되고, 초기화 메서드가 실행됨
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
        ac.close();
    }

    @Scope("prototype")
    //@Component -> ?? -> new Annotation..(PrototypeBean.class) 지정 시 @Component가 달린 것처럼 동작 함
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println(">>>> PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println(">>>> PrototypeBean.destroy");
        }
    }
}
