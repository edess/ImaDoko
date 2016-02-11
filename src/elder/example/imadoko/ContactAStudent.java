package elder.example.imadoko;

import java.util.Properties;

import javax.mail.*;  
import javax.mail.internet.*;  

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class ContactAStudent extends ActionBarActivity {
	
	AutoCompleteTextView autoCompTvSender;
	AutoCompleteTextView autoCompTvRecipient;
	String senderName="edess", recipientName; 
	String[] sendFrom_to; 
	String RecipientEmailAdd = "akpa.elder.zx6@is.naist.jp"; 
	//String RecipientEmailAdd = "dexterkonan@gmail.com"; 
	String recEmailadd; 
	
	Session session=null; 
	ProgressDialog pdialog =null; 
	Context context = null; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_astudent);
		
		context=this; 
		
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, STUDENT_NAME);
		
		 //autoComplet tv for sender
		 autoCompTvSender = (AutoCompleteTextView)findViewById(R.id.autoCompleteTvFrom);
		 autoCompTvSender.setAdapter(adapter);
		 autoCompTvSender.setThreshold(1);
		 senderName= autoCompTvSender.getText().toString();
		autoCompTvSender.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				  senderName = (String)parent.getItemAtPosition(position);
				 Log.e("name sender", ""+senderName); 
			}
		});
		 
		
		 
		 //autoComplet tv for receiver 
		 autoCompTvRecipient =(AutoCompleteTextView)findViewById(R.id.autoCompleteTvSendTo);
		 autoCompTvRecipient.setAdapter(adapter);
		 autoCompTvRecipient.setThreshold(1);
		 recipientName= autoCompTvRecipient.getText().toString();
		 autoCompTvRecipient.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					// TODO Auto-generated method stub
					  recipientName = (String)parent.getItemAtPosition(position);
					 Log.e("name receiver", ""+recipientName); 
					 //recEmailadd = STUDENT_EMAIL_ADDRESS[position];
					 
				}
			});
		 
		 
		 sendFrom_to= new String[] { senderName, recipientName}; 
		 Log.e("sender receiver", ""+sendFrom_to[0]+ ""+sendFrom_to[1]); 
		 
		 //button to send the msg
		 Button btnSendMsg = (Button)findViewById(R.id.buttonSendMsg); 
		 btnSendMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("button check", "ok"); 
				RecipientEmailAdd = matchNameToAddress(recipientName); 
				sendMessage(RecipientEmailAdd); 
				Toast.makeText(getApplicationContext(), "sending from "+senderName+"...to "+recipientName, Toast.LENGTH_LONG).show();

			}

			
			private String matchNameToAddress(String recipientName) {
				// TODO Auto-generated method stub
				StudentName name = StudentName.valueOf(recipientName); 
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
			
		});
		
	}
	
	private static final String[] STUDENT_NAME = new String[] {
		"Ashar","Kashimoto","Marko","Edith","Hirabe","Kawamura","Komai","Fujisawa","Maenaka",
 	   "Matsumoto", "Morishita","Tatsuro","Yoi","Elder", "Arakawa", "Kakigi","Kanehira", "Koshiba", "Yosuke","Nakagawa",
 	   "Nakamura","Kyoji","Maeda","Yuki","Moriya","Konan"
    };
	
	private enum StudentName{ Ashar,Kashimoto,Marko,Edith,Hirabe,Kawamura,Komai,Fujisawa,Maenaka,Matsumoto,Morishita,Tatsuro,Yoi,Elder, 
		Arakawa, Kakigi,Kanehira, Koshiba,Yosuke,Nakagawa,Nakamura,Kyoji,Maeda,Yuki,Moriya,Konan}
	
	private static final String[] STUDENT_EMAIL_ADDRESS = new String[] {"muhammad_ashar.ls6@is.naist.jp","yukitoshi-k@is.naist.jp","marko.trono.mg8@is.naist.jp",
		"edith.luhanga.ef6@is.naist.jp","hirabe.yuko.ho2@is.naist.jp","kawamura.kazuki.ka3@is.naist.jp", "komai.kiyoaki.kb9@is.naist.jp", "fujisawa.kazuki.ey4@is.naist.jp",
		"maenaka.shogo.me4@is.naist.jp","matsumoto.seigi.mj0@is.naist.jp","morishita.shigeya.mk6@is.naist.jp","yasukawa.tatsuro.ym8@is.naist.jp","yoi.kenji.yc6@is.naist.jp",
		"akpa.elder.zx6@is.naist.jp","arakawa.shuzo.aj4@is.naist.jp","kakigi.kento.jy7@is.naist.jp","kanehira.takuya.kj7@is.naist.jp","koshiba.ryota.km2@is.naist.jp",
		"tamura.yosuke.tp8@is.naist.jp","nakagawa.eri.nz6@is.naist.jp","nakamura.yugo.ns0@is.naist.jp","hata.kyoji.gx0@is.naist.jp","maeda.naoki.md9@is.naist.jp","matsuda.yuki.mr3@is.naist.jp",
		"moriya.kazuki.me0@is.naist.jp","konan.cedric.js5@is.naist.jp"};
	
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
				message.setText(senderName+" is looking for you in the lab. Please contact him via mail or Line.\n\n"+senderName +"さんはあなたを探しています。メールかLINEで連絡してください！\n\n\n\n Regards! よろしくお願いします！ ");  
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_astudent, menu);
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
