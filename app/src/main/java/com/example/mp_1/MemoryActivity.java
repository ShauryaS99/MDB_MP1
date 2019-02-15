package com.example.mp_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import static java.util.Arrays.asList;

public class MemoryActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    Random random;
    TextView timer, scoreBoard;
    Button button1, button2, button3, button4, quit;
    ImageButton pic;
    int score = 0;
    String correctAnswer;
    int answerPos;

    ArrayList<String> memberNames = new ArrayList<String>(Arrays.asList("Aayush Tyagi",
            "Abhinav Koppu", "Aditya Yadav", "Ajay Merchia", "Alice Zhao", "Amy Shen",
            "Anand Chandra", "Andres Medrano", "Angela Dong", "Anika Bagga",
            "Anmol Parande", "Austin Davis", "Ayush Kumar", "Brandon David",
            "Candice Ye", "Carol Wang", "Cody Hsieh", "Daniel Andrews", "Daniel Jing",
            "Eric Kong", "Ethan Wong", "Fang Shuo", "Izzie Lau", "Jaiveer Singh",
            "Japjot Singh", "Jeffery Zhang", "Joey Hejna", "Julie Deng", "Justin Kim",
            "Kaden Dippe", "Kanyes Thaker", "Kayli Jiang", "Kiana Go", "Leon Kwak",
            "Levi Walsh", "Louie Mcconnell", "Max Miranda", "Michelle Mao",
            "Mohit Katyal", "Mudabbir Khan", "Natasha Wong", "Nikhar Arora",
            "Noah Pepper", "Paul Shao", "Radhika Dhomse", "Sai Yandapalli",
            "Saman Virai", "Sarah Tang", "Sharie Wang", "Shiv Kushwah", "Shomil Jain",
            "Shreya Reddy", "Shubha Jagannatha", "Shubham Gupta", "Srujay Korlakunta",
            "Stephen Jayakar", "Suyash Gupta", "Tiger Chen", "Vaibhav Gattani",
            "Victor Sun", "Vidya Ravikumar", "Vineeth Yeevani", "Wilbur Shi",
            "William Lu", "Will Oakley", "Xin Yi Chen", "Young Lin"));
    ArrayList<String> options = new ArrayList<>(4);


    //timer functionality
    CountDownTimer stopwatch = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            nextMember();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        random = new Random();
        timer = (TextView) findViewById(R.id.Timer);
        scoreBoard = (TextView) findViewById(R.id.Score);
        pic = (ImageButton) findViewById(R.id.Image);

        quit = (Button) findViewById(R.id.endGame);
        button1 = (Button)findViewById(R.id.name1);
        button2 = (Button)findViewById(R.id.name2);
        button3 = (Button)findViewById(R.id.name3);
        button4 = (Button)findViewById(R.id.name4);

        scorePoint(score);



        //button functionality
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(0);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(2);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(3);
            }
        });


        //contact functionality
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatch.cancel();
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, correctAnswer);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
            }
        });
        //endgame functionality
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatch.cancel();
                AlertDialog endGameDialog = new AlertDialog.Builder(MemoryActivity.this).create();
                endGameDialog.setTitle("Exit Game?");
                endGameDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nextMember();
                                dialog.dismiss();
                            }
                        });
                endGameDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                endGameDialog.show();
            }
        });
        nextMember();

    }

    //check solution
    private void check(int x) {
        stopwatch.cancel();
        if (x == answerPos) {
            score++;
            scoreBoard.setText(String.format(Locale.getDefault(), "Score: %d", score));
        }
        else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
        nextMember();
    }

    //time limit
    public class timeLimit extends CountDownTimer {
        public timeLimit(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            int remaining_time = (int) (millisUntilFinished / 1000);
            timer.setText(remaining_time);
        }
        @Override
        public void onFinish() {
            Toast.makeText(MemoryActivity.this, "Time's Up!", Toast.LENGTH_SHORT).show();
        }
    }

    //scoreboard
    private void scorePoint(int points) {
        String score_placeholder = "Score: "+ score;
        scoreBoard.setText(score_placeholder);
    }

    private void nextMember() {
        Collections.shuffle(memberNames);

        for (int i = 0; i < 4; i++) {
            options.add(i, memberNames.get(i));
        }
        answerPos = random.nextInt(4);
        correctAnswer = options.get(answerPos);
        button1.setText(options.get(0));
        button1.setText(options.get(1));
        button1.setText(options.get(2));
        button1.setText(options.get(3));

        String img_file = correctAnswer.toLowerCase().replaceAll("\\s","");
        pic.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(),
                        getResources().getIdentifier(img_file, "drawable", getPackageName()),
                        500, 500)
        );
        stopwatch.start();
    }
    //image saving
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int width, int height) {
        final BitmapFactory.Options x = new BitmapFactory.Options();
        x.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, x);

        x.inSampleSize = calculateInSampleSize(x, width, height);

        x.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, x);

    }
    //image resizing
    public static int calculateInSampleSize(BitmapFactory.Options x, int width, int height) {
        final int w = x.outWidth;
        final int h = x.outHeight;
        int inSampleSize = 1;

        if (h > height || w > width) {
            final int new_w = width / 2;
            final int new_h = height / 2;

            while ((new_w / inSampleSize) >= width && (new_h / inSampleSize) >= height) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}

