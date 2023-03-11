package hello.core.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    /**
     * 단순히 프로토타입 빈을 호출하여 카운트가 각각 1이 되는지 확인하기 위함
     * @throws Exception
     */
    @Test
    @DisplayName("프로토타입 스코프 빈 호출 시 카운트 증가 테스트")
    void prototypeFind() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount(); // n + 1
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    /**
     * 싱글톤 빈 안에 있는 프로토타입 빈이 요청할 때마다 새로 생성되는 것을 원하는 상태
     * @throws Exception
     */
    @Test
    @DisplayName("싱글톤 스코프 빈 안에 있는 프로토타입 스코프 빈 호출하여 카운트 증가 테스트")
    void singletonClientUsePrototype() throws Exception {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        /**
            remind
            - 싱글톤 스코프는 싱글톤 객체 즉, 동일한 객체를 반환함
            - 프로토타입 스코프는 새로운 객체를 생성해 반환
         */
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    /* 싱글톤 스코프 빈 */
    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean; // 생성 시점에 이미 의존 관계 주입이 되어 있다

        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    /* 프로토타입 스코프 빈 */
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init => " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy => " + this);
        }
    }
}
