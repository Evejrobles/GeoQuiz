package edu.cnm.deepdive.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

  private static final String EXTRA_ANSWER_IS_TRUE = "edu.cnm.deepdive.geoquiz.answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = "edu.cnm.deepdive.geoquiz.answer_shown";
  private boolean mAnswerIsTrue;
  private TextView mAnswerTextView;
  private Button mshowAnswerButton;
  public static Intent newIntent (Context packageContext, boolean answerIsTrue) {
    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    return  intent;
  }
  public static boolean wasAnswereShown(Intent result) {
    return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
    mshowAnswerButton = (Button) findViewById(R.id.show_answer_button);
    mshowAnswerButton .setOnClickListener((new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mAnswerIsTrue) {
          mAnswerTextView.setText(R.string.true_button);

        } else {
          mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {

          int cx = mshowAnswerButton.getWidth() / 2;
          int cy = mshowAnswerButton.getHeight() / 2;
          float radius = mshowAnswerButton.getWidth();
          Animator anim = ViewAnimationUtils
              .createCircularReveal(mshowAnswerButton, cx, cy, radius, 0);
          anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              mshowAnswerButton.setVisibility(View.INVISIBLE);
            }
          });
          anim.start();
        }else {
          mshowAnswerButton.setVisibility(View.INVISIBLE);
        }
      }
    }));
  }
private void setAnswerShownResult(boolean isAnswerShown) {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
    setResult(RESULT_OK, data);
}
}
