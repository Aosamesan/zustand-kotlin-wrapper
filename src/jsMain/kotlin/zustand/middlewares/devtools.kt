package zustand.middlewares

import zustand.StateCreator

external interface DevtoolsOptions {
    var name: String?
    var enabled: Boolean?
    var anonymousActionType: String?
    var store: String?
}

fun <T> devtools(creator: StateCreator<T>): StateCreator<T> {
    return devtools(creator, null)
}

fun <T> devtools(creator: StateCreator<T>, options: DevtoolsOptions?): StateCreator<T> {
    return if (options == null) {
         middlewaresModule.devtools(creator)
    } else {
        middlewaresModule.devtools(creator, options)
    }.unsafeCast<StateCreator<T>>()
}