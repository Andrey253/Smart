package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geometry.smartdispatcherandroid.R;

import java.util.List;

import model.EndStationBusQueueItem;

/**
 * Класс представляет собой адаптер для представления объектов в ListView
 * @author BoykoAA
 * Created by house on 10.05.2019
 */
public class BusEndItemAdapter extends BaseAdapter {

    private List<EndStationBusQueueItem> list;
    private LayoutInflater layoutInflater;

    public BusEndItemAdapter(Context context, List<EndStationBusQueueItem> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
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
            view = layoutInflater.inflate(R.layout.bus_end_item_layout, parent,false );
        }
        EndStationBusQueueItem bus = getBusInfo(position);

        TextView textItemNumBus = view.findViewById(R.id.textItemBusState);
        textItemNumBus.setText(bus.getBusStateNumber());

        TextView textViewPositionQueue = view.findViewById(R.id.textViewPositionQueue);
        textViewPositionQueue.setText(String.valueOf(bus.getNumberQueue()));

        TextView textViewDepartureTimeInterval = view.findViewById(R.id.textViewDepartureTimeInterval);
        textViewDepartureTimeInterval.setText(String.valueOf(bus.getDepartureTimeInterval()));

        TextView textViewAssignTime = view.findViewById(R.id.textViewAssignTime);
        textViewAssignTime.setText(String.valueOf(bus.getAssignTime()));


        return view;
    }
    /**
     * Метод возвращает объект для позиции
     * @param position Индекс позиции
     * @return
     */
    private EndStationBusQueueItem getBusInfo(int position){
        return (EndStationBusQueueItem) getItem(position);
    }
}