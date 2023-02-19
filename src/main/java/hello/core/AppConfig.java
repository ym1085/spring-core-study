package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //@Bean -> memberService() 호출 -> new MemoryMemberRepository() 호출
    //@Bean -> ??

    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //...

    @Bean
    public MemberService memberService() {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    // AppConfig.class에서 빈을 등록하는 메서드가 static 메서드인 경우 -> 싱글턴 보장이 안된다
    // https://www.inflearn.com/questions/609357/appconfig-class%EC%97%90%EC%84%9C-%EB%B9%88%EC%9D%84-%EB%93%B1%EB%A1%9D%ED%95%98%EB%8A%94-%EB%A9%94%EC%84%9C%EB%93%9C%EA%B0%80-static-%EB%A9%94%EC%84%9C%EB%93%9C%EC%9D%BC-%EB%95%8C
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
