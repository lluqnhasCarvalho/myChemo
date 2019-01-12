<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:text="@string/novo_usuario"
        android:textSize="24sp"
        android:textStyle="bold"
        android:gravity="center"/>

    <ViewFlipper
        android:id="@+id/ViewFlipper"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <!--layout infor conta-->
        <android.support.v7.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimaryDark"
            app:cardCornerRadius="15dp"
            app:cardElevation="20dp"
            android:layout_margin="16dp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

                <RelativeLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:layout_gravity="center">

                    <View
                        android:id="@+id/view1"
                        android:layout_width="285dp"
                        android:layout_height="5dp"
                        android:layout_alignParentStart="true"
                        android:layout_centerVertical="true"
                        android:background="@color/colorPrimaryDark" />

                    <TextView
                        android:layout_width="30dp"
                        android:layout_height="30dp"
                        android:layout_alignParentLeft="true"
                        android:layout_alignParentTop="true"
                        android:background="@drawable/timeline_blue"