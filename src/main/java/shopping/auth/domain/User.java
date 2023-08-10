package shopping.auth.domain;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Pattern;
import shopping.auth.domain.status.UserExceptionStatus;
import shopping.core.exception.BadRequestException;

public final class User {

    private static final int MAXIMUM_EMAIL_LENGTH = 100;
    private static final Pattern EMAIL_MATCHER = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern SPECIAL_CHARACTER_MATCHER = Pattern.compile(".*[!@#$%^&*(),.?\":{}|<>].*");
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    private static final int MAXIMUM_PASSWORD_LENGTH = 100;

    private final Long id;
    private final String email;
    private final String password;

    public User(final String email, final String password) {
        this(null, email, password);
    }

    public User(final Long id, final String email, final String password) {
        validateEmail(email);
        validatePassword(password);

        this.id = id;
        this.email = email;
        this.password = password;
    }

    private void validateEmail(final String email) {
        if (isValidEmail(email)) {
            return;
        }
        throw new BadRequestException(MessageFormat.format("\"{0}\" 은 사용할 수 없는 이메일입니다.", email),
                UserExceptionStatus.INVALID_EMAIL.getStatus());
    }

    private boolean isValidEmail(String email) {
        return email.length() <= MAXIMUM_EMAIL_LENGTH && EMAIL_MATCHER.matcher(email).matches();
    }

    private void validatePassword(final String password) {
        if (isValidPassword(password)) {
            return;
        }
        throw new BadRequestException(MessageFormat.format("비밀번호 \"{0}\" 는 특수문자를 포함하면서 {1} 자 이상이어야 합니다.",
                password, MINIMUM_PASSWORD_LENGTH), UserExceptionStatus.INVALID_PASSWORD.getStatus());
    }

    private boolean isValidPassword(String password) {
        return MINIMUM_PASSWORD_LENGTH <= password.length() && password.length() <= MAXIMUM_PASSWORD_LENGTH
                && SPECIAL_CHARACTER_MATCHER.matcher(password).matches();
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void assertPassword(final String password) {
        if (this.password.equals(password)) {
            return;
        }
        throw new BadRequestException("비밀번호가 일치하지 않습니다.", UserExceptionStatus.LOGIN_FAILED.getStatus());
    }
}
