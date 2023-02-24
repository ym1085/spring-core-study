package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * @param memberId : 회원 번호
     * @param itemName : 상품명
     * @param itemPrice : 상품 가격
     * @return 주문 정보 반환
     */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // memberId를 기반으로 하여 회원 정보를 찾는다
        Member member = memberRepository.findById(memberId);

        // 회원 정보를 기반으로 할인율을 받아온다
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 회원 정보를 그대로 반환 해준다, DB는 사용하지 않음
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //싱글톤 컨테이너 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
