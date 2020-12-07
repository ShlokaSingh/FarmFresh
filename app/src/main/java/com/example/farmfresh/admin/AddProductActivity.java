package com.example.farmfresh.admin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.farmfresh.BuildConfig;
import com.example.farmfresh.R;
import com.example.farmfresh.admin.model.ProductModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class AddProductActivity extends AppCompatActivity {

    String categoryname, productname, productid, description, qty;
    String price, availability, discount;

    Context context;

    EditText etproductname, etdescription, etprice, etqty, etavail, etdiscount;
    Button addimage, addproduct;

    FirebaseDatabase database;
    DatabaseReference categoryNode;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    File file;
    Uri imageurl;
    String title = "no title";
    Task<Uri> downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etproductname = findViewById(R.id.etproductname);
        etdescription = findViewById(R.id.etdescription);
        etprice = findViewById(R.id.etprice);
        etqty = findViewById(R.id.etqty);
        etavail = findViewById(R.id.etavail);
        etdiscount = findViewById(R.id.etdiscount);
        addimage = findViewById(R.id.addimage);
        addproduct = findViewById(R.id.addproduct);

        database = FirebaseDatabase.getInstance();
        categoryNode = database.getReference().child("Category");

        Intent i = getIntent();
        categoryname = i.getStringExtra("category");
//        context = AddProductActivity.class;


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(AddProductActivity.this)
                        .title("Add Product Image")
                        .positiveText("Camera")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (hasPermissionsCamera()) {
                                    takePhoto();
                                } else {
                                    requestPermissionCamera();
                                }
                            }
                        })
                        .negativeText("Gallery")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (hasPermissionsGallery()) {
                                    accessImage();
                                } else {
                                    requestPermissionGallery();
                                }
                            }
                        })
                        .show();
            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productname = etproductname.getText().toString();
                price = etprice.getText().toString();
                description = etdescription.getText().toString();
                qty = etqty.getText().toString();
                discount = etdiscount.getText().toString();
                availability = etavail.getText().toString();
                productid = categoryNode.push().getKey();

                if (validateForm(productname, description, price, qty, availability, discount)) {
                    if (description.equals("")) {
                        description = "na";
                    }
                    uploadFile(productid, productname, description, price, qty, availability, discount);
                    Toast.makeText(getApplicationContext(), "Product added to inventory!!", Toast.LENGTH_SHORT).show();
                    finish();
//                    new AlertDialog.Builder(AddProductActivity.this)
//                            .setMessage("Product added to inventory!!")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    finish();
//                                }
//                            })
//                            .create()
//                            .show();
                }
            }
        });

    }

    /*  Method for checking permission for Camera  */
    public boolean hasPermissionsCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


    /* Method for requesting to grant permission for Camera */
    public void requestPermissionCamera() {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 1);
        }
    }


    /* Method for taking photo through Camera */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(this.getExternalCacheDir(), String.valueOf(System.currentTimeMillis() + "jpg"));
        imageurl = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageurl);
        this.startActivityForResult(intent, 0);
    }


    /*  Method for checking permission for Gallery  */
    public boolean hasPermissionsGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


    /* Method for requesting to grant permission for Gallery */
    public void requestPermissionGallery() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 2);
        }
    }


    /* Method for getting image from Gallery */
    private void accessImage() {
        Intent photoLibraryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoLibraryIntent, 1);
    }


    /* Method for validating AddProduct form */
    public boolean validateForm(String name, String discription, String price, String qty, String availability, String discount) {
        if (name.equals("")) {
            etproductname.setError("Enter name of product");
            return false;
        } else if (discription.equals("")) {
            etdescription.setError("Enter description of product");
            return false;
        } else if (price.equals("")) {
            etprice.setError("Enter price of product");
            return false;
        } else if (qty.equals("")) {
            etprice.setError("Enter quantity of product");
            return false;
        } else if (availability.equals("")) {
            etprice.setError("Enter amount of stock available");
            return false;
        } else if (discount.equals("")) {
            etprice.setError("Enter discount percentage of product");
            return false;
        }
        return true;
    }

    /* Method for getting name of file */
    public String getTitleOfFile(String filePath) {
        char[] title = filePath.toCharArray();
        String finalTitle = "";
        for (int count = title.length - 1; count >= 0; count--) {
            if (title[count] == '/')
                break;
            finalTitle = finalTitle + title[count];
        }
        return new StringBuilder(finalTitle).reverse().toString();
    }

    /* Method for getting path of file */
    public String getPath(String[] filePathColumn) {
        Cursor cursor = getContentResolver().query(imageurl,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filepath = cursor.getString(columnIndex);
        cursor.close();
        return filepath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                title = getTitleOfFile(file.toString());
            }
            if (requestCode == 1) {
                imageurl = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                String picturePath = getPath(filePathColumn);
                title = getTitleOfFile(picturePath);
            }
//            if(imageurl != null){
//                ivItemImage.setVisibility(View.VISIBLE);
//                ivItemImage.setImageURI(imageurl);
//            }
        }
    }

    /* Method for uploading image in Firebase Storage and send data to Database */
    public void uploadFile(final String id, final String name, final String description, final String price, final String qty, final String availability, final String discount) {
        if (!title.equals("no title")) {
            final StorageReference storageRef = firebaseStorage.getReference().child("images").child(title);
            UploadTask uploadTask = storageRef.putFile(imageurl);

            downloadUrl = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    Toast.makeText(getApplicationContext(),"Upload Complete!!",Toast.LENGTH_SHORT).show();
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        ProductModel product = new ProductModel();
                        product.setProductId(id);
                        product.setProductName(name);
                        product.setDescription(description);
                        product.setPrice(price);
                        product.setQty(qty);
                        product.setAvailability(availability);
                        product.setDiscount(discount);
                        product.setImageurl(downloadUrl.toString());
                        categoryNode.child(categoryname).child(id).setValue(product);
                    } else {
                    }
                }
            });
        } else {
            ProductModel product = new ProductModel();
            product.setProductId(id);
            product.setProductName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQty(qty);
            product.setAvailability(availability);
            product.setDiscount(discount);
            product.setImageurl("https://firebasestorage.googleapis.com/v0/b/groceryapp-ae091.appspot.com/o/b2.png?alt=media&token=0a54d2a2-bcc3-49a9-b7ab-09e6e6d6b51f");
            categoryNode.child(categoryname).child(id).setValue(product);
        }
    }

}
