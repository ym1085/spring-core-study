package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    /* 기존 Client 소스를 변경 하여야 한다 */
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    TODO: 03. 관심사의 분리 내용 진행
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 이 부분이 문제임, DIP 원칙 위배

    // 의존관계 역전 법칙 적용, 의존성 주입을 안해주고 있기 때문에 NullPointerException 발생!!
    // 누군가 대신 의존성 주입을 해줘야 Client 코드를 건드리지 않고 DIP 원칙을 지킬 수 있다
    // 누구?? -> 스프링 IOC 컨테이너 + DI 원칙
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;

//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

    /*@Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }*/

//     생성자가 하나인 경우에는, @Autowired를 생략할 수 있다, 두개인 경우에는 생략이 불가능
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
