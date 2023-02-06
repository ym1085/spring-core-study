package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyV2Test {

    private final DiscountPolicy discountPolicy = new RateDiscountPolicyV2();

    @Test
    @DisplayName("VIP인 경우 할인율이 10%가 적용되는지 확인")
    public void discount_10_percent_for_vip() {
        //given
        Member member = new Member(1L, "홍길동", Grade.VIP);// VIP

        //when
        int discountPrice = discountPolicy.discount(member, 10000);

        //then
        //org.assertj.core.api.Assertions;
        assertThat(discountPrice).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아닌 경우 할인율이 10%가 적용 안되는지 확인")
    public void none_discount_10_percent_for_basic() {
        //given
        Member member = new Member(2L, "일반인", Grade.BASIC);

        //when
        int discountPrice = discountPolicy.discount(member, 10000);

        //then
//        assertThat(discountPrice).isEqualTo(1000); // fail
        assertThat(discountPrice).isEqualTo(0); // success -> 할인율 안먹음
    }

}
