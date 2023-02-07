package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class OrderApp {

    public static void main(String[] args) {
        // given
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

//        MemberService memberService = new MemberServiceImpl(null);
//        OrderService orderService = new OrderServiceImpl(null, null);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);

        // when
        memberService.join(member); // 회원 가입
        Order order = orderService.createOrder(member.getId(), "itemA", 10000); // 상품 주문

        // then
        // order = Order{memberId=1, itemName='itemA', itemPrice=10000, discountPrice=1000}
        System.out.println("order = " + order); // 상품 결과
        System.out.println("order = " + order.calculatePrice()); // 상품 결과
    }
}

