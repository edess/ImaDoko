package elder.example.imadoko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CustomAdapter extends BaseAdapter {

	String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
     int isInLab = 0; 
     List<Integer>  whereNow;// an arraylist to hold the presence of student. '0' means outside lab, '1' inside 
     TinyDB tinydb ;
     ArrayList<Integer> actualInOut; 
     
     
    public CustomAdapter(Activity AfterMain, String[] studentNameList, int[] studentImages) {
        // TODO Auto-generated constructor stub
        result=studentNameList;
        context=AfterMain;
        imageId=studentImages;
         inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         tinydb = new TinyDB(context);
         
         int x = result.length; // the size or length of coming studentNameList
         System.out.println("Element: "+x);
        
         
         whereNow = new ArrayList<Integer>(Collections.nCopies(x, 0)); // create the array list with set size and default value=0
       
      // Loop through elements.
     	for (int i = 0; i < whereNow.size(); i++) {
     	    int value = whereNow.get(i);
     	    System.out.println("Element: "+i+"th =" + value);
     	}
     	
     	
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
        ImageView img;
        ToggleButton tggBut; 
        TextView tvInOut; 
    }
    @Override
    public View getView(final int positionCust, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
          

             rowView = inflater.inflate(R.layout.student_list, null);
             holder.tv=(TextView) rowView.findViewById(R.id.textViewStudentName);
             holder.img=(ImageView) rowView.findViewById(R.id.imageViewStudentPhoto);
             //holder.tvInOut=(TextView) rowView.findViewById(R.id.tvInOut); 
             holder.tggBut =(ToggleButton) rowView.findViewById(R.id.toggleButton1); 

         holder.tv.setText(result[positionCust]);
         holder.img.setImageResource(imageId[positionCust]);
         
      // Loop through elements to put in green or red the button of presence based the tinydb .
         actualInOut=tinydb.getListInt("WhereInOut");
      	System.out.println("arrayint ="+actualInOut);
      	int check = actualInOut.get(positionCust);
      	Log.d("check value", ""+check);
      	switch (check) {
		case 1:
			holder.tggBut.setBackgroundResource(R.drawable.button_shape_green);
			break;
		case 0:
			holder.tggBut.setBackgroundResource(R.drawable.buttonshape);
			break;

		default:
			break;
		}

         rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked on "+result[positionCust], Toast.LENGTH_LONG).show();
                
                
            }
        });
         
        /*holder.tvInOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isInLab==0){
					holder.tvInOut.setBackgroundColor(Color.GREEN);
					holder.tvInOut.setText("In the lab");
					isInLab=1; 
				}
				else{
					holder.tvInOut.setBackgroundColor(Color.RED);
					holder.tvInOut.setText("Not in the lab");
					isInLab=0; 
					
				}
				
				
			}
		});*/
         
         //ToggleButton TggBtn=(ToggleButton)findViewById(R.id.toggleButton1);// or get it from the layout by ToggleButton Btn=(ToggleButton) findViewById(R.id.IDofButton);
         holder.tggBut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
  
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 // TODO Auto-generated method stub 
                 if(isChecked){
                     //buttonView.setBackgroundColor(Color.GREEN);
                	 whereNow.set(positionCust, 1);
                 	buttonView.setBackgroundResource(R.drawable.button_shape_green);
                 	System.out.println("stdent at "+positionCust+" is present and Arraylist whereNow is "+whereNow.get(positionCust) +" at the positionCust"+positionCust);
                 	
                 	tinydb.putListInt("WhereInOut", (ArrayList<Integer>) whereNow);
                 }
                 else {
                	 //buttonView.setBackgroundColor(Color.GRAY);
                	 whereNow.set(positionCust, 0);
                	 buttonView.setBackgroundResource(R.drawable.button_shape_red);
                	 tinydb.putListInt("WhereInOut", (ArrayList<Integer>) whereNow);
                  	System.out.println("stdent at "+positionCust+" is now absent and Arraylist whereNow is "+whereNow.get(positionCust) +" at the positionCust"+positionCust);

                	 }
             } 
         }); 
      
        return rowView;
    }
    

}
