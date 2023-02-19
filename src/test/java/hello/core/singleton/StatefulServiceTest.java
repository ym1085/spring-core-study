package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    @DisplayName("싱글톤 컨테이너의 문제점 시나리오 구성")
    void statefulServiceSingleton() throws Exception {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //when
        //ThreadA: A사용자 10000원 주문
        int userA = statefulService1.order("userA", 10000);

        //ThreadB: B사용자 20000원 주문
        int userB = statefulService2.order("userB", 20000);

        //ThreadA: A사용자의 주문 금액 조회
        //int price = statefulService1.getPrice(); // expect) 20000
        //System.out.println("price = " + price);

        //then
        Assertions.assertThat(userA).isEqualTo(10000);
    }

    /**
     * 싱글톤 컨테이너의 Stateful 문제점 시나리오 구성
     */
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}
