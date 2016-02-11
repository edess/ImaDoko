package elder.example.imadoko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import elder.example.imadoko.AfterMain.RetrieveFeedTask;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class UbiSeminar extends ActionBarActivity {
	
	Session session=null; 
	ProgressDialog pdialog =null;
	Context context = null;
	
	public static String [] studentNameList={"Ashar","Kashimoto","Marko","Edith","Hirabe","Kawamura","Komai","Fujisawa","Maenaka",
 	   "Matsumoto", "Morishita","Tatsuro","Yoi","Elder", "Arakawa", "Kakigi","Kanehira", "Koshiba", "Yosuke","Nakagawa",
 	   "Nakamura","Kyoji","Maeda","Yuki","Moriya","Konan"};
	
	GridView gView;
	ArrayList<String> ListPresence = new ArrayList<String>(); 
	TinyDB tinydb; 
	String TypeofSemi; 
	int year_x, month_x, day_x; //variable for datePicker
	static final int DIALOG_ID=0;
	Button btnSettingDate; 
	TextView dateOfSemi; 
	TextView nbre_of_Attendees;
	int attStd; 
	ArrayList<String> listof_Sensei_to_sendMail = new ArrayList<String>();
	 CheckBox chBoxFujimoto;
	CheckBox chBoxSuwa;
	CheckBox chBoxArakawa;
	CheckBox chBoxYasumoto;
	CheckBox chBoxElderTesting;
	
	String[] receiverSensei = {"Fujimoto", "Suwa", "Arakawa", "Yasumoto", "Elder Testing"}; //string array for alert dialog multi choice items
	String [] senseiMailAddress={"manato@is.naist.jp","h-suwa@is.naist.jp","ara@is.naist.jp","yasumoto@is.naist.jp","akpa.elder.zx6@is.naist.jp"}; 
	//String [] senseiMailAddress={"akpaelder@gmail.com","akpa.elder.zx6@is.naist.jp","akpa.elder.zx6@is.naist.jp","akpa.elder.zx6@is.naist.jp","akpa.elder.zx6@is.naist.jp"}; 
	String [] recipientProf=new String[5];
	String RecipientProfEmailAdd; 
	ArrayList<String> presentElev; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubi_seminar);
		
		context=this;
		gView=(GridView) findViewById(R.id.gridViewSemiAttd);      
        gView.setAdapter(new CustomAdapter_seminar(this, studentNameList));
        tinydb = new TinyDB(context);
        tinydb.remove("MyUsers"); // do not clear all the shared pref, but only remove the table MyUsers
        
       
        
        final Calendar cal= Calendar.getInstance();
		year_x= cal.get(Calendar.YEAR);
		month_x=cal.get(Calendar.MONTH);
		day_x=cal.get(Calendar.DAY_OF_MONTH);
        
		
		dateOfSemi = (TextView)findViewById(R.id.tvDateofSemi);
		nbre_of_Attendees =(TextView)findViewById(R.id.tvNumbOfAtt);
		
		
        
        ImageView btnSendList = (ImageView)findViewById(R.id.imageViewSendList); 
        btnSendList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//confirm_sending_list();
				
				if(TypeofSemi!=null && !TypeofSemi.isEmpty()){
					confirm_sending_list_two(); 
					//variable to get the nb of attending student
					attStd = tinydb.getInt("numberAtt");
					nbre_of_Attendees.setText(""+attStd);
					nbre_of_Attendees.setTextColor(Color.BLUE);	
					}
				else{
					Toast toastSelectSeminarType = Toast.makeText(getApplicationContext(), "Please Select the seminar type and/or the seminar date", Toast.LENGTH_SHORT);
					toastSelectSeminarType.setGravity(Gravity.CENTER, 0, 0);
					toastSelectSeminarType.getView().setBackgroundColor(Color.RED);
					toastSelectSeminarType.show();
				}
				
				
			}
		});
        
        //RadioButton 
        RadioGroup rdioGrpeTypeSemi = (RadioGroup)findViewById(R.id.radioGroupButnSemiType); 
        //TypeofSemi =  ((RadioButton)findViewById(rdioGrpeTypeSemi.getCheckedRadioButtonId())).getText().toString();
        rdioGrpeTypeSemi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radioButtonTxtbook:
					TypeofSemi="Textbook Seminar";
					Toast.makeText(getApplicationContext(), ""+TypeofSemi, Toast.LENGTH_LONG).show();
					break;
				case R.id.radioButtonPaperS:
					TypeofSemi="Paper Seminar";
					Toast.makeText(getApplicationContext(), ""+TypeofSemi, Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		});
        
        //code for datepicker
        
        showDialogButtonClick(); // to display DatePicker
	}

	public void confirm_sending_list() {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
	    LayoutInflater inflater = this.getLayoutInflater();
	    final View dialogView = inflater.inflate(R.layout.custom_for_sending_attd_list, null);
	    dialogBuilder.setView(dialogView);
	    
	    dialogBuilder.setTitle("Okay to send the current list?? ");
	    //dialogBuilder.setMessage("Suwa先生とFujimoto先生だけアクセスをできる \nOnly Suwa sensei and Fujimoto sensei can access");
	    final TextView msgChooseRecSensei =(TextView)dialogView.findViewById(R.id.tvSendlistTo);
    	msgChooseRecSensei.setText("Select one or many Sensei to send the list of attendees");
    	
    	//get the list of student from sharedpref using the class TinyDB: 
    	//https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
    	ArrayList<String> presentElev = tinydb.getListString("MyUsers");
    	
    	Toast mToastpre= Toast.makeText(getApplicationContext(), "List of attendees = "+presentElev, Toast.LENGTH_LONG);
    	mToastpre.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
    	mToastpre.getView().setBackgroundColor(Color.BLUE);
    	mToastpre.show();
    	
    	//get the recipient of the listof attendee, from the checkbox 
    	
    	chBoxFujimoto = (CheckBox)dialogView.findViewById(R.id.checkBox_Fujimoto);
    	 chBoxSuwa = (CheckBox)dialogView.findViewById(R.id.checkBox_Suwa);
    	chBoxArakawa = (CheckBox)dialogView.findViewById(R.id.checkBox_Arakawa);
    	chBoxYasumoto = (CheckBox)dialogView.findViewById(R.id.checkBox_Yasumoto);
    	chBoxElderTesting = (CheckBox)dialogView.findViewById(R.id.checkBox_ElderTest);
    	
    	if(chBoxFujimoto.isChecked()){
    		listof_Sensei_to_sendMail.add("manato@is.naist.jp");
    		Log.d("prof", ""+listof_Sensei_to_sendMail); 
    	}
    	if(chBoxSuwa.isChecked()){
    		listof_Sensei_to_sendMail.add("h-suwa@is.naist.jp");
    		Log.d("prof", ""+listof_Sensei_to_sendMail);
    	}
    	if(chBoxArakawa.isChecked()){
    		listof_Sensei_to_sendMail.add("ara@is.naist.jp");
    		Log.d("prof", ""+listof_Sensei_to_sendMail);
    	}
    	if(chBoxYasumoto.isChecked()){
    		listof_Sensei_to_sendMail.add("yasumoto@is.naist.jp");
    		Log.d("prof", ""+listof_Sensei_to_sendMail);
    	}
    	if(chBoxElderTesting.isChecked()){
    		listof_Sensei_to_sendMail.add("akpa.elder.zx6@is.naist.jp");
    		Log.d("prof", ""+listof_Sensei_to_sendMail);
    	}
	    
	    //to do when send is push
	    dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	final EditText editPassword = (EditText) dialogView.findViewById(R.id.editTextPasswordSensei);//nn
	        	Toast.makeText(getApplicationContext(), "Send list to:"+listof_Sensei_to_sendMail, Toast.LENGTH_LONG).show();
				
	        }
	    });
	    
	    //to do when Cancel is push
	    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            //pass
	        }
	    });
	    AlertDialog b = dialogBuilder.create();
	    b.show();
		
	}
	
	public void confirm_sending_list_two() {
		
		//get the list of student from sharedpref using the class TinyDB: 
    	//https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
    	 presentElev = tinydb.getListString("MyUsers");
    	
    	Toast mToastpre= Toast.makeText(getApplicationContext(), "List of attendees = "+presentElev, Toast.LENGTH_LONG);
    	mToastpre.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
    	mToastpre.getView().setBackgroundColor(Color.BLUE);
    	mToastpre.show();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		 // Boolean array for initial selected items
        final boolean[] checkedProfByDefault = new boolean[]{
                true, // fujimoto
                true, // suwa
                false, // arakawa
                false, // yasumoto
                false // test_elder
        };
     // Convert the sensei array to list
        final List<String> senseiList = Arrays.asList(receiverSensei);
        
        builder.setMultiChoiceItems(receiverSensei, checkedProfByDefault, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				 // Update the current focused item's checked status
				checkedProfByDefault[which] = isChecked;

                // Get the current focused item
                String currentItem = senseiList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
				
			}
		});
        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Select the Recipient of the attendee list?");

        // Set the positive/yes button click listener
        builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                
                for (int i = 0; i<checkedProfByDefault.length; i++){
                    boolean checked = checkedProfByDefault[i];
                    if (checked) {
                       
                        Toast.makeText(getApplicationContext(), ""+senseiList.get(i), Toast.LENGTH_LONG).show();
                        RecipientProfEmailAdd=senseiMailAddress[i]; 
                        sendMessage(RecipientProfEmailAdd);
                        System.out.println("sending "+i+"th to "+RecipientProfEmailAdd );
                       //System.out.println("sending "+i+"th to "+recipientProf[i] );
                        
                        //recipientProf[i]=senseiMailAddress[i]; 
                        //sendMessage(recipientProf);
                        
                        
                        
                    }
                }
            }
        });
        
     // Set the negative/no button click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });
        //System.out.println("final recipients=  "+recipientProf);
        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
	}
	
	public void sendMessage(String recipientProfEmailAdd){
	if(recipientProfEmailAdd!=null && !recipientProfEmailAdd.isEmpty()){
		
			Log.e("email @ of the recipient professor", ""+recipientProfEmailAdd);
		
		 Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		  Log.d("debugging ", "check 1");
		
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				Log.d("debugging ", "check 2");
				return new PasswordAuthentication("ubi.ima.doko@gmail.com", "greenubi"); 
			}
			
		}); 
		pdialog =ProgressDialog.show(context, "", "Sending list of attendees of seminar...", true); 
		RetrieveFeedTask task = new RetrieveFeedTask();
		task.execute();
		}
	
	else{
		Toast toastSelectRecipient = Toast.makeText(getApplicationContext(), "Please select one sensei to send the list", Toast.LENGTH_SHORT);
		toastSelectRecipient.show();
	}
}
	
	class RetrieveFeedTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("ubi.ima.doko@gmail.com"));
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientProfEmailAdd));
				/*
				InternetAddress[] recProfAddressUbi= new InternetAddress[recipientProf.length];
				int myCounter =0;
				for(String RecipientProfEmailAdd:recipientProf){
					recProfAddressUbi[myCounter]= new InternetAddress(RecipientProfEmailAdd.trim());
					myCounter++;
				}
				
				message.setRecipients(Message.RecipientType.TO, recProfAddressUbi);*/
				message.setSubject("[UBI-PRESENCE-LIST]");
				message.setText("Seminar= "+TypeofSemi+", date= "+dateOfSemi.getText()+", attendees="+presentElev +", total="+attStd+" students");  //send the list of presence, seminar type and day to the recipient prof 
				Transport.send(message);
				
			}catch(MessagingException e ){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pdialog.dismiss();
			Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void showDialogButtonClick(){
		btnSettingDate=(Button)findViewById(R.id.btnSettgDateSemi);
		btnSettingDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_ID);
				
			}
		});
	}
	

	@Override
	protected Dialog onCreateDialog(int id){
		
		if(id==DIALOG_ID)
			return new DatePickerDialog(this, dpickerListner, year_x,month_x,day_x); 
		
		return null; 
		
	}
	
	private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			// TODO Auto-generated method stub
			
			year_x= year; 
			month_x= monthOfYear+1;
			day_x=dayOfMonth;
			
			//Toast.makeText(getApplicationContext(), year_x+"/"+month_x +"/"+day_x, Toast.LENGTH_SHORT).show();
			
			dateOfSemi.setText(year_x+"/"+month_x +"/"+day_x);
			dateOfSemi.setTextColor(Color.BLUE);
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ubi_seminar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
