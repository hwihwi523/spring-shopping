package shopping.core.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
abstract class TimeBaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP(6)", nullable = false, updatable = false)
    protected Instant createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP(6)", nullable = false)
    protected Instant updatedAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();

        createdAt = createdAt != null ? createdAt : now;
        updatedAt = updatedAt != null ? updatedAt : now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = updatedAt != null ? updatedAt : Instant.now();
    }
}
