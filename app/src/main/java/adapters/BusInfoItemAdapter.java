package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geometry.smartdispatcherandroid.R;

import java.util.List;

import model.Bus;

/**
 * Класс представляет собой адаптер для представления объектов в ListView
 * @author BoykoAA
 * Created by house on 10.05.2019
 */
public class BusInfoItemAdapter extends BaseAdapter {

    private List<Bus> list;
    private LayoutInflater layoutInflater;

    public BusInfoItemAdapter(Context context, List<Bus> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Bus getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){

            view = layoutInflater.inflate(R.layout.bus_info_item_layout, parent,false );
        }
        Bus bus = getBusInfo(position);

        TextView textItemNumBus = view.findViewById(R.id.textItemBusState);
        textItemNumBus.setText(bus.getBusState());

        TextView textItemNumRoute = view.findViewById(R.id.textItemRouteNumber);
        textItemNumRoute.setText(bus.getRouteNumber());

        if (bus.getBusState().contains("0")) textItemNumBus.setTextColor(Color.GREEN);
        else textItemNumBus.setTextColor(Color.WHITE);

        return view;
    }
    /**
     * Метод возвращает объект для позиции
     * @param position Индекс позиции
     * @return
     */
    private Bus getBusInfo(int position){
        return (Bus)getItem(position);
    }
}