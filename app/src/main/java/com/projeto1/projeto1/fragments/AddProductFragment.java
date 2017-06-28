package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.CategoryListAdapter;
import com.projeto1.projeto1.adapters.SubCategoryListAdapter;
import com.projeto1.projeto1.models.Sale;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddProductFragment extends Fragment {


    public static final String TAG = "ADD_PRODUCT_FRAGMENT";

    private View mview;
    private CategoryListAdapter mAdapter;
    private SubCategoryListAdapter mListAdapter;
    private List<String> subCategoryList;
    private RecyclerView subCategoryRecycleView;
    private ArrayList<String> groceryCategoryList;
    private ArrayList<String> hygieneCategoryList;
    private ArrayList<String> otherCategoryList;
    private GroceryProductsFragment groceryProductsFragment;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddProductFragment() {
    }

    // TODO: Customize parameter initialization
    public static AddProductFragment getInstance() {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.fragment_add_product, container, false);

        final CheckBox cb_grocery = (CheckBox) mview.findViewById(R.id.checkbox_grocery);
        final CheckBox cb_hygiene = (CheckBox) mview.findViewById(R.id.checkbox_hygiene);
        final CheckBox cb_other = (CheckBox) mview.findViewById(R.id.checkbox_other);

        subCategoryRecycleView = (RecyclerView) mview.findViewById(R.id.sub_category_list);
        subCategoryRecycleView.setLayoutManager(new FlowLayoutManager());
        groceryCategoryList = new ArrayList<>(Arrays.asList( "Grãos", "Bebidas", "Laticínio",
                "Carnes", "Oleos", "Frutas e Verduras"));
        hygieneCategoryList = new ArrayList<>(Arrays.asList( "Sabonetes", "Detergentes", "Limpeza",
                "Hidratantes"));
        otherCategoryList = new ArrayList<>(Arrays.asList( "Outros"));

        cb_grocery.setChecked(true);
        mListAdapter = new SubCategoryListAdapter(groceryCategoryList);
        subCategoryRecycleView.setAdapter(mListAdapter);

        cb_grocery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_grocery.isChecked()) {
                    cb_other.setChecked(false);
                    cb_hygiene.setChecked(false);
                    mListAdapter = new SubCategoryListAdapter(groceryCategoryList);
                    subCategoryRecycleView.setAdapter(mListAdapter);
                }
            }
        });

        cb_hygiene.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_hygiene.isChecked()) {
                    cb_other.setChecked(false);
                    cb_grocery.setChecked(false);
                    mListAdapter = new SubCategoryListAdapter(hygieneCategoryList);
                    subCategoryRecycleView.setAdapter(mListAdapter);
                }
            }
        });

        cb_other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_other.isChecked()) {
                    cb_hygiene.setChecked(false);
                    cb_grocery.setChecked(false);
                    mListAdapter = new SubCategoryListAdapter(otherCategoryList);
                    subCategoryRecycleView.setAdapter(mListAdapter);
                }
            }
        });

        final EditText productNameET = (EditText) mview.findViewById(R.id.product_name_input);
        final EditText productCodeET = (EditText) mview.findViewById(R.id.product_code_input);
        final EditText productMarketET = (EditText) mview.findViewById(R.id.brand_input);
        final EditText productPriceET = (EditText) mview.findViewById(R.id.price_input);
        final EditText productQuantityET = (EditText) mview.findViewById(R.id.quantity_input);

        Button sendBtn = (Button) mview.findViewById(R.id.send_btn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameET.getText().toString();
                String productCode = productCodeET.getText().toString();
                String productMarket = productMarketET.getText().toString();
                String productPrice = productPriceET.getText().toString();
                String productQuantity = productQuantityET.getText().toString();

                //TODO criar objeto e salvar no banco.
               //Sale sale = new Sale(productName,null, Double.parseDouble(productPrice), null, null,0,0,null,null,0,false);

            }
        });
        return mview;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}