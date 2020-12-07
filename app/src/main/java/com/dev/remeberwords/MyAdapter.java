package com.dev.remeberwords;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends ListAdapter<Word, MyAdapter.MyViewHolder> {

    private boolean viewFlag;
    private WordViewModel wordViewModel;
    protected MyAdapter(boolean viewFlag , WordViewModel wordViewModel) {
        super(new DiffUtil.ItemCallback<Word>(){

            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return (oldItem.getEnglishWord().equals(newItem.getEnglishWord())
                        && oldItem.getChineseMeaning().equals(newItem.getChineseMeaning())
                        && oldItem.isShowFlag() == newItem.isShowFlag());
            }
        });
        this.viewFlag = viewFlag;
        this.wordViewModel = wordViewModel;
    }

    @NonNull
    @Override
    //2、加载布局文件
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if(viewFlag){
            itemView = layoutInflater.inflate(R.layout.cell_card,parent,false);
        }else{
            itemView = layoutInflater.inflate(R.layout.cell_normal,parent,false);
        }
        final MyViewHolder holder = new MyViewHolder(itemView);
        //每项被点击的时候执行的跳转动作
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q="+holder.textViewEnglish.getText().toString().trim());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        //switch 开关监听的实现，注意设置一个Tag,用来取回Word实例
        holder.showFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Word word = (Word) holder.itemView.getTag(R.id.viewtype_for_word);
                if(isChecked){
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setShowFlag(true);
                    wordViewModel.updateWords(word);
                }else{
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setShowFlag(false);
                    wordViewModel.updateWords(word);
                }
            }
        });
        return holder;
    }

    @Override
    //3、绑定显示数据
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Word word = getItem(position);
        //设置个tag,被点击的时候好取得的word
        holder.itemView.setTag(R.id.viewtype_for_word,word);
        holder.textViewNum.setText(String.valueOf(position));
        holder.textViewEnglish.setText(word.getEnglishWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        if(word.isShowFlag()){
            holder.textViewChinese.setVisibility(View.GONE);
            holder.showFlag.setChecked(true);
        }else{
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.showFlag.setChecked(false);
        }
    }

    @Override
    //界面重新进入的时候保证序号显示正确
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.textViewNum.setText(String.valueOf(holder.getAdapterPosition()+1));
    }

    // 1、 先定义一个内部类-取得每一个单元里的元素
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNum,textViewEnglish,textViewChinese;
        Switch showFlag;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNum = itemView.findViewById(R.id.textViewNum);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            showFlag = itemView.findViewById(R.id.showFlag);
        }
    }
}
