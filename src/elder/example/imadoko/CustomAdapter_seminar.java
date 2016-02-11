package elder.example.imadoko;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter_seminar extends BaseAdapter {
	String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
     int isInSemi = 0; 
     ArrayList<String> listof_present_Std = new ArrayList<String>(); 
     String list_ofMy_guy; 
     TinyDB tinydb ;
      
    public CustomAdapter_seminar(Activity UbiSeminar, String[] studentNameList) {
        // TODO Auto-generated constructor stub
        result=studentNameList;
        context=UbiSeminar;
        //imageId=studentImages;
         inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         tinydb = new TinyDB(context);
         

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        LinearLayout llayoutPresenceInSemi; 
        //ImageView img;
        //ToggleButton tggBut; 
        //TextView tvInOut; 
    }
    
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
          

             rowView = inflater.inflate(R.layout.student_att_seminar, null);
             holder.tv=(TextView) rowView.findViewById(R.id.tvNameStdSeminar);
             holder.llayoutPresenceInSemi = (LinearLayout) rowView.findViewById(R.id.llayoutSeminarIn_Out); 
             //holder.img=(ImageView) rowView.findViewById(R.id.imageViewStudentPhoto);
             //holder.tvInOut=(TextView) rowView.findViewById(R.id.tvInOut); 
             //holder.tggBut =(ToggleButton) rowView.findViewById(R.id.toggleButton1); 

           // insert the student name inside the cell
             holder.tv.setText(result[position]);
         //holder.img.setImageResource(imageId[position]);

         rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked on "+result[position], Toast.LENGTH_LONG).show();
                
                
            }
        });
       
         
         
         //change color of student name cell according to click and add or remove name of the stdent in the arraylist
         holder.llayoutPresenceInSemi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isInSemi==0){
					holder.llayoutPresenceInSemi.setBackgroundResource(R.drawable.button_shape_green);
					isInSemi=1; 
	                Toast.makeText(context, ""+result[position]+" is present", Toast.LENGTH_LONG).show();
	                //check whether a std name is already in the arrayList or not before adding it (add only when the name is not there already)
	                if(listof_present_Std.contains(result[position])){
	                	Log.e("name already in the list", ""+result[position]);
	                }else{
	                	listof_present_Std.add(result[position]); // add the student name in the arrayList
	                	//list_ofMy_guy =TextUtils.join("", listof_present_Std);
	                	tinydb.putListString("MyUsers", listof_present_Std); // put name of std in the sharedfile of the TinyDB
	                	
	                }
	                
	                

				}
				else{
					holder.llayoutPresenceInSemi.setBackgroundResource(R.drawable.buttonshape);
					isInSemi=0;
	                Toast.makeText(context, ""+result[position]+" is absent", Toast.LENGTH_LONG).show();
	                listof_present_Std.remove(result[position]);
	               // list_ofMy_guy =TextUtils.join("", listof_present_Std);
	                tinydb.putListString("MyUsers", listof_present_Std); // put name of std in the sharedfile of the TinyDB
	                //tinydb.putInt("numberAtt", listof_present_Std.size()); // put the number of student attending the seminar in the db

				}
				System.out.println("list of present student: "+listof_present_Std);
				tinydb.putInt("numberAtt", listof_present_Std.size()); // put the number of student attending the seminar in the db
            	Log.e("#ofpresent", ""+tinydb.getInt("numberAtt"));
				//System.out.println("list stringer = "+list_ofMy_guy);
			}
		});
        
        return rowView;
    }
    
    public ArrayList<String> getListofAtt(){
    	
    	String lits = TextUtils.join("", listof_present_Std);
        System.out.println("list stringer = "+lits);
		return listof_present_Std;
   	 
    }
}
