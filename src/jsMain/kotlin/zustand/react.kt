package zustand

external interface ReadonlyStoreApi<T> {
    var getState: () -> T
    var getInitialState: () -> T
    var subscribe: (listener: (state: T, prevState: T) -> Unit) -> () -> Unit
}

@JsModule("zustand")
@JsNonModule
@JsName("useStore")
external val useStoreModule: dynamic

fun <T> useStore(store: ReadonlyStoreApi<T>): T {
    return useStoreModule.useStore(store).unsafeCast<T>()
}

fun <T, U> useStore(store: ReadonlyStoreApi<T>, selector: (T) -> U): U {
    return useStoreModule.useStore(store, selector).unsafeCast<U>()
}