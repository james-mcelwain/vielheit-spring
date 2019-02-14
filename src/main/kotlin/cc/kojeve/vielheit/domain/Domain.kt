package cc.kojeve.vielheit.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
abstract class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column
    @CreatedDate
    var createDate: LocalDateTime? = null

    @Column
    @LastModifiedDate
    var updateDate: LocalDateTime? = null
}