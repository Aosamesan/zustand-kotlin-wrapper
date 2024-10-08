package zustand

typealias EqualityFunction<U> = (a: U, b: U) -> Boolean

external interface Traditional {
    @JsName("createWithEqualityFn")
    fun <T> createWithEqualityFn(
        initializer: dynamic,
        defaultEqualityFn: EqualityFunction<*>? = definedExternally
    ): StoreApi<T>

    @JsName("useStoreWithEqualityFn")
    fun <T, TSlice> useStoreWithEqualityFn(
        api: ReadonlyStoreApi<T>,
        selector: ((T) -> TSlice) = definedExternally,
        defaultEqualityFn: EqualityFunction<TSlice>? = definedExternally
    ): TSlice
}

@JsModule("zustand/traditional")
@JsNonModule
private external val traditional: Traditional

fun <T> useStoreWithEqualityFn(api: ReadonlyStoreApi<T>): T {
    return traditional.useStoreWithEqualityFn(api)
}

fun <T, Slice> useStoreWithEqualityFn(api: ReadonlyStoreApi<T>, selector: (T) -> Slice): Slice {
    return traditional.useStoreWithEqualityFn(api, selector)
}

fun <T, Slice> useStoreWithEqualityFn(api: ReadonlyStoreApi<T>, selector: (T) -> Slice, equalityFn: EqualityFunction<Slice>): Slice {
    return traditional.useStoreWithEqualityFn(api, selector, equalityFn)
}