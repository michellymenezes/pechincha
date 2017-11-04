package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.endpoints.HerokuAddFavoriteSaleTask;
import com.projeto1.projeto1.endpoints.HerokuDeleteDislikeTask;
import com.projeto1.projeto1.endpoints.HerokuDeleteLikeTask;
import com.projeto1.projeto1.endpoints.HerokuGetMarketTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductTask;
import com.projeto1.projeto1.endpoints.HerokuGetUserTask;
import com.projeto1.projeto1.endpoints.HerokuGetUsersTask;
import com.projeto1.projeto1.endpoints.HerokuPostDislikeTask;
import com.projeto1.projeto1.endpoints.HerokuPostLikeTask;
import com.projeto1.projeto1.endpoints.HerokuPostUserTask;
import com.projeto1.projeto1.endpoints.HerokuPutSaleTask;
import com.projeto1.projeto1.endpoints.HerokuRemoveFavoriteSaleTask;
import com.projeto1.projeto1.listeners.UserListener;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by samirsmedeiros on 17/06/17.
 */

public class SaleDetailsFragment extends Fragment implements ProductListener, MarketListener, UserListener, SaleListener {


    public static final String TAG = "SALE_DETAILS_FRAGMENT";

    private View mview;
    private Product product;
    private HerokuGetProductTask productTask;
    private HerokuGetMarketTask marketTask;
    private HerokuGetUserTask userTask;
    private Sale sale;
    private Market market;
    private User user;
    private User currentUser;
    private MainActivity myMainActivity;
    private TextView mOld_price;
    private TextView mName_product;
    private TextView mBarcod;
    private TextView mValidity;
    private TextView currentPrice;
    private Button marketName;
    private TextView validity;
    private TextView quantity;
    private TextView username;
    private ImageView userImage;
    private HerokuPutSaleTask salePutTask;
    private Button updateSaleBtn;
    private LineChart chart;
    private Boolean favoriteAction;
    private TextView updateDate;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SaleDetailsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SaleDetailsFragment getInstance() {
        SaleDetailsFragment fragment = new SaleDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_sale_details, container, false);


        chart = (LineChart) mview.findViewById(R.id.chart);

        setData();
        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);


        ImageView product_image = (ImageView) mview.findViewById(R.id.product_image);
        TextView tv_category = (TextView) mview.findViewById(R.id.tv_category);
        ImageButton category_detail = (ImageButton) mview.findViewById(R.id.category_detail);
        mOld_price = (TextView) mview.findViewById(R.id.old_price);
        mName_product = (TextView) mview.findViewById(R.id.name_product);
        mBarcod = (TextView) mview.findViewById(R.id.barcode);
        mValidity = (TextView) mview.findViewById(R.id.validity);
        currentPrice = (TextView) mview.findViewById(R.id.current_price);
        marketName = (Button) mview.findViewById(R.id.name_supermarket);
        validity = (TextView) mview.findViewById(R.id.validity);
        quantity = (TextView) mview.findViewById(R.id.quantity);
        username = (TextView) mview.findViewById(R.id.user_name);
        updateSaleBtn = (Button) mview.findViewById(R.id.att);
        currentUser = SharedPreferencesUtils.getUser(getActivity().getBaseContext());
        updateDate = (TextView) mview.findViewById(R.id.sale_update_date);
        favoriteAction = false;

        sale = SharedPreferencesUtils.getSelectedSale(getContext());
        product_image.setImageResource(/*getImage(((MainActivity) getActivity()).getCurrentCategory())*/R.drawable.ic_offer);
        tv_category.setText(((MainActivity) getActivity()).getCurrentCategory());
        category_detail.setImageResource(getImage(((MainActivity) getActivity()).getCurrentCategory()));

        updateSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(UpdateSaleFragment.getInstance(),TAG,true);
            }
        });


        if (sale != null) {

            final CheckBox cb_fav = (CheckBox) mview.findViewById(R.id.fav_btn);
            //final TextView fav_quanity = (TextView) mview.findViewById(R.id.fav_quantity);

            boolean firstTime = true;
            if (firstTime) {
                cb_fav.setChecked(currentUser.getFavorites().contains(sale));
                firstTime = false;
            }
           // fav_quanity.setText(currentUser.getFavorites().size()+"");

            if(!firstTime) {
                cb_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.d("USER", currentUser.getName());
                      //  fav_quanity.setText(currentUser.getFavorites().size()+ "");
                        updateSale(sale, currentUser, cb_fav);
                    }
                });
            }

            //EXEMPLO DE EDIÇÃO DE PROMOÇÃO - PUT

            /*Sale newSale = sale;
            newSale.setSalePrice(1.0);

            salePutTask = new HerokuPutSaleTask(newSale, getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)) + "/" + sale.getId(), this);
            salePutTask.execute();*/

        }


        if (sale != null) {

            final CheckBox cb_like = (CheckBox) mview.findViewById(R.id.like_btn);
            final CheckBox cb_dislike = (CheckBox) mview.findViewById(R.id.dislike_btn);

            final TextView like_quanity = (TextView) mview.findViewById(R.id.like_quantity);
            final TextView dislike_quanity = (TextView) mview.findViewById(R.id.dislike_quantity);

            boolean firstTime = true;
            if (firstTime) {
                cb_like.setChecked(sale.getLikeUsers().contains(currentUser.getId()));
                cb_dislike.setChecked(sale.getDislikeUsers().contains(currentUser.getId()));
                firstTime = false;
            }
            like_quanity.setText(sale.getLikeCount()+"");
            dislike_quanity.setText(sale.getDislikeCount()+"");

            if(!firstTime) {
                cb_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.v("IDDDDDDDD",sale.getId());
                        if(cb_like.isChecked()) cb_dislike.setChecked(false);
                        updateSaleLike(sale, cb_like,currentUser);
                        Log.d("USER", currentUser.getName());
                        like_quanity.setText(sale.getLikeCount() + "");
                    }
                });

                cb_dislike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.v("IDDDDDDDD",sale.getId());
                        if(cb_dislike.isChecked()) cb_like.setChecked(false);
                        updateSaleDislike(sale, cb_dislike, currentUser);
                        Log.d("USER", currentUser.getName());
                        dislike_quanity.setText(sale.getDislikeCount() + "");
                    }
                });
            }



            //EXEMPLO DE EDIÇÃO DE PROMOÇÃO - PUT

            /*Sale newSale = sale;
            newSale.setSalePrice(1.0);

            salePutTask = new HerokuPutSaleTask(newSale, getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)) + "/" + sale.getId(), this);
            salePutTask.execute();*/

            productTask = new HerokuGetProductTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)) + "/" + sale.getProductId(), this);
            productTask.execute();
        }

        marketDetail();
        //updateSale(inflater, container);



        return mview;
    }

    private void updateSaleDislike(Sale sale, CheckBox cb_dislike, User currentUser) {
        if(cb_dislike.isChecked()) {
            sale.addDislike(currentUser.getId());
            HerokuPostDislikeTask mTask = new HerokuPostDislikeTask(sale.getId(), currentUser.getId(), getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
            mTask.execute();
        } else {
            sale.removeDislike(currentUser.getId());
            HerokuDeleteDislikeTask mTask = new HerokuDeleteDislikeTask(sale.getId(), currentUser.getId(), getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
            mTask.execute();
        }
    }

    private ArrayList setYAxisValues(){
        ArrayList yVals = new ArrayList<>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }
    private ArrayList setXAxisValues(){
        ArrayList xVals = new ArrayList<>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }
    private void setData() {
        ArrayList xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

// create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setFillAlpha(110);

        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillAlpha(255);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

//LineData data = new LineData(xVals, dataSets);

        LineData data = new LineData();
        data.addDataSet(set1);


        chart.setData(data);
    }


    private int getImage(String currentCategory) {
        switch (currentCategory) {
            case "Alimento": {
                return R.drawable.ic_grocery;
            }
            case "Cuidados pessoais": {
                return R.drawable.ic_hygiene;
            }
            case "Limpeza": {
                return R.drawable.ic_wiping_white;
            }
            case "Eletrônico": {
                return R.drawable.ic_plug_white;

            }
            case "Mobília": {
                return R.drawable.ic_sofa_white;

            }
            case "Outros": {
                return R.drawable.ic_other;
            }
            default: return R.drawable.ic_offer;

        }

    }

    private void updateSale(Sale sale, User user, CheckBox likeCB) {
        if(likeCB.isChecked()){
            HerokuAddFavoriteSaleTask mTask = new HerokuAddFavoriteSaleTask(user, sale.getId(), getContext(), String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)), this);
            mTask.execute();
        } else {
            HerokuRemoveFavoriteSaleTask mTask = new HerokuRemoveFavoriteSaleTask(user, sale.getId(), getContext(), String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)), this);
            mTask.execute();
        }

        }


    private void updateSaleLike(Sale sale, CheckBox cb_like, User currentUser) {
        if(cb_like.isChecked()) {
            sale.addLike(currentUser.getId());
            HerokuPostLikeTask mTask = new HerokuPostLikeTask(sale.getId(), currentUser.getId(), getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
            mTask.execute();
        } else {
            sale.removeLike(currentUser.getId());
            HerokuDeleteLikeTask mTask = new HerokuDeleteLikeTask(sale.getId(), currentUser.getId(), getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
            mTask.execute();
        }
    }

    private void marketDetail() {
        marketName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View viewDialog = View.inflate(getActivity(), R.layout.supermarket_info_dialog, null);
                TextView marketNameDialog = (TextView) viewDialog.findViewById(R.id.market_name);
                TextView marketAddressDialog = (TextView) viewDialog.findViewById(R.id.market_address);
                TextView marketCnpjDialog = (TextView) viewDialog.findViewById(R.id.market_cnpj);

                marketNameDialog.setText(market.getName());
                marketAddressDialog.setText(market.getAdress().getFormattedAddress());
                marketCnpjDialog.setText("CNPJ: " + market.getCnpj());

                new AlertDialog.Builder(getContext())
                        .setTitle("")
                        .setView(viewDialog)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.ic_map_pin_marked)
                        .show();
            }
        });
    }

    private void startAdapter() {

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

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {

    }

    @Override
    public void OnGetProductsByCategoryReady(boolean ready, ArrayList<Product> products) {

    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {
        this.product = product;
        SharedPreferencesUtils.setProductFromSale(getContext(), product);
        User user = SharedPreferencesUtils.getUser(getContext());
        user.setId(sale.getAuthorId());
        userTask = new HerokuGetUserTask(String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)), this, user);
        userTask.execute();

    }

    @Override
    public void OnGetProductByBarcodeReady(boolean ready, Product product) {

    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {
    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketReady(boolean b, Market market) {
        this.market = market;
        SharedPreferencesUtils.setMarketFromSale(getContext(), market);


        mOld_price.setText("R$" + sale.getRegularPrice().toString());
        currentPrice.setText("R$" + sale.getSalePrice().toString());
        if (product != null) {
            mName_product.setText(product.getName() + " " + product.getBrand()  +" " +Integer.valueOf((int) product.getSize())+product.getSizeUnity());
            mBarcod.setText(product.getBarcode());
        }
        marketName.setText(market.getName());
        String [] date =  sale.getExpirationDate().substring(0,10).split("-");
        String expireDate = date[2] +"-"+ date[1] +"-"+ date[0];
        validity.setText(expireDate);
        username.setText(user.getName());
        quantity.setText(((int)product.getSize()) +product.getSizeUnity()+"");

        String datePub = getFomatedDate(sale.getPublicationDate());

        updateDate.setText(datePub);
        mOld_price.setPaintFlags(mOld_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        userImage = (ImageView) mview.findViewById(R.id.user_img);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setUserSelected(getContext(), user);
                final View viewDialog = View.inflate(getActivity(), R.layout.user_info_dialog, null);
                RatingBar ratingBar = (RatingBar) viewDialog.findViewById(R.id.ratingBar);
                TextView userName = (TextView) viewDialog.findViewById(R.id.user_name);
                ratingBar.setRating(Float.valueOf(String.valueOf(user.getReputation())));
                userName.setText(user.getName());

                new AlertDialog.Builder(getContext())
                        .setTitle("")
                        .setView(viewDialog)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.ic_map_pin_marked)
                        .show();
            }
        });
    }

    private String getFomatedDate(Date publicationDate) {

        String splitDate [] = publicationDate.toString().split(" ");
        String day = splitDate[2];
        String month = getMonth(splitDate[1]);
        String year = splitDate[5];
        return day+"/"+month+"/"+year;

    }

    private String getMonth(String s) {
        switch (s) {
            case "Jan": {
                return "01";
            }
            case "Feb": {
                return "02";
            }
            case "Mar": {
                return "03";
            }
            case "Apr": {
                return "04";
            }
            case "May": {
                return "05";
            }
            case "Jun": {
                return "06";
            }
            case "Jul": {
                return "07";
            }
            case "Aug": {
                return "08";
            }
            case "Sep": {
                return "09";
            }
            case "Oct": {
                return "10";
            }
            case "Nov": {
                return "11";
            }
            case "Dec": {
                return "12";
            }

            default: return "01";

        }
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public void OnGetAllUsersFinished(boolean ready, ArrayList<User> users) {

    }

    @Override
    public void OnGetUserFinished(boolean find, User user) {
        if (!find){
            HerokuPostUserTask userTask = new HerokuPostUserTask(user,
                    getContext(), String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)), this);
            userTask.execute();
        } else if(find && currentUser.getId().equals(user.getId())){
            SharedPreferencesUtils.setUser(getContext(),user);
        }
        if(!favoriteAction) {
            this.user = user;
            favoriteAction = false;
        }
        marketTask = new HerokuGetMarketTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)) + "/" + sale.getMarketId(), this);
        marketTask.execute();

    }


    @Override
    public void OnPostUserFinished(boolean finished) {

    }

    @Override
    public void OnAddFavoriteSaleFinished(boolean finished) {
        favoriteAction = finished;
        User user = SharedPreferencesUtils.getUser(getContext());
        HerokuGetUsersTask getUserTask = new HerokuGetUsersTask(String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)),
                this, user);
        getUserTask.execute();
        currentUser = SharedPreferencesUtils.getUser(getContext());

    }

    @Override
    public void OnRemoveFavoriteSaleFinished(boolean finished) {
        favoriteAction = finished;
        User user = SharedPreferencesUtils.getUser(getContext());
        HerokuGetUsersTask getUserTask = new HerokuGetUsersTask(String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)),
                this, user);
        getUserTask.execute();
        currentUser = SharedPreferencesUtils.getUser(getContext());


    }

    @Override
    public void OnPutUserFinished(boolean finished) {

    }

    @Override
    public void OnGetSalesReady(boolean ready, ArrayList<Sale> sales) {

    }

    @Override
    public void OnPostSaleFinished(boolean finished) {

    }

    @Override
    public void OnPutSaleFinished(boolean finished) {
        if (finished) {
            Log.v("EDICAO", "FOI!!!");
        } else {
            Log.v("EDICAO", "FLOPOU :(");
        }
    }

    @Override
    public void OnPostLikeFinished(boolean finished) {

    }

    @Override
    public void OnDeleteLikeFinished(boolean finished) {

    }

    @Override
    public void OnPostDislikeFinished(boolean finished) {

    }

    @Override
    public void OnDeleteDislikeFinished(boolean finished) {

    }

    private void updateDialog(LayoutInflater inflater, ViewGroup container) {
        final View viewDialog = View.inflate(getActivity(), R.layout.update_sale_dialog, null);

        new AlertDialog.Builder(getContext())
                .setTitle("Atualização de promoção")
                .setView(viewDialog)
                .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Atualizar", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                //.setIcon(R.drawable.ic_calendar)
                .show();
    }

}