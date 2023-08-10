package shopping.mart.persist;

import shopping.core.entity.ProductEntity;

class PersistFixture {

    static class Product {

        static shopping.mart.domain.Product withEntity(ProductEntity productEntity) {
            return new shopping.mart.domain.Product(productEntity.getId(), productEntity.getName(),
                    productEntity.getImageUrl(),
                    productEntity.getPrice());
        }
    }
}
