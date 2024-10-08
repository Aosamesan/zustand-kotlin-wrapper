package zustand

external interface SetStateByState<T> {
    @JsName("call")
    operator fun invoke(self: dynamic = definedExternally, state: T)
}

external interface SetStateByTransform<T> {
    @JsName("call")
    operator fun invoke(self: dynamic = definedExternally, transform: (T) -> T)
}

external interface SetState<T> : SetStateByState<T>, SetStateByTransform<T>

@JsModule("zustand/vanilla")
@JsNonModule
@JsName("createStore")
external val createStoreModule: dynamic

@JsModule("zustand/vanilla")
@JsNonModule
external interface StoreApi<T> : ReadonlyStoreApi<T> {
    override var getState: () -> T
    var setState: dynamic
}

typealias StateCreator<T> = (setState: SetState<T>, getState: () -> T, store: StoreApi<T>) -> T

fun <T> createStore(initializer: StateCreator<T>): StoreApi<T> {
    return createStoreModule.createStore(initializer).unsafeCast<StoreApi<T>>()
}

fun <T> createStore(): ((initializer: StateCreator<T>) -> StoreApi<T>) {
    return { initializer ->
        createStore(initializer)
    }
}