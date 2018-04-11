package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.Cor;

public class CoresAdapter extends RecyclerView.Adapter<CoresAdapter.CoresViewHolder> {

    private Context context;
    private List<Cor> cores;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CoresAdapter(Context context, List<Cor> cores) {
        this.context = context;
        this.cores = cores;
    }

    @Override
    public CoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_cores, parent, false);
        return new CoresViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(CoresViewHolder holder, int position) {
        Cor cor = cores.get(position);
        holder.vincula(cor);
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    class CoresViewHolder extends RecyclerView.ViewHolder {

        private Cor cor;

        public CoresViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(cor);
                }
            });
        }

        public void vincula(Cor cor) {
            this.cor = cor;
            Drawable drawable = itemView.getBackground();
            drawable.setColorFilter(Color.parseColor(cor.toString()), PorterDuff.Mode.SRC_ATOP);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Cor cor);
    }

}
