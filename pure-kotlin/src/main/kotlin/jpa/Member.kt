package jpa

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 환경에 따라서 엔티티 클래스를 인식 못할 수 있으니,
 * persistence.xml 에 내용을 추가해준다.
 * 간혹 이렇게 뜨는 경우가 있다. Unknown entity: jpa.Member
 */
@Table(name = "member")
@Entity
open class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = ""

    var age: Int = 0
}