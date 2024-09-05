package me.jun.core.member.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
open class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var memberId: Long?,

    @Column(nullable = false)
    open var name: String,

    @Column(nullable = false)
    open var email: String,

    @Embedded
    open var password: Password,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open var role: Role,

    @Column(nullable = false, updatable = false)
    @CreatedDate
    open var createdAt: Instant?,

    @Column(nullable = false)
    @LastModifiedDate
    open var updatedAt: Instant?
) {

    fun validatePassword(password: String) {
        this.password.validate(password)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false

        if (memberId != other.memberId) return false

        return true
    }

    override fun hashCode(): Int {
        return memberId?.hashCode() ?: 0
    }
}