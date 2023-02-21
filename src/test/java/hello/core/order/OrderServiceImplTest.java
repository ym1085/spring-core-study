package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {

    @Test
    void createOrder() throws Exception {
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        orderService.createOrder(1L, "itemA", 10000);
    }

}
