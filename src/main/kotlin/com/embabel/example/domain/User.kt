package com.embabel.example.domain

import com.dataagent.core.annotation.DataAgent
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

@DataAgent(
    description = "Example JPA entity User",
    discoverable = true
)
@Entity
@Table(
    name = "JP_USER",
    uniqueConstraints = [UniqueConstraint(name = "UM_USERNAME_UK", columnNames = ["username"])]
)
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    @Column(name = "first_name", length = 50)
    @field:Size(max = 50)
    var firstName: String? = null,

    @Column(name = "last_name", length = 50)
    @field:Size(max = 50)
    var lastName: String? = null,

    @Column(length = 50, unique = true, nullable = false)
    @field:NotNull
    @field:Size(min = 1, max = 50)
    var username: String? = null,

    @Column(length = 100)
    @field:Size(min = 8, max = 100)
    var password: String? = null,

    @Column(length = 254)
    @field:Email
    @field:Size(min = 5, max = 254)
    var email: String? = null,

    @Column(name = "is_online", nullable = false)
    var online: Boolean = false,

    @Column(name = "is_activated", nullable = false)
    @field:NotNull
    var activated: Boolean = false,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "profile_id")
    var profile: Profile? = null
)
