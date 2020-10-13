package com.firebase.firebasedatabase.data

import com.google.firebase.database.Exclude

data class Auther(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    @get:Exclude
    var isDeleted: Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return if (other is Auther)
        {
            other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }


}