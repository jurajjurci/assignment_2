package sk.jurci.core_repository.mediator

import sk.jurci.core_database.model.MovieEntity

internal class Paging(
    val page: Int = 1,
    itemOrder: Long = 0,
) {

    var itemOrder: Long = itemOrder
        get() = field++
        private set

    constructor(lastItem: MovieEntity) : this(
        page = lastItem.page.inc(),
        itemOrder = lastItem.order.inc()
    )
}