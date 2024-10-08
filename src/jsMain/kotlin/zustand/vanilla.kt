package zustand

import js.objects.JsoDsl
import js.objects.jso

external interface SetState<T> {
    @JsName("call")
    operator fun invoke(self: dynamic = definedExternally, state: T)

    @JsName("call")
    operator fun invoke(self: dynamic = definedExternally, transform: (T) -> T)
}

fun <T : Any> SetState<T>.stateAction(state: T) {
    this.invoke(null, state)
}

fun <T : Any> SetState<T>.builderAction(builder: @JsoDsl T.() -> Unit) {
    stateAction(jso(builder))
}

fun <T : Any> SetState<T>.transformAction(transform: (T) -> T) {
    this.invoke(null, transform)
}

@JsModule("zustand/vanilla")
@JsNonModule
@JsName("createStore")
external val createStoreModule: dynamic

@JsModule("zustand/vanilla")
@JsNonModule
external interface StoreApi<T> : ReadonlyStoreApi<T> {
    var setState: SetState<T>
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