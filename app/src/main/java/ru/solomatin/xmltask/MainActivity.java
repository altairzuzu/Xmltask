package ru.solomatin.xmltask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.solomatin.xmltask.Model.ApiResponse;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_RX = "EXTRA_RX";
    private ProgressDialog pDialog;
    //private Button rxCall;
    private boolean rxCallInWorks = false;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);


        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rxCallInWorks)
            presenter.loadRxData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.rxUnSubscribe();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RX, rxCallInWorks);
    }

    public void showRxResults(ApiResponse response) {
//        personList = response.getResponse();
//        specialtyList.clear();
//        for (Person person : personList) {
//            for (Specialty specialty : person.getSpecialty()) {
//                if (!specialtyList.contains(specialty)) {
//                    specialtyList.add(specialty);
//                }
//            }
//        }
//        specialtyFragment.notifyRefresh();
        hidePDialog();
    }

    protected void showRxInProcess(){
        pDialog = new ProgressDialog(this);
        // Показываем прогресс-диалог
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.show();
    }

    protected void showRxFailure(Throwable throwable){
        hidePDialog();
        Toast.makeText(this,
                getResources().getString(R.string.no_connection_message),
                Toast.LENGTH_LONG)
                .show();
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
