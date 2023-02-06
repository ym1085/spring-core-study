package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RateDiscountPolicyV2 implements DiscountPolicy {

    /* 할인율 : 10% */
    private static final int DISCOUNT_PERCENT = 10; // 10% 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * DISCOUNT_PERCENT / 100; // 상품 가격 * 할인율 / 100
        } else {
            return 0; // VIP 아닌 경우 할인율은 없음
        }
    }
}
