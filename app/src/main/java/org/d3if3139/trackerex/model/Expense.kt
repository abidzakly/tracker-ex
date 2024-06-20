package org.d3if3139.trackerex.model

data class Expense(
    val user_email: String,
    val created_at: String,
    val type: String,
    val price: Int,
    val title: String,
    val id: Int,
    val image_id: String
)