package org.the_chance.honeymart.ui.addCategory

import android.util.Log
import org.the_chance.design_system.R

data class CategoryImage(
    val categoryImageId: Int = 0,
    val categoryImage: Int = 0
)

val categoryImages =
    (1..20).map { CategoryImage(it, getResourceIdByName("ic_$it", R.drawable::class.java)) }

private fun getResourceIdByName(resourceName: String, resourceType: Class<*>): Int {
    return try {
        val idField = resourceType.getDeclaredField(resourceName)
        idField.getInt(idField)
    } catch (e: Exception) {
        Log.i("listCategoryImages", e.message.toString())
    }
}