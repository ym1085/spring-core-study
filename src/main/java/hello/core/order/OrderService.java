package hello.core.order;

public interface OrderService {

    /**
     * @param memberId : 회원 번호
     * @param itemName : 상품명
     * @param itemPrice : 상품 가격
     * @return 주문 내역 반환
     */
    Order createOrder(Long memberId, String itemName, int itemPrice);

}
