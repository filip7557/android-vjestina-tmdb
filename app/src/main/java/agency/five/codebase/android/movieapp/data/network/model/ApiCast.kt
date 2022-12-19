package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import agency.five.codebase.android.movieapp.model.Actor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCast(
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("cast_id")
    val cast_id: Int,

    @SerialName("character")
    val character: String,

    @SerialName("credit_id")
    val credit_id: String,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("id")
    val id: Int,

    @SerialName("known_for_department")
    val known_for_department: String,

    @SerialName("name")
    val name: String,

    @SerialName("order")
    val order: Int,

    @SerialName("original_name")
    val original_name: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("profile_path")
    val profile_path: String?
){
    fun toActor() = Actor(
        id = id,
        name = name,
        character = character,
        imageUrl = "$BASE_IMAGE_URL/$profile_path"
    )
}
