package com.example.crud;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud.adapter.ItemAdapter;
import com.example.crud.model.get.DataItem;
import com.example.crud.model.get.GetResponse;
import com.example.crud.model.getid.Data;
import com.example.crud.model.getid.GetItemResponse;
import com.example.crud.presenter.MainPresenter;
import com.example.crud.presenter.MainView;

import java.util.ArrayList;
import java.util.List;

public class Lihat extends AppCompatActivity implements MainView, ItemAdapter.OnAdapterClickListener {
    private RecyclerView recyclerView;
    private ItemAdapter itemsAdapter;
    private MainPresenter presenter;
    private List<DataItem> list;
    private List<Data> listItem;
    private FloatingActionButton floatingActionButton;
    EditText cari;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);
        list = new ArrayList<>();
        //listItem = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_items);
        cari = findViewById(R.id.ET_1);
        floatingActionButton = findViewById(R.id.fb_items);
        img = findViewById(R.id.img_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newItemsDialog();
            }
        });
        itemsAdapter = new ItemAdapter(this, list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
        presenter = new MainPresenter(this);
        presenter.getAllItems();
        img.setOnClickListener(new View.OnClickListener() {
            String cari_id = cari.getText().toString();
            @Override
            public void onClick(View v) {
                //Intent x = new Intent(getApplicationContext(), cari.class);
                //startActivity(x);
                presenter.getItems(cari_id);
            }
        });

    }
    private void newItemsDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Barang");
        final View textEntryView = factory.inflate(R.layout.input_data, null);
        final EditText name = (EditText)
                textEntryView.findViewById(R.id.edt_name);
        final EditText description = (EditText)
                textEntryView.findViewById(R.id.edt_description);
        name.setHint("Nama Barang");
        description.setHint("Deskripsi");
        name.setText("", TextView.BufferType.EDITABLE);
        description.setText("", TextView.BufferType.EDITABLE);
        builder.setView(textEntryView);
        builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!name.getText().toString().equals("")) {
                    presenter.createItems(name.getText().toString(),
                            description.getText().toString());
                } else {
                    Toast.makeText(Lihat.this, "Masukkan Nama Barang", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private void deleteDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah kita Benar Akan Menghapus Item ini?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteItems(id);
            }
        });
        builder.setNegativeButton("Tidak", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    private void editDialog(final String id, final String name, final String
            description) {
        LayoutInflater factory = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Barang");
        final View textEntryView = factory.inflate(R.layout.input_data, null);
        final EditText edtName = (EditText)
                textEntryView.findViewById(R.id.edt_name);
        final EditText edtDescription = (EditText)
                textEntryView.findViewById(R.id.edt_description);
        edtName.setText(name, TextView.BufferType.EDITABLE);
        edtDescription.setText(description, TextView.BufferType.EDITABLE);
        builder.setView(textEntryView);
        builder.setTitle("Update Barang");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.updateItems(id, edtName.getText().toString(),
                        edtDescription.getText().toString());
            }
        });
        builder.setNegativeButton("Tidak", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllItems();
    }
    @Override
    public void onClicked(String id, String name, String description, String key) {
        if (key.equalsIgnoreCase("edit")) {
            editDialog(id, name, description);
        }
        else {
            deleteDialog(id);
        }
    }

    @Override
    public void getSuccess(GetResponse list) {
        this.list.clear();
        this.list.addAll(list.getData());
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.getAllItems();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String failureMessage) {
        Toast.makeText(this, failureMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getSuccess2(GetItemResponse listItem) {
        this.listItem.clear();
        this.listItem.add(listItem.getData());
        itemsAdapter.notifyDataSetChanged();
    }


}

