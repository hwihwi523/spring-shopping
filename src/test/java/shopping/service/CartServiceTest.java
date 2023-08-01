package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.domain.Cart;
import shopping.domain.exception.StatusCodeException;
import shopping.dto.CartAddRequest;
import shopping.dto.CartUpdateRequest;
import shopping.persist.CartRepository;
import shopping.persist.ProductRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("CartService 테스트")
@ContextConfiguration(classes = {CartService.class})
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductRepository productRepository;

    private void assertStatusCodeException(final Exception exception, final String expectedStatus) {
        assertThat(exception.getClass()).isEqualTo(StatusCodeException.class);
        assertThat(((StatusCodeException) exception).getStatus()).isEqualTo(expectedStatus);
    }

    @Nested
    @DisplayName("addProduct 메소드는")
    class addProduct_method {

        @Test
        @DisplayName("product를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_product_not_found() {
            // given
            CartAddRequest request = new CartAddRequest(9999999999L);
            Cart cart = new Cart(1L, 1L);

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.addProduct(1L, request));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-401");
        }

        @Test
        @DisplayName("userId에 해당하는 cart를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_cart_not_found() {
            // given
            CartAddRequest request = new CartAddRequest(9999999999L);

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.addProduct(1L, request));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-402");
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class updateProduct_method {
        @Test
        @DisplayName("product를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_product_not_found() {
            // given
            CartUpdateRequest request = new CartUpdateRequest(9999999999L, 100);
            Cart cart = new Cart(1L, 1L);

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.updateProduct(1L, request));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-401");
        }

        @Test
        @DisplayName("userId에 해당하는 cart를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_cart_not_found() {
            // given
            CartUpdateRequest request = new CartUpdateRequest(9999999999L, 100);

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.updateProduct(1L, request));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-402");
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class deleteProduct_method {

        @Test
        @DisplayName("product를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_product_not_found() {
            // given
            long userId = 999999L;
            long productId = 99999999L;
            Cart cart = new Cart(1L, 1L);

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.deleteProduct(userId, productId));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-401");
        }

        @Test
        @DisplayName("userId에 해당하는 cart를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_cart_not_found() {
            // given
            long userId = 999999L;
            long productId = 99999999L;

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.deleteProduct(userId, productId));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-402");
        }
    }

    @Nested
    @DisplayName("findCart 메소드는")
    class findCart_method {

        @Test
        @DisplayName("userId에 해당하는 cart를 찾을 수 없으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_cart_not_found() {
            // given
            long userId = 999999L;

            when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.findCart(userId));

            // then
            assertStatusCodeException(exception, "CART-SERVICE-402");
        }
    }
}
