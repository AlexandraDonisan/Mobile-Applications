package en.ubb.trippie_kotlin

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter internal constructor(
    context: Context,
    private val seeDetailsListener: (Word) -> Unit,
    private val deleteListener: (Word) -> Unit,
    private val updateListener: (Word) -> Unit
    ) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words =  emptyList<Word>() //Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val wordItemView : TextView = itemView.findViewById(R.id.textView)

        fun bind(pos: Int, listener: (Int) -> Unit) = with(itemView){
            val wordItemView : TextView = itemView.findViewById(R.id.textView)
            val deleteButton : ImageButton = itemView.findViewById(R.id.delete_btn)
            val updateButton : ImageButton = itemView.findViewById(R.id.update_btn)
            wordItemView.setOnClickListener{
                seeDetailsListener(words[pos])
//                Toast.makeText(
//                    context,
//                    "Item $pos was clicked",
//                    Toast.LENGTH_LONG
//                ).show()
            }
            deleteButton.setOnClickListener {
                deleteListener(context, pos, deleteListener)
            }

            updateButton.setOnClickListener {
                updateListener(context, pos, updateListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.word
        val listener : (Int) -> Unit = {}
        holder.bind(position, listener)
    }

    override fun getItemCount() = words.size

    internal fun setWords(words: List<Word>){
        this.words = words
        notifyDataSetChanged()
    }

    internal fun deleteListener(context: Context, pos: Int, listener: (Word) -> Unit){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Do you really want to delete this sightseeing?")
        builder.setPositiveButton("Yes"){_, _ ->
            listener(words[pos])
        }
        builder.setNegativeButton("No"){_,_ ->
            Toast.makeText(context, "Sightseeing will not be removed!", Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    internal fun updateListener(context: Context, pos: Int, listener: (Word) -> Unit){
        listener(words[pos])
    }
}
