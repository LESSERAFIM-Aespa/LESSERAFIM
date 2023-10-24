package kr.sparta.tripmate.util.method

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
import java.util.regex.Pattern

fun Context.shortToast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun Context.longToast(message: String, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, time).show()
}

fun TabLayout.Tab.setIcon(context: Context, @DrawableRes resourceId: Int) {
    this.icon = ContextCompat.getDrawable(context, resourceId)
}

fun removeHtmlTags(input: String): String {
    val pattern = Pattern.compile("<.*?>")
    return pattern.matcher(input).replaceAll("")
}

fun Int.toMoneyFormat(): String = DecimalFormat("#,###").format(this)
fun Int.toTimeFormat(): String = DecimalFormat("00").format(this)

/**
 * 작성자: 서정한
 * 내용: 금액에 콤마 추가
 * */
fun setCommaForMoneeyText(money: String): String {
    val commaCount = if (money.length % 3 == 0) {
        (money.length / 3) - 1
    } else {
        money.length / 3
    }
    var commaStartIndex = money.length % 3
    val comma = ','
    val stringBuilder = StringBuilder()
    stringBuilder.append(money)

    for (i in 0 until commaCount) {
        stringBuilder.insert(commaStartIndex, comma)
        commaStartIndex += 4
    }

    return "${stringBuilder}원"
}
