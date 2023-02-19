package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void test() throws Exception {
        //given
        AppConfig appConfig = new AppConfig();

        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //when
        //then
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    public static void main(String[] args) {
        // 싱글턴 객체 생성이 불가능하게 설계 되어 있음
//        SingletonService singletonService = new SingletonService();
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() throws Exception {
        //given
        //new SingletonService(); // compile error

        // 01. 처음 호출 방식
        //new SingletonService.getInstance();

        // 02. getInstance가 static method 이기에 아래와 같이 호출하여 사용 한다
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //when
        //then
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        assertThat(singletonService1).isSameAs(singletonService2);
        //Assertions.assertThat(singletonService1).isNotSameAs(singletonService2); // 같아야 함

        // same : ==
        // equal : java equals method
    }

}
