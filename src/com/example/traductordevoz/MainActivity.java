package com.example.traductordevoz;


import java.util.ArrayList;
import java.util.Locale;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

import android.speech.tts.TextToSpeech;

public class MainActivity extends Activity {
	//public class MainActivity extends Activity implements
	//TextToSpeech.OnInitListener{

	protected static final int RESULT_SPEECH = 1;

	private ImageButton btnSpeak;
	private ImageButton btnReproducir;
	private TextView txtText;
	private TextToSpeech tts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtText = (TextView) findViewById(R.id.txtText);
		btnReproducir= (ImageButton) findViewById(R.id.btnRepro);
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
		 

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					txtText.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});

		btnReproducir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String textoAtraducir= txtText.getText().toString(); 
				try {
					   Translate.setHttpReferrer("http://android-er.blogspot.com/");
					 String   textoTraducido = Translate.execute(textoAtraducir, Language.SPANISH, Language.ENGLISH);
					// tts.speak(textoAtraducir, TextToSpeech.QUEUE_FLUSH, null);
					 txtText.setText(textoTraducido);
					  } catch (Exception ex) {
					   ex.printStackTrace();
					   
					     }
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				txtText.setText(text.get(0));
			}
			break;
		}

		}
	}
//	 @Override
//	    public void onDestroy() {
//	        // Don't forget to shutdown tts!
//	        if (tts != null) {
//	            tts.stop();
//	            tts.shutdown();
//	        }
//	        super.onDestroy();
//	    }
//	 
//	    @Override
//	    public void onInit(int status) {
//	 
//	        if (status == TextToSpeech.SUCCESS) {
//	 
//	            int result = tts.setLanguage(Locale.US);
//	 
//	            if (result == TextToSpeech.LANG_MISSING_DATA
//	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//	            //	 Log.e("TTS", "This Language is not supported");     
//	            } else {
//	                btnSpeak.setEnabled(true);
//	                speakOut();
//	            }
//	 
//	        } else {
//	         //   Log.e("TTS", "Initilization Failed!");
//	        }
//	 
//	    }
//	    private void speakOut() {
//	    	 
//	        String text = txtText.getText().toString();
//	 
//	        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//	    }
	    
}