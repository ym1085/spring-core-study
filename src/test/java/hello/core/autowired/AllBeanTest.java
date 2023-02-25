package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    @DisplayName("스프링 Bean 조회 타입이 2개 이상인 경우 List, Map을 사용하여 모든 의존 관계 주입을 받는다")
    void getAllBeanWithMapAndList() throws Exception {
        // given

        // 01. ApplicationContext(DI 컨테이너 안에 연관된 모든 Bean, DisCountService(Bean)을 등록 한다)
        // ㄴ AutoAppConfig.class의 경우 @Component가 붙은 모든 클래스를 대상으로 Bean 등록을 수행 하도록 설정 되있ㄷ
        // ㄴ DiscountService.class의 경우 아래에서 만든 Custom 클래스다
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DisCountService.class);

        // 02. DiscountService Bean을 가져온다
        DisCountService discountService = ac.getBean(DisCountService.class);

        // 03. 한명의 Dummy 유저를 등록 ( uno, name, grade )
        Member member = new Member(1L, "youngminkim", Grade.VIP);

        // 04. 아래 Custom Class discountService의 discount method 호출 -> 다형성을 활용하여 override 된 메서드를 호출한다
        String childDiscountBeanName = "fixDiscountPolicy"; // 현재 IOC 컨테이너 안에는 fixDiscountPolicy bean이 등록되어 있는 상황

        // when

        // 할인율을 적용하고자 하는 '유저의 정보', '금액', '하위 오버라이딩 클래스 bean'
        int discountPrice = discountService.discount(member, 1000, childDiscountBeanName);

        // then

        assertThat(discountService).isInstanceOf(DisCountService.class);
        assertThat(discountPrice).isEqualTo(1000); // 고정 할인율 : 1000원
    }

    static class DisCountService {
        // https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans
        // Spring IOC이 내부적으로 Map, List에 의존 관계를 주입하는 메터니즘
        private Map<String, DiscountPolicy> policyMap;
        private List<DiscountPolicy> policies;

        // 생성자가 1개인 경우 @Autowired를 생략 할 수 있다
        @Autowired
        public DisCountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println(">>>> policyMap => " + policyMap.toString());
            System.out.println(">>>> policies => " + policies.toString());
        }

        public int discount(Member member, int price, String childDiscountBeanName) {
            // policyMap에 이미 등록이 되어있는 Bean들이 존재할 것이다.
            // 위에서 받은 childDiscountBeanName(bean 이름)을 통해 실제 사용할 Bean 객체를 가져온다
            DiscountPolicy discountPolicy = this.policyMap.get(childDiscountBeanName);

            // TODO 검증 discountPolicy(Interface)에 어떤 객체가 들어가 있는가?
            System.out.println(">>>> discountPolicy.getClass() => " + discountPolicy.getClass());
            return discountPolicy.discount(member, price);
        }
    }
}
