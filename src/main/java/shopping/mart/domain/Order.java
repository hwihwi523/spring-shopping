package shopping.mart.domain;

import java.math.BigInteger;
import java.util.List;
import shopping.core.exception.BadRequestException;
import shopping.mart.domain.status.OrderExceptionStatus;

public class Order {

    private final List<Product> products;
    private final Price totalPrice;

    public Order(final List<Product> products) {
        validate(products);
        this.products = products;
        this.totalPrice = calcTotalPrice(products);
    }

    private void validate(final List<Product> products) {
        if (products.isEmpty()) {
            throw new BadRequestException("장바구니에 물건이 존재하지 않습니다.", OrderExceptionStatus.EMPTY_CART.getStatus());
        }
    }

    private Price calcTotalPrice(final List<Product> products) {
        String calculated = products.stream()
                .map(product -> new BigInteger(product.getPrice()))
                .reduce(BigInteger::add)
                .orElseThrow(() -> new IllegalStateException("장바구니가 비어 있어 총 결제 금액을 계산할 수 없습니다."))
                .toString();

        return new Price(calculated);
    }

    public String getTotalPrice() {
        return totalPrice.getValue().toString();
    }
}
