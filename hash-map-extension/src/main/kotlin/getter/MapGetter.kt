package getter

interface MapGetter<K, V> {
    operator fun get(key: K): V
}

fun <K, V> mapGetter(get: (K) -> V): MapGetter<K, V> = object : MapGetter<K, V> {
    override fun get(key: K): V = get(key)
}