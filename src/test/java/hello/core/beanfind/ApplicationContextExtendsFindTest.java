package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회 시 자식(bean)이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByParentTypeDuplicate() throws Exception {
        //given
        //when
        //then
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면, 빈 이름을 지정")
    void findBeanByParentTypeBeanName() throws Exception {
        //given
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);

        //when
        //then
        assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() throws Exception {
        //given
        // getBean -> ApplicationContext 안에 있는 RateDiscountPolicy Type을 통해 추출
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);

        //when
        //then
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllByParentType() throws Exception {
        //given
        Map<String, DiscountPolicy> beans = ac.getBeansOfType(DiscountPolicy.class);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + ", value = " + beans.get(key));
        }

        //when
        //then
        assertThat(beans.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllBeanByObjectType() throws Exception {
        //given
        Map<String, Object> beans = ac.getBeansOfType(Object.class);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + ", value = " + beans.get(key));
        }

        //when

        //then
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
