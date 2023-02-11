package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    public void findBeanByTypeDuplicate() {
        //given
        /* 스프링 입장에서는 현재 name(bean 이름)이 존재하지 않기 때문에 Exception이 발생한다 */
//        MemberRepository bean = ac.getBean(MemberRepository.class);

        //when
        //then
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByName() throws Exception {
        //given
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        //when
        //then
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기 -> bean 이름이 다르고 타입은 갖은 경우가 1개 이상인 경우")
    void findAllBeanByType() throws Exception {
        //given
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);

        //when
        //then
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    /* class 안에 inner class를 사용했다는 것은, 해당 클래스의 안에서(scope)만 사용하겠다는 것을 의미 함 */
    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
