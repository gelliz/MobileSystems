package example.fit.bstu.by.lab11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    //2 a
    val num: Int = 3
    val str: String = "str_val"
    val doub: Double = 3.4

    var num2 = 4
    var str2 = "str_val"
    var doub2 = 4.5

    //2 b
    var byte: Byte = 1
    var integer: Int = byte.toInt()
    private val string: String = integer.toString()

    companion object {
        //2 d
        const val max_time: Int = 30
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //2 c
        if (string is String) {
            Log.d("tag", "2c - " + string)
        }
        if (integer is Int) {
            Log.d("tag", "2c - $integer: int")
        }

        //2 e
        val num_or_str: Int? = null
        Log.d("tag", "2e - " + num_or_str.toString())

        //3 a
        Log.d("tag", "3a - " + sum(2.2, 4.5, 3.3).toString())

        //3 b
        Log.d("tag", "3b - " + isValid("login@mail.com", "1234567").toString())

        //3 c
        var HolyDay = Holidays.Christmas
        HolyDay.isHoliday()
        var Day = Holidays.Day2
        Day.isHoliday()

        //3 d
        Log.d("tag", "3d - " + doOperation(5, 4, '/').toString())

        //3 e
        val numarr = intArrayOf(1, 4, 2, 53, 78, 2, 94, 34, 21)
        numarr.indexOfMax(numarr)

        //3 f
        Log.d("tag", "3f - " + "hell".coincidence("helm").toString())

        //4 a
        Log.d("tag", "4a - " + factorial(5).toString())
        Log.d("tag", "4a - " + factorial2(5).toString())

        //4 b
        var primeNumbericList: ArrayList<Int> = arrayListOf()
        var primeNumbericArray: Array<Int> = arrayOf(0,0,0,0,0,0,0,0,0,0)
        var indexArray = 0
        for (i in 2 until 10000) {
            if (isPrime(i)) {
                if (primeNumbericList.size < 20)
                    primeNumbericList.add(i)
                else {
                    primeNumbericArray[indexArray] = i
                    indexArray++
                }
                if (indexArray == 10)
                    break
            }
        }
        Log.d("tag", "4b - primeList: ")
        primeNumbericList.forEach { Log.d("tag", it.toString()) }
        Log.d("tag", "4b - primeArray: ")
        primeNumbericArray.forEach { Log.d("tag", it.toString()) }

        //5 a
        var numList = ArrayList<Int>()
        numList.add(2)
        numList.add(4)
        numList.add(5)
        numList.add(8)

        var firstRes: Boolean = containsIn(numList) { x -> x % 3 == 0}
        Log.d("tag", "5a - first lambda: " + firstRes)

        var secondRes: Boolean = containsIn(numList) { x -> x  - 5 > 0}
        Log.d("tag", "5a - second lambda: " + secondRes)

        //5 b
        nums += 10
        nums += 11
        nums += 12

        //только уникальные
        var set: Set<Int> = LinkedHashSet(nums)

        //только нечетные
        nums = nums.filter { it % 2 != 0 }

        //сслыка на функцию
        nums = nums.filter(::isPrime)

        set.forEach { Log.d("tag", "5b - " + it.toString()) }

        nums.find { it == 2 }
        nums.groupBy { it }
        nums.all { it > 10 }
        nums.any { it < 20 }

        //Деструктуризация
        val data = listOf("qwe", "asd", "zxc")
        val (a, b, _) = data
        Log.d("tag", "5b - $a , $b")

        //5 c
        for (map in map_marks) {
            when {
                map.value == 40 -> map.setValue(10)
                map.value == 39 -> map.setValue(9)
                map.value == 38 -> map.setValue(8)
                map.value in 35..37 -> map.setValue(7)
                map.value in 32..34 -> map.setValue(6)
                map.value in 29..31 -> map.setValue(5)
                map.value in 25..28 -> map.setValue(4)
                map.value in 22..24 -> map.setValue(3)
                map.value in 19..21 -> map.setValue(2)
                map.value in 0..18 -> map.setValue(1)
                else -> Log.d("tag", "5c - error")
            }
        }
        Log.d("tag", "5c - $map_marks")

        var mark_10 = 0
        var mark_9 = 0
        var mark_8 = 0
        var mark_7 = 0
        var mark_6 = 0
        var mark_5 = 0
        var mark_4 = 0
        var mark_3 = 0
        var mark_2 = 0
        var mark_1 = 0

        for (map in map_marks) {
            when {
                map.value == 10 -> mark_10++
                map.value == 9 -> mark_9++
                map.value == 8 -> mark_8++
                map.value == 7 -> mark_7++
                map.value == 6 -> mark_6++
                map.value == 5 -> mark_5++
                map.value == 4 -> mark_4++
                map.value == 3 -> mark_3++
                map.value == 2 -> mark_2++
                map.value == 1 -> mark_1++
            }
        }
    }

    //3 a
    fun sum(vararg values: Double): Double {
        var sum: Double = 0.0
        for (n in values) {
            sum += n
        }
        return sum
    }

    //3 b
    fun isValid(login: String, password: String): Boolean {
        val notNull: () -> Boolean = { login.isNotEmpty() && password.isNotEmpty() }
        return (notNull() && android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()
                && password.trim().length >= 6
                && password.trim().length <= 12)
    }

    //3 c
    enum class Holidays(val date: String) {
        Christmas("25.12"),
        NewYear("31.12"),
        IndependentDay("03.07"),
        Day("02.02"),
        Day2("03.02");

        fun isHoliday() {
            if (date.isEmpty() || date.length != 5) {
                throw Exception()
            } else {
                when (date) {
                    Christmas.date -> Log.d("tag", "3c - " + Christmas.name)
                    NewYear.date -> Log.d("tag", "3c - " + NewYear.name)
                    IndependentDay.date -> Log.d("tag", "3c - " + IndependentDay.name)
                    else -> Log.d("tag", "3c - It's not a holiday")
                }
            }
        }
    }

    //3 d
    fun doOperation(a: Int, b: Int, operation: Char): Double {
        when (operation) {
            '+' -> return a.toDouble() + b.toDouble()
            '-' -> return a.toDouble() - b.toDouble()
            '*' -> return a.toDouble() * b.toDouble()
            '/' -> return a.toDouble() / b.toDouble()
            else -> return 0.0
        }
    }

    //4 a
    fun factorial(n: Int): Double {
        var result = 1
        var n2 = n
        while (n2 > 1) {
            result *= n2
            n2--
        }
        return result.toDouble()
    }

    fun factorial2(n: Int): Double = if (n < 2) 1.0 else n * factorial2(n - 1)

    //4 b
    fun isPrime(n: Int): Boolean {
        val n_sqrt = Math.sqrt(n.toDouble())
        for (i in 2..n_sqrt.toInt()) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    //5 a
    fun containsIn(collections: Collection<Int>, operation: (item: Int) -> Boolean): Boolean =
        collections.any { item -> operation(item) }

    //5 b
    var nums = listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 0)

    //5c
    var map_marks = mutableMapOf("Ivanov" to 35, "Petrov" to 24, "Sidorov" to 15)

}

//3 e
fun IntArray.indexOfMax(a: IntArray): Int? {
    val num = a.max()
    var count = 0
    for (i in a) {
        if (i == num) {
            Log.d("tag", "3e -  index : $count")
            break
        }
        count++
    }
    return count
}

//3 f
fun String.coincidence(s: String): Int? {
    var i = 0
    var count = 0
    for (ss in s) {
        if (this.get(i) == ss) {
            count++
        }
        i++
    }
    return count
}
