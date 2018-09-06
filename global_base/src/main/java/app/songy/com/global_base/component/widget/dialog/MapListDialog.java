package app.songy.com.global_base.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

import app.songy.com.global_base.R;


/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:25
 */
abstract class MapListDialog extends Dialog implements AdapterView.OnItemClickListener {

    private List<Map.Entry<String, String>> listMap;
    private int gravity;

    MapListDialog(Context context, List<Map.Entry<String, String>> list, int gravity) {
        super(context, R.style.Dialog);
        this.listMap = list;
        this.gravity = gravity;
        init();
    }

    private void init() {
        Context context = getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        setContentView(root);
        ListView mList = (ListView) root.findViewById(R.id.dialog_item_detail);
        ListAdapter mAdapter = new ListAdapter(context, listMap);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.dismiss();
        onSelected(listMap.get(position), position);
    }

    private class ListAdapter extends BaseAdapter {
        private List<Map.Entry<String, String>> list = null;
        private Context context = null;

        private ListAdapter(Context context, List<Map.Entry<String, String>> list) {
            this.list = list;
            this.context = context;
        }

        public void setList(List<Map.Entry<String, String>> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Map.Entry<String, String> getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_simple_text, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.text.setGravity(gravity);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Map.Entry<String, String> entry = getItem(position);
            if (entry != null) {
                holder.text.setText(entry.getValue());
            }
            return convertView;
        }

        private class ViewHolder {
            TextView text;
        }

    }

    public abstract void onSelected(Map.Entry<String, String> object, int position);

}
