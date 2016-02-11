package elder.example.imadoko;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetAllStudentDetailsListViewAdapter extends BaseAdapter {
	
	private JSONArray dataArray;
	private Activity activity;
	private static LayoutInflater inflater=null;
	
	public GetAllStudentDetailsListViewAdapter(JSONArray jsonArray, Activity a){
		
		this.dataArray=jsonArray;
		this.activity=a; 
		
		inflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.dataArray.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// set up convert view if it is null
				ListCell cell;
				if(convertView==null)
				{
					convertView=inflater.inflate(R.layout.get_all_student_details, null);
					cell = new ListCell();
					cell.student_name=(TextView)convertView.findViewById(R.id.tvStdName);
					cell.student_level=(TextView)convertView.findViewById(R.id.tvStdLev);
					//cell.numbAcceptImg=(TextView)convertView.findViewById(R.id.textView_Already_Accepted);
					//cell.support=(TextView)convertView.findViewById(R.id.textView_Support);
					cell.imageStudentImage=(ImageView)convertView.findViewById(R.id.imageViewStdPicture);
					cell.btn_inOut_Secret=(Button)convertView.findViewById(R.id.btn_In_or_Out);
					
					convertView.setTag(cell);
				}
				else
				{
					cell=(ListCell)convertView.getTag(); 
				}
				
				try
				{
					JSONObject jsonObject = this.dataArray.getJSONObject(position);
					cell.student_name.setText(jsonObject.getString("student_name"));
					cell.student_level.setText(jsonObject.getString("student_level"));
					//cell.numbAcceptImg.setText(jsonObject.getString("Numb_Accepted_images"));
					//cell.support.setText(jsonObject.getString("Support"));
					
					//get whether the student is in lab or not from the db
					
					int where_is_the_student = jsonObject.getInt("in_lab");
					if(where_is_the_student==1)
					{
						cell.btn_inOut_Secret.setBackgroundResource(R.drawable.button_in_lab_secretary);
						cell.btn_inOut_Secret.setText("In lab");
					}
					else{
						cell.btn_inOut_Secret.setBackgroundResource(R.drawable.button_in_out_secretary);
						cell.btn_inOut_Secret.setText("Not in lab");
					}
					
					
					
					String foodNameForIcon = jsonObject.getString("student_name");
					if(foodNameForIcon.equals("Ashar"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.ashar);
					}
					else if(foodNameForIcon.equals("Kashimoto"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.kashimoto);
					}
					else if(foodNameForIcon.equals("Marko"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.marko);
					}
					else if(foodNameForIcon.equals("Edith"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.edith);
					}
					else if(foodNameForIcon.equals("Hirabe"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.hirabe_yuko);
					}
					else if(foodNameForIcon.equals("Kawamura"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.kawamura_kazuki);
					}
					else if(foodNameForIcon.equals("Komai"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.komai_kiyoaki);
					}
					else if(foodNameForIcon.equals("Fujisawa"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.fujisawa_kazuki);
					}
					else if(foodNameForIcon.equals("Maenaka"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.maenaka_shogo);
					}
					else if(foodNameForIcon.equals("Matsumoto"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.matsumoto_seigi);
					}
					else if(foodNameForIcon.equals("Morishita"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.morishita_shigeya);
					}
					else if(foodNameForIcon.equals("Tatsuro"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.yasukawa_tatsuro);
					}
					else if(foodNameForIcon.equals("Yoi"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.yoi_kenji);
					}
					else if(foodNameForIcon.equals("Elder"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.akpa);
					}
					else if(foodNameForIcon.equals("Arakawa"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.arakawa_sh);
					}
					else if(foodNameForIcon.equals("Kakigi"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.kakigi);
					}
					else if(foodNameForIcon.equals("Kanehira"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.kanehira);
					}
					else if(foodNameForIcon.equals("Koshiba"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.koshiba);
					}
					else if(foodNameForIcon.equals("Yosuke"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.tamura);
					}
					else if(foodNameForIcon.equals("Nakagawa"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.nakagawa);
					}
					else if(foodNameForIcon.equals("Nakamura"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.nakamura);
					}
					else if(foodNameForIcon.equals("Kyoji"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.hata);
					}
					else if(foodNameForIcon.equals("Maeda"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.maeda);
					}
					else if(foodNameForIcon.equals("Yuki"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.matsuda);
					}
					else if(foodNameForIcon.equals("Moriya"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.moriya);
					}
					else if(foodNameForIcon.equals("Konan"))
					{
						cell.imageStudentImage.setImageResource(R.drawable.cedric);
					}
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
				
				return convertView;
			
	}
	
	private class ListCell{
		
		private TextView student_name;
		private TextView student_level;
		//private TextView numbAcceptImg;
		//private TextView support;
		private ImageView imageStudentImage; 
		private Button btn_inOut_Secret; 
	}

}
