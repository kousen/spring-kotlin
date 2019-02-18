package com.kousenit.persistence.entities

import javax.persistence.*

@Entity
@Table(name = "officers")
class Officer(
        @Enumerated(EnumType.STRING) var rank: Rank,
        @Column(name = "first_name") var first: String,
        @Column(name = "last_name") var last: String,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null)