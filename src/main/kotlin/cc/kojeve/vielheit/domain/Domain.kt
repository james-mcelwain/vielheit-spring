package cc.kojeve.vielheit.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column
    @CreatedDate
    var createDate: LocalDateTime? = null

    @Column
    @CreatedBy
    var createUser: String? = null

    @Column
    @LastModifiedDate
    var updateDate: LocalDateTime? = null

    @Column
    @LastModifiedBy
    var updateUser: String? = null
}