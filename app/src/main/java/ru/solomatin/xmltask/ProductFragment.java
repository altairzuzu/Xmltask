package ru.solomatin.xmltask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.solomatin.xmltask.Model.Product;

/**
 * Created by altair on 26.07.2016.
 */
public class ProductFragment extends Fragment {

    public static final String TAG = "ProductFragment";
    private MainActivity listener;
    private Product product;

    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.activdatum)
    TextView activdatum;
    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.segment)
    TextView segment;
    @BindView(R.id.filiale)
    TextView filiale;
    @BindView(R.id.aendkennz)
    TextView aendkennz;
    @BindView(R.id.aenddatum)
    TextView aenddatum;
    @BindView(R.id.hierarchie)
    TextView hierarchie;
    @BindView(R.id.verknuepfg)
    TextView verknuepfg;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, view);

        id.setText(product.getId());
        activdatum.setText(product.getActivdatum());
        name.setText(product.getName());
        segment.setText(String.valueOf(product.getSegment()));
        filiale.setText(product.getFiliale());
        aendkennz.setText(product.getAendkennz());
        aenddatum.setText(product.getAenddatum());
        hierarchie.setText(product.getHierarchie());
        verknuepfg.setText(product.getVerknuepfg());

//        if (person.getAge() != 0) {
//            age.setText(String.valueOf(person.getAge()));
//        } else {
//            age.setText("-");
//        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener.setTitle(String.valueOf(product.getName()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
