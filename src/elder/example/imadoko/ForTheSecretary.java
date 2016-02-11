package elder.example.imadoko;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ForTheSecretary extends ActionBarActivity {
	
	private ListView getAllStudentDetailsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_the_secretary);
		
		getAllStudentDetailsListView=(ListView)findViewById(R.id.listViewStdPosition);
		
		new GetAllStudentDetails().execute(new ApiConnectorImaDoko());
	}
	
	private class GetAllStudentDetails extends AsyncTask<ApiConnectorImaDoko, Long, JSONArray>{

		@Override
		protected JSONArray doInBackground(ApiConnectorImaDoko... params) {
			// TODO Auto-generated method stub
			return params[0].makeGetRequest("http://160.16.101.96/~cedric-k/elder_imaDoko/fetchStudentDetails.php");// change this;;;;;;;;;;;;;;''
		}
		@Override
		protected void onPostExecute(JSONArray jsonArray) {
	         //setTextToTextview(jsonArray);
			setListAdapter(jsonArray);
	     }
		
	}
	
	public void setListAdapter(JSONArray jsonArray){
		
		this.getAllStudentDetailsListView.setAdapter(new GetAllStudentDetailsListViewAdapter(jsonArray, this));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.for_the_secretary, menu);
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
