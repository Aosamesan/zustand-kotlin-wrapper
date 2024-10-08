import js.objects.jso
import zustand.StateCreator
import zustand.createStore
import zustand.createStoreContext
import zustand.middlewares.devtools
import zustand.middlewares.persist
import zustand.middlewares.zustandLogger

external interface AppState {
    var count: Int
    var message: String

    var increment: () -> Unit
    var decrement: () -> Unit
    var addCount: (Int) -> Unit
    var subtractCount: (Int) -> Unit

    var setMessage: (String) -> Unit

    var reset: () -> Unit
}

val createAppState: StateCreator<AppState> = { set, get, api ->
    jso {
        // state
        count = 0
        message = ""

        // actions
        increment = {
            set { state ->
                jso {
                    count = state.count + 1
                }
            }
        }

        decrement = {
            set { state ->
                jso {
                    count = state.count - 1
                }
            }
        }

        addCount = { num ->
            set { state ->
                jso {
                    count = state.count + num
                }
            }
        }

        subtractCount = { num ->
            set { state ->
                jso {
                    count = state.count - num
                }
            }
        }

        setMessage = { message ->
            set(state = jso {
                this.message = message
            })
        }

        reset = {
            set(state = api.getInitialState())
        }
    }
}

val AppStateStore = createStore(
    devtools(persist(zustandLogger(createAppState), jso {
        name = "ZustandAppStateTests"
    }))
)

val AppStateContext = createStoreContext<AppState>()