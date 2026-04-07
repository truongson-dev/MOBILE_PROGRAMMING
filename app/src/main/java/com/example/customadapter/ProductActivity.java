package com.example.customadapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;
    EditText editName, editPrice;
    Button btnAdd, btnUpdate, btnDelete;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editName = findViewById(R.id.edit_name);
        editPrice = findViewById(R.id.edit_price);
        btnAdd = findViewById(R.id.btn_add);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        listViewProduct = findViewById(R.id.listproduct);

        // Khởi tạo danh sách sản phẩm
        listProduct = new ArrayList<>();
        listProduct.add(new Product(1, "Iphone 6", 500));
        listProduct.add(new Product(2, "Iphone 7", 700));
        listProduct.add(new Product(3, "Sony Abc", 800));

        productListViewAdapter = new ProductListViewAdapter(listProduct);
        listViewProduct.setAdapter(productListViewAdapter);

        // Thêm sản phẩm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String priceStr = editPrice.getText().toString();

                if (name.isEmpty() || priceStr.isEmpty()) {
                    Toast.makeText(ProductActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int price = Integer.parseInt(priceStr);
                int id = listProduct.size() > 0 ? listProduct.get(listProduct.size() - 1).productID + 1 : 1;
                
                listProduct.add(new Product(id, name, price));
                productListViewAdapter.notifyDataSetChanged();
                clearInputs();
            }
        });

        // Sửa sản phẩm
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndex == -1) {
                    Toast.makeText(ProductActivity.this, "Chọn một sản phẩm để sửa", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = editName.getText().toString();
                String priceStr = editPrice.getText().toString();

                if (name.isEmpty() || priceStr.isEmpty()) {
                    Toast.makeText(ProductActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product product = listProduct.get(selectedIndex);
                product.name = name;
                product.price = Integer.parseInt(priceStr);

                productListViewAdapter.notifyDataSetChanged();
                clearInputs();
                selectedIndex = -1;
            }
        });

        // Xóa sản phẩm
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndex == -1) {
                    Toast.makeText(ProductActivity.this, "Chọn một sản phẩm để xóa", Toast.LENGTH_SHORT).show();
                    return;
                }

                listProduct.remove(selectedIndex);
                productListViewAdapter.notifyDataSetChanged();
                clearInputs();
                selectedIndex = -1;
            }
        });

        // Lắng nghe sự kiện click vào item
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                Product product = listProduct.get(position);
                editName.setText(product.name);
                editPrice.setText(String.valueOf(product.price));
            }
        });
    }

    private void clearInputs() {
        editName.setText("");
        editPrice.setText("");
        editName.requestFocus();
    }

    // Model Product
    class Product {
        String name;
        int price;
        int productID;

        public Product(int productID, String name, int price) {
            this.name = name;
            this.price = price;
            this.productID = productID;
        }
    }

    // Adapter cho ListView
    class ProductListViewAdapter extends BaseAdapter {
        final ArrayList<Product> listProduct;

        ProductListViewAdapter(ArrayList<Product> listProduct) {
            this.listProduct = listProduct;
        }

        @Override
        public int getCount() {
            return listProduct.size();
        }

        @Override
        public Object getItem(int position) {
            return listProduct.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listProduct.get(position).productID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewProduct;
            if (convertView == null) {
                viewProduct = View.inflate(parent.getContext(), R.layout.product_view, null);
            } else {
                viewProduct = convertView;
            }

            Product product = (Product) getItem(position);
            
            TextView txtId = viewProduct.findViewById(R.id.idproduct);
            TextView txtName = viewProduct.findViewById(R.id.nameproduct);
            TextView txtPrice = viewProduct.findViewById(R.id.priceproduct);

            txtId.setText(String.format(Locale.getDefault(), "ID = %d", product.productID));
            txtName.setText(String.format(Locale.getDefault(), "Tên SP : %s", product.name));
            txtPrice.setText(String.format(Locale.getDefault(), "Giá %d", product.price));

            return viewProduct;
        }
    }
}
