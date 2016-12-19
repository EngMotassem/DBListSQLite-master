package gust.dblist;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    ArrayList<Contact> contacts=new ArrayList<Contact>();
    MyOwnAdapter myadapter;
    NotificationManager notif;
    Notification notify;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBAdapter DB=new DBAdapter(MainActivity.this);



        notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notify=new Notification.Builder
                (getApplicationContext()).//context
                setContentTitle("title").//content title
                setContentText("body").//content body
                setSmallIcon(R.mipmap.ic_launcher).//notification icon
                build();

        //notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);

        Button add= (Button) findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name= (EditText) findViewById(R.id.name);
                EditText phone= (EditText) findViewById(R.id.phone);
                String n=name.getText().toString();
                int num=     Integer.parseInt(   phone.getText().toString());
                DB.open();
                Contact c1=new Contact(n,num);
                DB.add(c1);
                myadapter.notifyDataSetChanged();
               DB.retreiveAll();

                DB.close();
                name.setText(" ");
                phone.setText(" ");
            }
        });

//contacts.add(new Contact("ahmad",333444));
        //      contacts.add(new Contact("ali",555666));


        DB.open();
        contacts=DB.retreiveAll();
        DB.close();

        ListView mylist= (ListView) findViewById(R.id.mylist);
        myadapter= new MyOwnAdapter(MainActivity.this,R.layout.item,contacts);
        myadapter.setNotifyOnChange(true);

        mylist.setAdapter(myadapter);
        /*
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,contacts.get(position).name,Toast.LENGTH_SHORT).show();
            }
        });

        */
        mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                contacts.remove(position);
                myadapter.notifyDataSetChanged();
                return false;
            }
        });




    }

    public class MyOwnAdapter extends ArrayAdapter<Contact>{

        public MyOwnAdapter(Context context, int resource, List<Contact> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=getLayoutInflater().inflate(R.layout.item,null);

            TextView name= (TextView) v.findViewById(R.id.thename);
            name.setText(contacts.get(position).name);
            TextView thenumber= (TextView) v.findViewById(R.id.thenumber);
            thenumber.setText(contacts.get(position).number +"");

            return v;



        }
    }

}
