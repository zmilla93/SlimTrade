package github.zmilla93.modules.data

/**
 * A HashMap that maps to an array of values.
 */
class HashMapList<T, U>(val allowRepeats: Boolean = true) {

    val map = HashMap<T, ArrayList<U>>()
    val entries get() = map.values

    fun put(key: T, value: U) {
        if (!allowRepeats) {
            if (containsValue(key, value)) return
        }
        val list: ArrayList<U>
        if (map.containsKey(key)) list = map[key]!!
        else {
            list = ArrayList()
            map[key] = list
        }
        list.add(value)
    }

    fun get(key: T): ArrayList<U>? {
        return map[key]
    }

    fun containsKey(key: T): Boolean {
        return map.containsKey(key)
    }

    fun containsValue(key: T, value: U): Boolean {
        val list = map[key] ?: return false
        return list.contains(value)
    }

    override fun toString(): String {
        return map.toString()
    }

}