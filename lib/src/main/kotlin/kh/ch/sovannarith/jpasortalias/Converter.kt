package kh.ch.sovannarith.jpasortalias

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

object Converter {
    fun <T> Class<T>.convert(pageable: Pageable): Pageable {
        var newPageable = pageable
        this.javaClass.declaredFields.map {
            it.name to it.annotations.filterIsInstance<SortAlias>().firstOrNull()
        }.filterNot { it.second == null }.firstOrNull()
            ?.let {
                newPageable = if (it.second == null) pageable
                else if (it.second!!.name.contains(pageable.sort.first().property)) {
                    val newSort = Sort.by(pageable.sort.first().direction, it.first)
                    PageRequest.of(pageable.pageNumber, pageable.pageSize, newSort)
                } else pageable
                it
            }
        return newPageable
    }
}