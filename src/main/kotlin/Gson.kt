import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Test class for random stuff
 */


fun main() {

    //intMap()
    //floatMap()
    stringMap()

}

fun intMap(){
    val map = mutableMapOf<Int, String>()
    map[1] = "One"
    map[2] = "Two"
    map[3] = "Three"
    map[4] = "Four"
    map[5] = "Five"

    val json = Gson().toJson(map)

    println(json)

    val deserialize = Gson().fromJson(json, HashMap::class.java)
    println(deserialize)

    val type = object : TypeToken<HashMap<Int, String>>(){}.type
    val des1 = Gson().fromJson<HashMap<Int, String>>(json, type)
    println(des1)
}

fun floatMap(){
    val map = mutableMapOf<Float, String>()
    map[1.1f] = "One"
    map[2.2f] = "Two"
    map[3.1f] = "Three"
    map[4.1f] = "Four"
    map[5.1f] = "Five"

    val json = Gson().toJson(map)

    println(json)

    val deserialize = Gson().fromJson(json, HashMap::class.java)
    println(deserialize)

    val type = object : TypeToken<HashMap<Float, String>>(){}.type
    val des1 = Gson().fromJson<HashMap<Float, String>>(json, type)
    println(des1)
}

fun stringMap(){
    val map = mutableMapOf<String, String>()
    map["one"] = "One"
    map["two"] = "Two"
    val json = Gson().toJson(map)

    println(json)

    //val deserialize = Gson().fromJson(json, HashMap::class.java)
    //println(deserialize)

    val type = object : TypeToken<HashMap<Float, String>>(){}.type // Should throw an exception
    val des1 = Gson().fromJson<HashMap<Float, String>>(json, type) // Should throw an exception
    println(des1)
}

