package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.model.Crewman
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCrew(
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("credit_id")
    val credit_id: String,

    @SerialName("department")
    val department: String,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("id")
    val id: Int,

    @SerialName("job")
    val job: String,

    @SerialName("known_for_department")
    val known_for_department: String,

    @SerialName("name")
    val name: String,

    @SerialName("original_name")
    val original_name: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("profile_path")
    val profile_path: String?
){
    fun toCrewman() = Crewman(
        id = id,
        name = name,
        job = job
    )
}
