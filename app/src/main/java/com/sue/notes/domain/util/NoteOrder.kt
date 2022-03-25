package com.sue.notes.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(
        orderType: OrderType = OrderType.Ascending
    ): NoteOrder(orderType)

    class Date(
        orderType: OrderType = OrderType.Ascending
    ): NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }
}
