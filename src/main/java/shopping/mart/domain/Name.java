package shopping.mart.domain;

import java.text.MessageFormat;
import java.util.Objects;
import shopping.core.exception.BadRequestException;
import shopping.mart.domain.status.ProductExceptionStatus;

final class Name {

    private final String value;

    Name(final String name) {
        validName(name);
        this.value = name;
    }

    private void validName(final String name) {
        validNullName(name);
        validExceedName(name);
        validBlankName(name);
    }

    private void validNullName(final String name) {
        if (name == null) {
            throw new BadRequestException("name은 null이 될 수 없습니다.", ProductExceptionStatus.NULL_NAME.getStatus());
        }
    }

    private void validExceedName(final String name) {
        if (name.length() > 20) {
            throw new BadRequestException(MessageFormat.format("name \"{0}\"은 20자보다 길어질 수 없습니다.", name),
                    ProductExceptionStatus.EXCEED_NAME.getStatus());
        }
    }

    private void validBlankName(final String name) {
        if (name.isBlank()) {
            throw new BadRequestException(MessageFormat.format("name \"{0}\"은 공백이 될 수 없습니다.", name),
                    ProductExceptionStatus.BLANK_NAME.getStatus());
        }
    }

    String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name)) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Name{" +
                "value='" + value + '\'' +
                '}';
    }
}
