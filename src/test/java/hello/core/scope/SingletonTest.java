package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    /**
     * '싱글톤 스코프 빈'
     * 1. 싱글톤 스코프 빈은 요청 시 동일한 객체를 반환한다
     * 2. 싱글톤 스코프 빈은 스프링 컨테이너의 생성 시점에 빈이 생성되고 초기화 메서드 역시 호출이된다
     * 3. 싱글톤 스코프 빈은 컨테이너 생성 시점 -> 컨테이너 종료 시점 까지 관리가 되기에 @Predestroy와 같은 메서드도 호출이 된다
     * @throws Exception
     */
    @Test
    void singletonBeanFind() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        System.out.println("Is same instance? bean1 == bean2 => " + (bean1 == bean2)); //  true

        // 동일한 인스턴스 객체인가?
        assertThat(bean1).isSameAs(bean2);
        ac.close();
    }

    @Scope("singleton") // default : singleton
    static class SingletonBean {

        @PostConstruct
        public void init() {
            System.out.println(">>>> SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println(">>>> SingletonBean.destroy");
        }
    }
}
