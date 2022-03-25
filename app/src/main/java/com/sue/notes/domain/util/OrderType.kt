package com.sue.notes.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

fun Boolean.toOrderType() {

}