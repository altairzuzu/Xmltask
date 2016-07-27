package ru.solomatin.xmltask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.solomatin.xmltask.Model.Product;

/**
 * Фрагмент со списком Товаров
 */
public class TableFragment extends Fragment {

    public static final String TAG = "TableFragment";
    private MainActivity listener;
    private ProductListAdapter adapter;
    @BindView(R.id.list) ListView prodListView;
    @BindView(R.id.url) EditText urlView;
    private List<Product> productList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // При удалении MainActivity - отцепляем фрагмент и оставляем в памяти
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ProductListAdapter(getActivity(),
                listener.productList);
        prodListView.setAdapter(adapter);
        prodListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view, int pos, long arg)
            {
                listener.changeFragment(pos);
            }
        });
        listener.setTitle(getResources().getString(R.string.app_name));
    }

    @OnClick(R.id.getXml)
    public void getXml(View view) {
        urlView.clearFocus();
        String url = urlView.getText().toString();
        listener.rxUrl = url;
        listener.rxCallInWorks = true;
        listener.presenter.loadRxData(url, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public void notifyRefresh() {
        adapter.notifyDataSetChanged();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
