package shopping.mart.domain.status;

public enum CartExceptionStatus {

    NOT_EXIST_DELETABLE_PRODUCT("CART-401"),
    NOT_EXIST_UPDATABLE_PRODUCT("CART-402"),
    UPDATE_COUNT_NOT_POSITIVE("CART-403"),
    ALREADY_EXIST_PRODUCT("CART-404"),
    NOT_FOUND("CART-405"),
    ;

    private final String status;

    CartExceptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
