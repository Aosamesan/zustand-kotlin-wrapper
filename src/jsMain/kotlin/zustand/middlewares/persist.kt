package zustand.middlewares

import js.promise.PromiseLike
import zustand.StateCreator

external interface StateStorage {
    var getItem: (name: String) -> PromiseLike<String?>
    var setItem: (name: String, value: String) -> PromiseLike<Unit>
    var removeItem: (name: String) -> PromiseLike<Unit>
}

external interface StorageValue<S> {
    var state: S
    var version: Number?
}

external interface PersistStorage<S> {
    var getItem: (name: String) -> PromiseLike<StorageValue<S>?>
    var setItem: (name: String, value: StorageValue<S>) -> PromiseLike<Unit>
    var removeItem: (name: String) -> PromiseLike<Unit>
}

external interface PersistOptions<S, PersistedState> {
    var name: String
    var storage: PersistStorage<PersistedState>?
    var partialize: ((S) -> PersistedState)?
    @JsName("onRehydrateStorage")
    var onRehydrateStorage: ((state: S) -> Unit)?
    @JsName("onRehydrateStorage")
    var onRehydrateStorageWithCallback: ((state: S) -> ((state: S?, error: Any?) -> Unit))?
    var version: Number?
    var migrate: ((persistedState: dynamic, version: Number) -> PromiseLike<PersistedState>)?
    var merge: ((persistedState: dynamic, currentState: S) -> S)?
    var skipHydration: Boolean?
}

fun <T, S> persist(creator: StateCreator<T>, options: PersistOptions<T, S>): StateCreator<T> {
    return middlewaresModule.persist(creator, options).unsafeCast<StateCreator<T>>()
}

fun <T> persist(creator: StateCreator<T>, options: PersistOptions<T, T>): StateCreator<T> {
    return middlewaresModule.persist(creator, options).unsafeCast<StateCreator<T>>()
}