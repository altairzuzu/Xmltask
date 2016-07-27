package ru.solomatin.xmltask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.solomatin.xmltask.Model.ApiResponse;
import ru.solomatin.xmltask.Model.Product;

public class MainActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener {

    private static final String EXTRA_RX = "EXTRA_RX";
    private static final String EXTRA_URL = "EXTRA_URL";
    private ProgressDialog pDialog;
    public boolean rxCallInWorks = false;
    public String rxUrl = "";
    public Presenter presenter;
    public TableFragment tableFragment;
    public List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);

        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
            rxUrl = savedInstanceState.getString(EXTRA_URL);
        }

        // Отслеживаем изменения стэка возврата
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();

        FragmentManager fm = getSupportFragmentManager();
        tableFragment =
                (TableFragment) fm.findFragmentByTag(TableFragment.TAG);
        if (tableFragment == null) {
            // Создаем фрагмент с таблицей
            tableFragment = new TableFragment();
            android.support.v4.app.FragmentTransaction fr = fm.beginTransaction();
            fr.replace(R.id.content_frame, tableFragment, TableFragment.TAG);
            fr.commit();
        } else {
            // Если фрагмент с таблицей уже есть в памяти - восстанавливаем список Товаров
            productList = tableFragment.getProductList();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rxCallInWorks)
            presenter.loadRxData(rxUrl, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.rxUnSubscribe();
        tableFragment.setProductList(productList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RX, rxCallInWorks);
        outState.putString(EXTRA_URL, rxUrl);
    }


    protected void showRxResults(ApiResponse response) {
        List<Product> pl = response.getProducts();
        for (Product p : pl) {
            if (!productList.contains(p)) {
                productList.add(p);
            }
        }
        hidePDialog();
        rxCallInWorks = false;
        showSortDialog();
        tableFragment.notifyRefresh();
    }

    protected void showRxInProcess(){
        productList.clear();
        tableFragment.notifyRefresh();
        pDialog = new ProgressDialog(this);
        // Показываем прогресс-диалог
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    protected void showRxFailure(Throwable throwable){
        hidePDialog();
        rxCallInWorks = false;
        Toast.makeText(this,
                getResources().getString(R.string.no_connection_message),
                Toast.LENGTH_LONG)
                .show();
    }

    public void sortProductList(int sortType){

        Comparator<Product> comparator;
        switch (sortType) {
            case 0:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return product.getId().compareToIgnoreCase(t1.getId());
                    }
                };
                break;
            case 1:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return product.getName().compareToIgnoreCase(t1.getName());
                    }
                };

                break;
            default:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return product.getActivdatum().compareToIgnoreCase(t1.getActivdatum());
                    }
                };
                break;
        }

        Collections.sort(productList, comparator);
        tableFragment.notifyRefresh();

    }

    void showSortDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment sortFrag = new SortDialog();
        sortFrag.setCancelable(true);
        sortFrag.show(ft, "dialog");
    }

    public void changeFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fr = fm.beginTransaction();
        ProductFragment fragment = new ProductFragment();
        fragment.setProduct(productList.get(position));
        fr.replace(R.id.content_frame,fragment,ProductFragment.TAG);
        fr.addToBackStack(null);
        fr.commit();
    }
    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @SuppressWarnings("")
    public void shouldDisplayHomeUp(){
        //Включаем кнопку Вверх только, если есть записи в стэке возврата
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Если нажата кнопка Вверх, выводим последний фрагмент из стэка возврата
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
