package shopping.mart.dto;

import java.util.List;

public final class OrderDetailResponse {

    private Long orderId;
    private List<OrderedProductResponse> items;
    private String totalPrice;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long orderId, List<OrderedProductResponse> items, String totalPrice) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderedProductResponse> getItems() {
        return items;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public static final class OrderedProductResponse {

        private String name;
        private String imageUrl;
        private String price;
        private int count;

        public OrderedProductResponse() {
        }

        public OrderedProductResponse(String name, String imageUrl, String price, int count) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.price = price;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getPrice() {
            return price;
        }

        public int getCount() {
            return count;
        }
    }
}
