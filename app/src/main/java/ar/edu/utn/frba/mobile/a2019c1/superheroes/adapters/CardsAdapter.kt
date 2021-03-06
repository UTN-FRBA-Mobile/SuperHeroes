package ar.edu.utn.frba.mobile.a2019c1.superheroes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.a2019c1.superheroes.R
import ar.edu.utn.frba.mobile.a2019c1.superheroes.R.drawable
import ar.edu.utn.frba.mobile.a2019c1.superheroes.domain.Card
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.linearlayout_card.view.*

class CardsAdapter(
	private val clickListener: ((Card, View) -> Any)? = null,
	private val longClickListener: ((Card, View) -> Boolean)? = null
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

	private var cards = emptyList<Card>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater
		.from(parent.context).inflate(R.layout.linearlayout_card, parent, false)
		.let { view ->
			ViewHolder(view)
		}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val card = cards[position]
		val imageView = holder.containerView.imageview_card
		val nameTextView = holder.containerView.textview_card_name
		val powerTextView = holder.containerView.textview_card_power
		Picasso.get()
			.load(card.thumbnail)
			.placeholder(drawable.ic_superhero)
			.error(drawable.ic_superhero)
			.resize(300, 300)
			.centerCrop()
			.into(imageView)
		nameTextView.text = card.name
		powerTextView.text = holder.containerView.context.getString(R.string.text_card_power, card.power)
		if (longClickListener != null) {
			holder.bindLongClick(card, longClickListener)
		}
		if (clickListener != null) {
			holder.bindClick(card, clickListener)
		}
	}

	override fun getItemId(position: Int) = position.toLong()

	override fun getItemCount() = cards.size

	override fun getItemViewType(position: Int) = position

	fun replaceItems(_cards: List<Card>) {
		cards = _cards
		notifyDataSetChanged()
	}

	fun hasCards() = cards.isNotEmpty()

	inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

		fun bindLongClick(card: Card, longClickListener: (Card, View) -> Boolean) {
			containerView.setOnLongClickListener { longClickListener(card, containerView) }
		}

		fun bindClick(card: Card, clickListener: (Card, View) -> Any) {
			containerView.setOnClickListener { clickListener(card, containerView) }
		}
	}

}
