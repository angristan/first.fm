package fr.esgi.firstfm.album.albumList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.objects.Album

class AlbumAdapter(private val albums: List<Album>, private val onAlbumClickedListener: AlbumViewHolder.OnAlbumClickedListener) :
    RecyclerView.Adapter<AlbumViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int)
    {
        val album = albums[position]
        holder.bind(album, onAlbumClickedListener)
    }

}