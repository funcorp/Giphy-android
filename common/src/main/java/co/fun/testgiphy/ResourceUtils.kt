package co.`fun`.testgiphy

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.AnyRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import arrow.core.Try
import arrow.core.identity
import co.`fun`.testgiphy.common.R
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ResourceUtils @Inject constructor(private val context: Context) {

    fun getResources(): Resources = context.resources

    fun getPrimaryColor(): Int = getColorSafe(R.color.colorPrimary)

    fun getPrimaryDarkColor(): Int = getColorSafe(R.color.colorPrimaryDark)

    fun getAccentColor(): Int = getColorSafe(R.color.colorAccent)

    fun getColorSafe(@ColorRes colorRes: Int): Int =
        Try
            .invoke { ContextCompat.getColor(context, colorRes) }
            .fold(
                ifFailure = { android.R.color.transparent },
                ifSuccess = ::identity
            )

    fun getResNameById(@AnyRes resId: Int) = context.resources.getResourceName(resId)

    fun getResIdByName(resName: String) =
        try {
            context.resources.getIdentifier(resName, "drawable", context.packageName)
        } catch (ignore: Exception) {
            0
        }

    fun getDrawableByResName(resName: String): Drawable? =
        getResIdByName(resName)
            .let(::getDrawable)

    fun getDrawable(@DrawableRes drawableRes: Int) = try {
        ContextCompat.getDrawable(context, drawableRes)
    } catch (e: Exception) {
        AppCompatResources.getDrawable(context, drawableRes)
    }

    fun getColorDrawable(color: Int): Drawable = ColorDrawable(getColorSafe(color))

    fun getString(@StringRes stringRes: Int): String = context.getString(stringRes).orEmpty()

    fun getString(@StringRes stringRes: Int, vararg any: Any): String =
        context.getString(stringRes, *any).orEmpty()

    fun getDimension(@DimenRes dimenRes: Int): Float = try {
        context.resources.getDimension(dimenRes)
    } catch (e: Resources.NotFoundException) {
        0f
    }

    fun getInteger(@IntegerRes integerRes: Int, defaultValue: Int?): Int = try {
        context.resources.getInteger(integerRes)
    } catch (e: Exception) {
        defaultValue ?: 0
    }

    fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int = try {
        context.resources.getDimensionPixelSize(dimenRes)
    } catch (e: Resources.NotFoundException) {
        0
    }

    fun getQuantityString(id: Int, quantity: Int, formatArgs: Any): String {
        return context.resources.getQuantityString(id, quantity, formatArgs)
    }
}
