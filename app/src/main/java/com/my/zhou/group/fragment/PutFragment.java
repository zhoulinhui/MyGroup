package com.my.zhou.group.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.my.zhou.group.OrderActivity;
import com.my.zhou.group.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PutFragment extends Fragment {

    public PutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_take, container, false);
//        image_join_xiangqing= (ImageView) view.findViewById(R.id.image_join_xiangqing);
//        image_join_xiangqing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), OrderActivity.class));
//            }
//        });
        return view;
    }

}
