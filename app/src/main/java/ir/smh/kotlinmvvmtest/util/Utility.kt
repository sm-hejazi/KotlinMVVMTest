package ir.smh.kotlinmvvmtest.util

object Utility {

    fun commaSeparate(digits: String): String {
        val ss = StringBuilder()
        var count = 0
        for (i in digits.length - 1 downTo 0) {
            count++
            if ((count == 3) and (i != 0)) {
                count = 0
                ss.append(digits[i])
                ss.append(",")
            } else
                ss.append(digits[i])
        }
        return ss.reverse().toString()
    }
}
