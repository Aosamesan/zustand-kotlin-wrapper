import js.objects.jso
import react.FC
import react.StrictMode
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import web.cssom.pct
import web.dom.document
import zustand.useStore

fun main() {
    val root = document.getElementById("root")!!
    createRoot(root).render(App.create())
}

val App = FC {
    StrictMode {
        AppStateContext.Provider {
            value = AppStateStore
            Reset()
            Counter()
            Echo()
        }
    }
}

val Reset = FC {
    val reset = AppStateContext.useStore(AppState::reset)
    button {
        style = jso {
            width = 100.pct
        }
        onClick = { reset() }
        +"Reset"
    }
}

val Counter = FC {
    val count = AppStateContext.useStore(AppState::count)
    val increment = AppStateContext.useStore(AppState::increment)
    val decrement = AppStateContext.useStore(AppState::decrement)
    val addCount = AppStateContext.useStore(AppState::addCount)
    val subtractCount = AppStateContext.useStore(AppState::subtractCount)

    h1 {
        +"Count : $count"
    }
    div {
        button {
            onClick = { increment() }
            +"Increment"
        }
        button {
            onClick = { decrement() }
            +"Decrement"
        }
        button {
            onClick = { addCount(10) }
            +"Add 10"
        }
        button {
            onClick = { subtractCount(10) }
            +"Subtract 10"
        }
    }
}

val Echo = FC {
    val message = AppStateContext.useStore(AppState::message)
    val setMessage = AppStateContext.useStore(AppState::setMessage)

    h1 {
        if (message.isEmpty()) {
            +"Empty Message"
        } else {
            +message
        }
    }
    div {
        input {
            value = message
            onChange = {
                setMessage(it.target.value)
            }
        }
    }
}