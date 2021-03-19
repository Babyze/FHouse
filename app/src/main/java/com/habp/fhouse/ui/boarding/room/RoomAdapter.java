package com.habp.fhouse.ui.boarding.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Room;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    List<Room> listRoom;

    public RoomAdapter() {
    }

    public List<Room> getListRoom() {
        return listRoom;
    }

    public void setListRoom(List<Room> listRoom) {
        this.listRoom = listRoom;
    }

    public RoomAdapter(List<Room> listRoom) {
        this.listRoom = listRoom;
    }

    @Override
    public int getCount() {
        return listRoom.size();
    }

    @Override
    public Object getItem(int i) {
        return listRoom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.room_item, viewGroup, false);
        }
        Room itemRoom = listRoom.get(i);
        Glide.with(view).load(itemRoom.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgRoom));
        TextView txtRoomName = view.findViewById(R.id.txtRoomName);
        txtRoomName.setText(itemRoom.getRoomName());
        return view;
    }
}
