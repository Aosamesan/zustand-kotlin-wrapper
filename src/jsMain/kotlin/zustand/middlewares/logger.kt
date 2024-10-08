package zustand.middlewares

import zustand.SetState
import zustand.StateCreator

private fun <T> groupedLog(previousState: T, nextState: T, action: dynamic) {
    js("console.group('%caction %c%s', 'color:red;font-weight:bold;', 'color:inherit;font-weight:inherit;', action.name || 'Partial Update')")
    console.log("%cprev state %o", "color:grey;font-weight:bold;", previousState)
    console.log("%caction %o", "color:blue;font-weight:bold;", action)
    console.log("%cnext state %o", "color:green;font-weight:bold;", nextState)
    js("console.groupEnd()")
}

private fun <T> spreadLog(previousState: T, nextState: T, action: dynamic) {
    console.log("%caction %c%s", "color:red;font-weight:bold;", "color:inherit;font-weight:inherit;", action.name ?: "Partial Update")
    console.log("%cprev state %o", "color:grey;font-weight:bold;", previousState)
    console.log("%caction %o", "color:blue;font-weight:bold;", action)
    console.log("%cnext state %o", "color:green;font-weight:bold;", nextState)
}

fun <T> zustandLogger(stateCreator: StateCreator<T>): StateCreator<T> {
    // Do not shadow `set`. It is used within `js` function call.
    return { set, get, api ->
        val newSet: SetState<T> = ({ action: dynamic ->
            val previousState = get()
            // kotlin/js uses ES5. `set(...arguments)` does not work.
            js("set.apply(null, arguments)")
            val nextState = get()
            if (jsTypeOf(js("console.group")) == "undefined") {
                spreadLog(previousState, nextState, action)
            } else {
                groupedLog(previousState, nextState, action)
            }
        }).unsafeCast<SetState<T>>()

        stateCreator(newSet, get, api)
    }
}