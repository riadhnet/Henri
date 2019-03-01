package net.riadh.henri.util

import java.text.NumberFormat
import java.util.*


fun getFormattedPrice(value: Int): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    //https://www2.1010data.com/documentationcenter/beta/1010dataUsersGuide/DataTypesAndFormats/currencyUnitCodes.html
    format.currency = Currency.getInstance("EUR")
    format.minimumFractionDigits = format.currency.defaultFractionDigits
    return format.format(value)
}