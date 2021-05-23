package com.example.sohbetapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sohbetapp.Models.MessageModel;
import com.example.sohbetapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<String> userKeysList;
    Activity activity;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userId;
    List<MessageModel> messageModelList;
    Boolean state;
    int view_type_sent=1, view_type_recieved=2;


    public MessageAdapter(List<String> userKeyList, Activity activity, Context context,List<MessageModel> messageModelList) {
        this.userKeysList = userKeyList;
        this.activity = activity;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        userId = firebaseUser.getUid();
        this.messageModelList=messageModelList;
        state=false;
    }

    //layout tanımlaması yapılacak
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType==view_type_sent)
        {

           view= LayoutInflater.from(context).inflate(R.layout.message_sent_layout, parent, false);
            return new ViewHolder(view);
        }
        else
        {

            view= LayoutInflater.from(context).inflate(R.layout.message_received_layout, parent, false);
            return new ViewHolder(view);
        }

    }

    //viewlara setlemeler yapılacak
    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, int position) {
        holder.messageText.setText(messageModelList.get(position).getText());




    }

    //adapteri oluşturulacak olan listenin size'ı
    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    //viewların tanımlanma işlemleri
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        CircleImageView friend_image;
        CircleImageView friend_state_img;


        ViewHolder(View itemView) {
            super(itemView);
            if(state==true) {
                messageText = (TextView) itemView.findViewById(R.id.message_sent_text);
            }
            else
            {
                messageText=(TextView)itemView.findViewById(R.id.message_received_text);

            }



        }



    }

    @Override
    public int getItemViewType(int position) {
        if(messageModelList.get(position).getFrom().equals(userId))
        {
            state=true;
            return view_type_sent;
        }
        else
        {
            state=false;
            return view_type_recieved;


        }
    }
}