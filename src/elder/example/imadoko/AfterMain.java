package elder.example.imadoko;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class AfterMain extends ActionBarActivity {
	
	GridView gv;
    //Context context;    
       ArrayList prgmName; 
       ImageView img_of_Anna; 
       public static String [] studentNameList={"Ashar","Kashimoto","Marko","Edith","Hirabe","Kawamura","Komai","Fujisawa","Maenaka",
    	   "Matsumoto", "Morishita","Tatsuro","Yoi","Elder", "Arakawa", "Kakigi","Kanehira", "Koshiba", "Yosuke","Nakagawa",
    	   "Nakamura","Kyoji","Maeda","Yuki","Moriya","Konan"};
   public static int [] studentImages={R.drawable.ashar,R.drawable.kashimoto,R.drawable.marko,R.drawable.edith,R.drawable.hirabe_yuko,
	   									R.drawable.kawamura_kazuki,R.drawable.komai_kiyoaki,R.drawable.fujisawa_kazuki,R.drawable.maenaka_shogo,
	   									R.drawable.matsumoto_seigi, R.drawable.morishita_shigeya, R.drawable.yasukawa_tatsuro, R.drawable.yoi_kenji, R.drawable.akpa,
	   									R.drawable.arakawa_sh, R.drawable.kakigi,R.drawable.kanehira, R.drawable.koshiba, R.drawable.tamura, R.drawable.nakagawa, R.drawable.nakamura,R.drawable.hata, 
	   									R.drawable.maeda,R.drawable.matsuda,R.drawable.moriya,R.drawable.cedric}; 
   
   Session session=null; 
	ProgressDialog pdialog =null;
	Context context = null;
   String NameofSender, Nameofrecipient; 
   String RecipientEmailAdd; 
   String recEmailadd; 
   
   String Suwa_Fuji_Password ="12345"; 
   String Kanaoka_Password ="k123";
   String checkEnterPass="nothing"; 
   
   Button Upload_studentsPosition;
   TinyDB tinydb ;
   ArrayList<Integer> actualInOut_AfterMain; 
   
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_after_main);
		
		context=this;
		tinydb = new TinyDB(context);
		
		gv=(GridView) findViewById(R.id.gridViewPhD);      
        gv.setAdapter(new CustomAdapter(this, studentNameList,studentImages));
       
        gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//showContactStudentPopUp(position);
				Toast.makeText(getApplicationContext(), "hhhmmmmmm", Toast.LENGTH_LONG).show();
				
			}
		});
        
        gv.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "You selected "+ position, Toast.LENGTH_LONG).show();
			//showContactStudentPopUp(position);

			return false;
		}
	});
        
        
		Button btnMoreOptions =(Button)findViewById(R.id.buttonMoreOptions); 
		btnMoreOptions.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder builderInfo = new AlertDialog.Builder(AfterMain.this);
						builderInfo.setTitle("Information - Details");
						builderInfo.setItems(R.array.list_of_options, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								switch (which) {
								case 0:
									startActivity(new Intent(AfterMain.this, ContactAStudent.class));
									break;
								case 1:
									startActivity(new Intent(AfterMain.this, SendGroupMessage.class));
									break;
								case 2:
									startActivity(new Intent(AfterMain.this, SetLongTimeAbsence.class)); 
									break;
								case 3:
									startActivity(new Intent(AfterMain.this, ContactDevelopers.class));
									//Toast.makeText(getApplicationContext(), "You clicked on "+which, Toast.LENGTH_LONG).show();
		
									break;
		
								default:
									Toast.makeText(getApplicationContext(), "You clicked on nothing", Toast.LENGTH_LONG).show();
		
									break;
								}
							}
						}); 
						AlertDialog dialog = builderInfo.create(); 
						dialog.show();
					}
				});
		
		ImageView imgFastMail =(ImageView)findViewById(R.id.ivFastMail); 
		imgFastMail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFastDialog(); 
				
			}
		});
		
		ImageView ubiSeminar =(ImageView)findViewById(R.id.imageViewUbiSeminar);
		ubiSeminar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Confirm_Suwa_FujiSensei_kanaoka("Suwa先生とFujimoto先生" , Suwa_Fuji_Password, 1);
				
			}
		});
		
		// manage the click on the secretary iv 
		ImageView ubiSecret =(ImageView)findViewById(R.id.ivSecretary); 
		ubiSecret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Confirm_Suwa_FujiSensei_kanaoka("Kanahoka さん" , Kanaoka_Password, 2);
				
			}
		});
		
		// button of device manager
		ImageView ubiDeviceMang =(ImageView)findViewById(R.id.ivDeviceMan); 
		ubiDeviceMang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopUpAlert();
				
			}
		});
		
		// button of device manager
				ImageView ubiGroupMail =(ImageView)findViewById(R.id.ivGroupMail); 
				ubiGroupMail.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						showPopUpAlert();
						
					}
				});
				
			//get data from tinydb
				actualInOut_AfterMain=tinydb.getListInt("WhereInOut");
		     	Log.e("array int after login",""+actualInOut_AfterMain);
	     	//convert the arraylist to array to string
		     	/*int [] position_zero_un = actualInOut_AfterMain.toArray(new int[actualInOut_AfterMain.size()]);
		     	
		     	String[][]d_name_Plus_presence = new String[studentNameList.length][1];
		     	for (int i = 0; i < studentNameList.length; i++)
		     	{ 
		     		d_name_Plus_presence[i][0] = studentNameList[i] + " " + position_zero_un[i];
		     	 
		     	} 
		     	System.out.println("2dim table= "+d_name_Plus_presence);
				*/
			//manage the upload button
				Upload_studentsPosition =(Button)findViewById(R.id.btn_Upload);
				Upload_studentsPosition.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						new UploadStudentPositionDetails().execute(new ApiConnectorImaDoko());
					}
				});
	}
	
	//private class managing the upload of the student presence to the db
	private class UploadStudentPositionDetails extends AsyncTask<ApiConnectorImaDoko, Long, JSONArray>{

		@Override
		protected JSONArray doInBackground(ApiConnectorImaDoko... params) {
			// TODO Auto-generated method stub
			//nn
			return params[0].makeGetRequest("http://160.16.101.96/~cedric-k/elder_imaDoko/fetchStudentDetails.php");// change this;;;;;;;;;;;;;;''
		}
		@Override
		protected void onPostExecute(JSONArray jsonArray) {
	         //setTextToTextview(jsonArray);
			//setListAdapter(jsonArray);
			Toast.makeText(getApplicationContext(), "STUDENT Presence sent to the database", Toast.LENGTH_LONG).show();
	     }
		
	}
	
protected void Confirm_Suwa_FujiSensei_kanaoka(String username, final String password, final int typeForswitch) {
		//I added typeForSwitch to allow me to easily switch between kanaoka san and the sensei
		//if typeForswitch 1 = suwaand fujimoto, and typeforSwitch = 2 is for kanaoka
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
	    LayoutInflater inflater = this.getLayoutInflater();
	    final View dialogView = inflater.inflate(R.layout.custom_for_suwa_fuji_confirm, null);
	    dialogBuilder.setView(dialogView);
	    
	    dialogBuilder.setTitle("Are you "+username+ "?");
	    dialogBuilder.setMessage(username+" だけアクセスをできる \nOnly "+ username+"can access");
	    
	  
	    
	    //to do when Confirm button is pushed
	    dialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	final EditText editPassword = (EditText) dialogView.findViewById(R.id.editTextPasswordSensei);
	     	   checkEnterPass  = editPassword.getText().toString(); 
	     	  Log.e("entered passw value", ""+checkEnterPass);
	     	  System.out.println("enter pass = "+checkEnterPass);
	        	
	        	if( checkEnterPass.equals(password)){
	        		Toast.makeText(getApplicationContext(), "password validated", Toast.LENGTH_LONG).show();
	        		
	        		switch (typeForswitch) {
					case 1:
						startActivity(new Intent(AfterMain.this, UbiSeminar.class));
						break;
					case 2:
						startActivity(new Intent(AfterMain.this, ForTheSecretary.class));
						break;

					default:
						break;
					}
	        	}
	        	else{
	        		 Toast toastWrongPass = Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_LONG);
	        		 toastWrongPass.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	        		 toastWrongPass.getView().setBackgroundColor(Color.RED);
	        		 toastWrongPass.show();
	        	}
				
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

public void showFastDialog(){
	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, STUDENT_NAME); // adapter for autocompleteTV
				//https://bhavyanshu.me/tutorials/create-custom-alert-dialog-in-android/08/20/2015/
	
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		    LayoutInflater inflater = this.getLayoutInflater();
		    final View dialogView = inflater.inflate(R.layout.custom_for_dialog_fast_email, null);
		    dialogBuilder.setView(dialogView);
		
		    //final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
		    
		    //code for the autoComp of sender tv
		    final AutoCompleteTextView autoCompTvofSender =(AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteTvSender);
		    autoCompTvofSender.setAdapter(adapter);
			 autoCompTvofSender.setThreshold(1);
			 autoCompTvofSender.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
						// TODO Auto-generated method stub
						  NameofSender = (String)parent.getItemAtPosition(position);
						 Log.e("name sender", ""+NameofSender); 
					}
				});
			 
			//code for the autoComp of recipient tv
			    final AutoCompleteTextView autoCompTvofReceiver =(AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteTvRecipient);
			    autoCompTvofReceiver.setAdapter(adapter);
			    autoCompTvofReceiver.setThreshold(1);
			    autoCompTvofReceiver.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
							// TODO Auto-generated method stub
							  Nameofrecipient = (String)parent.getItemAtPosition(position);
							 Log.e("name of recipient", ""+Nameofrecipient); 
						}
					});
		
		    dialogBuilder.setTitle("Send a quick message");
		    dialogBuilder.setMessage("Enter your name and choose the name of who you're looking for!!");
		    
		    //to do when send is push
		    dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		        	RecipientEmailAdd = matchNameToAddress(Nameofrecipient); 
					sendMessage(RecipientEmailAdd); 
					Toast.makeText(getApplicationContext(), "sending from "+NameofSender+"...to "+Nameofrecipient, Toast.LENGTH_LONG).show();
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
	
/*public void showContactStudentPopUp( int studentPoz){
    	
		img_of_Anna= new ImageView(this);
		img_of_Anna.setImageResource(R.drawable.anna);
		final int poz = studentPoz; // position of the pet food in the grid 
        AfterMain.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(AfterMain.this);
                builder.setTitle("Do you want to contact "+studentNameList[poz]+" ?");
                builder.setMessage("Click on OK to send a quick message to = "+studentNameList[poz]) 
                       .setCancelable(false)
                       .setView(img_of_Anna)
                       .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                        	   startActivity(new Intent(AfterMain.this, ContactAStudent.class)); 
               				
                           }
                       })
                       .setNegativeButton("NO", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
                       
                	
                AlertDialog alert = builder.create();
                alert.show();               
            }
        });
    }*/

protected String matchNameToAddress(String nameofrecipient) {
	StudentName name = StudentName.valueOf(nameofrecipient); 
	switch (name) {
	case Ashar:
		recEmailadd="muhammad_ashar.ls6@is.naist.jp"; 
		break;
	case Kashimoto:
		recEmailadd="kashimoto.yukitoshi.km3@is.naist.jp"; 
		break;
	case Marko:
		recEmailadd="marko.trono.mg8@is.naist.jp"; 
		break;
	case Edith:
		recEmailadd="edith.luhanga.ef6@is.naist.jp"; 
		break;
	case Hirabe:
		recEmailadd="hirabe.yuko.ho2@is.naist.jp"; 
		break;
	case Kawamura:
		recEmailadd="kawamura.kazuki.ka3@is.naist.jp"; 
		break;
	case Komai:
		recEmailadd="komai.kiyoaki.kb9@is.naist.jp"; 
		break;
	case Fujisawa:
		recEmailadd="fujisawa.kazuki.ey4.naist.jp"; 
		break;
	case Maenaka:
		recEmailadd="maenaka.shogo.me4@is.naist.jp"; 
		break;
	case Matsumoto:
		recEmailadd="matsumoto.seigi.mj0@is.naist.jp"; 
		break;
	case Morishita:
		recEmailadd="morishita.shigeya.mk6@is.naist.jp"; 
		break;
	case Tatsuro:
		recEmailadd="yasukawa.tatsuro.ym8@is.naist.jp"; 
		break;
	case Yoi:
		recEmailadd="yoi.kenji.yc6@is.naist.jp"; 
		break;
	case Elder:
		recEmailadd="akpa.elder.zx6@is.naist.jp"; 
		break;
	case Arakawa:
		recEmailadd="arakawa.shuzo.aj46@is.naist.jp"; 
		break;
	case Kakigi:
		recEmailadd="kakigi.kento.jy7@is.naist.jp"; 
		break;
	case Kanehira:
		recEmailadd="kanehira.takuya.kj7@is.naist.jp"; 
		break;
	case Koshiba:
		recEmailadd="koshiba.ryota.km2@is.naist.jp"; 
		break;
	case Yosuke:
		recEmailadd="tamura.yosuke.tp8@is.naist.jp"; 
		break;
	case Nakagawa:
		recEmailadd="nakagawa.eri.nz6@is.naist.jp"; 
		break;
	case Nakamura:
		recEmailadd="nakamura.yugo.ns0@is.naist.jp"; 
		break;
	case Kyoji:
		recEmailadd="hata.kyoji.gx0@is.naist.jp"; 
		break;
	case Maeda:
		recEmailadd="maeda.naoki.md9@is.naist.jp"; 
		break;
	case Yuki:
		recEmailadd="matsuda.yuki.mr3@is.naist.jp"; 
		break;
	case Moriya:
		recEmailadd="moriya.kazuki.me0@is.naist.jp"; 
		break;
	case Konan:
		recEmailadd="konan.cedric.js5@is.naist.jp"; 
		break;

	default:
		break;
	}
	return recEmailadd; 
}


	private static final String[] STUDENT_NAME = new String[] {
	"Ashar","Kashimoto","Marko","Edith","Hirabe","Kawamura","Komai","Fujisawa","Maenaka",
	   "Matsumoto", "Morishita","Tatsuro","Yoi","Elder", "Arakawa", "Kakigi","Kanehira", "Koshiba", "Yosuke","Nakagawa",
	   "Nakamura","Kyoji","Maeda","Yuki","Moriya","Konan","Yasumoto Sensei","Arakawa Sensei","Suwa Sensei","Fujimoto Sensei", "Kanaoka san"};

	private enum StudentName{ Ashar,Kashimoto,Marko,Edith,Hirabe,Kawamura,Komai,Fujisawa,Maenaka,Matsumoto,Morishita,Tatsuro,Yoi,Elder, 
	Arakawa, Kakigi,Kanehira, Koshiba,Yosuke,Nakagawa,Nakamura,Kyoji,Maeda,Yuki,Moriya,Konan}


	public void sendMessage(String RecipientEmailAdd){
	if(RecipientEmailAdd!=null && !RecipientEmailAdd.isEmpty()){
		
		Log.e("email @ of the recipient", ""+RecipientEmailAdd);
	
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
	pdialog =ProgressDialog.show(context, "", "Sending...", true); 
	RetrieveFeedTask task = new RetrieveFeedTask();
	task.execute();
	}
	else{
		Toast toastSelectRecipient = Toast.makeText(getApplicationContext(), "Please select the person you're looking for", Toast.LENGTH_SHORT);
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
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmailAdd));
				message.setSubject("[UBI-IMA DOKO ?]");
				//message.setContent("Someone is looking for you in the lab", "text/html; charset-utf-8");
				message.setText(NameofSender +"はあなたを探しています。メールかLINEで連絡してください！\n\n"  + NameofSender+" is looking for you in the lab. Please come to lab or contact him via mail or Line.\n\n Regards! よろしくお願いします！ ");  
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
	
	 public void showPopUpAlert(){
	    	
	    	final ImageView imageOfWorkInpro= new ImageView(this);
	    	imageOfWorkInpro.setImageResource(R.drawable.work_in_progress);
	        AfterMain.this.runOnUiThread(new Runnable() {
	            public void run() {
	                AlertDialog.Builder builder = new AlertDialog.Builder(AfterMain.this);
	                builder.setTitle("Work in progress....");
	                builder.setMessage(" 少し待ってください！まだおわってない！ありがとうございます！\n Please wait a little ! We are still working on this feature.\nThank you.")  
	                       .setCancelable(false)
	                       .setView(imageOfWorkInpro)
	                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                           public void onClick(DialogInterface dialog, int id) {
	                           }
	                       });                     
	                AlertDialog alert = builder.create();
	                alert.show();               
	            }
	        });
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.after_main, menu);
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
