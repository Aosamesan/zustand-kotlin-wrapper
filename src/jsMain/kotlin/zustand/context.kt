package zustand

import react.RequiredContext
import react.createRequiredContext
import react.useRequiredContext

typealias StoreContext<T> = RequiredContext<StoreApi<T>>

fun <T> createStoreContext(): StoreContext<T> = createRequiredContext()

fun <T> StoreContext<T>.useStore(): T = useStore(useRequiredContext(this))

fun <T, U> StoreContext<T>.useStore(selector: (T) -> U) = useStore(useRequiredContext(this), selector)