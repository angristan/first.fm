package fr.esgi.firstfm.album.albumList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.R
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.lastfmapi.AlbumFromTop
import fr.esgi.firstfm.lastfmapi.LastFmApi.retrieveArtistTopAlbumsInfoByMbId
import fr.esgi.firstfm.lastfmapi.LastFmApiArtistTopAlbumsGetInfoResponse
import kotlinx.android.synthetic.main.activity_album_recycler.*
import kotlinx.android.synthetic.main.fragment_main_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumRecyclerActivity : AppCompatActivity(), AlbumViewHolder.OnAlbumClickedListener,
        Callback<LastFmApiArtistTopAlbumsGetInfoResponse> {

    private var albums: List<AlbumFromTop>? = null

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, AlbumRecyclerActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_recycler)
        loader?.visibility = View.VISIBLE

        retrieveArtistTopAlbumsInfoByMbId("6cad3ce5-6380-4594-a8da-ae7d273b683d", "", this)
    }

    override fun onAlbumClicked(album: AlbumFromTop?) {
        if (album != null) {
            AlbumDetailActivity.navigateTo(this, album.mbId, album.artist.name, album.name)
        }
    }

    override fun onResponse(call: Call<LastFmApiArtistTopAlbumsGetInfoResponse>, response: Response<LastFmApiArtistTopAlbumsGetInfoResponse>) {
        deleteNameLessAlbumFromList(response.body()?.albums?.albums)
        loader?.visibility = View.GONE

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@AlbumRecyclerActivity)
            adapter = albums?.let { AlbumAdapter(it, this@AlbumRecyclerActivity) }
        }
    }

    override fun onFailure(call: Call<LastFmApiArtistTopAlbumsGetInfoResponse>, t: Throwable) {
        loader?.visibility = View.GONE
        val toast = Toast.makeText(applicationContext, "Error while loading list content", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()
    }

    private fun deleteNameLessAlbumFromList(albums: List<AlbumFromTop>?) {
        if (albums != null) {
            this.albums = albums.filter { x: AlbumFromTop? -> x?.name != "(null)" }
        }
    }
}