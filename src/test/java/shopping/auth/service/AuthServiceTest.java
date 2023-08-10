package shopping.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyString;
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
import shopping.auth.domain.User;
import shopping.auth.dto.LoginRequest;
import shopping.auth.dto.TokenResponse;
import shopping.auth.dto.UserJoinRequest;
import shopping.auth.infra.JwtUtils;
import shopping.auth.persist.UserRepository;
import shopping.core.exception.BadRequestException;

@ExtendWith(SpringExtension.class)
@DisplayName("AuthService 클래스")
@ContextConfiguration(classes = {AuthService.class, JwtUtils.class})
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    private void assertStatusCodeException(final Exception exception, final String expectedStatus) {
        assertThat(exception.getClass()).isEqualTo(BadRequestException.class);
        assertThat(((BadRequestException) exception).getStatus()).isEqualTo(expectedStatus);
    }

    @Nested
    @DisplayName("joinUser 메소드는")
    class joinUser_method {

        @Test
        @DisplayName("email이 중복되면, BadRequestException을 던진다.")
        void throw_StatusCodeException_when_duplicated_email() {
            // given
            UserJoinRequest request = new UserJoinRequest("hello@hello.world", "hello!123");
            User user = new User(request.getEmail(), request.getPassword());
            when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

            // when
            Exception exception = catchException(() -> authService.joinUser(request));

            // then
            assertStatusCodeException(exception, "AUTH-404");
        }
    }

    @Nested
    @DisplayName("authenticate 메소드는")
    class authenticate_method {

        @Test
        @DisplayName("email, password가 일치하면 토큰을 반환한다.")
        void it_return_token_when_matched_email_and_password() {
            // given
            LoginRequest request = new LoginRequest("hello@hello.world", "hello!123");

            User user = new User(1L, request.getEmail(), request.getPassword());
            when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

            // when
            TokenResponse result = authService.authenticate(request);

            // then
            assertThat(result.getAccessToken()).isNotNull();
        }

        @Test
        @DisplayName("email에 해당하는 유저를 찾을 수 없으면, BadRequestException 반환한다.")
        void it_throw_StatusCodeException_when_not_matched_email() {
            // given
            LoginRequest request = new LoginRequest("hello@hello.world", "hello!123");

            when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> authService.authenticate(request));

            // then
            assertStatusCodeException(exception, "AUTH-405");
        }
    }
}
