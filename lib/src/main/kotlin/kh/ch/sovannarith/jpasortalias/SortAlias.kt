package kh.ch.sovannarith.jpasortalias

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@MustBeDocumented
annotation class SortAlias(
    vararg val name: String = []
)