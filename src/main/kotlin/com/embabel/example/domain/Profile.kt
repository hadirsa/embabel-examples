package com.embabel.example.domain

import com.dataagent.core.annotation.DataAgent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@DataAgent(
    description = "Example JPA entity Profile",
    discoverable = true
)
@Entity
@Table(name = "JP_PROFILE")
class Profile(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    var nationalCode: String? = null,
    var birthPlace: String? = null,
    var birthday: String? = null,
    var fatherName: String? = null,
    var mobileNumber: String? = null,
    var personnelCode: String? = null,
    var photoFileId: String? = null,

    @OneToOne(mappedBy = "profile")
    var user: User? = null
)
