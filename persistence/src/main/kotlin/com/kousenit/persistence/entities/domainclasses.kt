package com.kousenit.persistence.entities

import javax.persistence.*

enum class Rank {
    ENSIGN, LIEUTENANT, COMMANDER, CAPTAIN, COMMODORE, ADMIRAL
}

@Entity
@Table(name = "officers")
class Officer(
        @Enumerated(EnumType.STRING) val rank: Rank,
        @Column(name = "first_name") val first: String,
        @Column(name = "last_name") val last: String,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null)
