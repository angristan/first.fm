package fr.esgi.firstfm.data.User

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.TopAlbumsResponse
import fr.esgi.firstfm.entity.TopArtistsResponse


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class UserRepository(val dataSource: UserDataSource) {
    fun getTopAlbums(
        activity: AppCompatActivity,
    ): Result<TopAlbumsResponse> {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        return dataSource.getTopAlbums(username!!)
    }

    fun getTopArtists(
        activity: AppCompatActivity,
    ): Result<TopArtistsResponse> {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        return dataSource.getTopArtists(username!!)
    }
}