package com.kousenit.reactiveofficers.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Officer(var rank: Rank,
              var first: String,
              var last: String,
              @Id var id: String? = null)