package zustand.middlewares

import zustand.SetState
import zustand.StateCreator

fun <T> zustandLogger(stateCreator: StateCreator<T>): StateCreator<T> {
    // Do not shadow `set`. It is used within `js` function call.
    return { set, get, api ->
        val newSet: SetState<T> = ({ action: dynamic ->
            val previousState = get()
            js("console.group('%caction %c%s', 'color:red;font-weight:bold;', 'color:inherit;font-weight:inherit;', action.name || 'Partial Update')")
            console.log("%cprev state %o", "color:grey;font-weight:bold;", previousState)
            console.log("%caction %o", "color:blue;font-weight:bold;", action)
            // kotlin/js uses ES5. `set(...arguments)` does not work.
            js("set.apply(null, arguments)")
            val nextState = get()
            console.log("%cnext state %o", "color:green;font-weight:bold;", nextState)
            js("console.groupEnd()")
        }).unsafeCast<SetState<T>>()

        stateCreator(newSet, get, api)
    }
}