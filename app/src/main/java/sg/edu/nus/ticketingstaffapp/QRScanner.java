package sg.edu.nus.ticketingstaffapp;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QRScanner extends AppCompatActivity {

    SurfaceView surfaceView; //camera
    TextView tvCodeInfo;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;

    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        db = new MyDB(this);
        addDummyRecord();

        surfaceView = (SurfaceView) findViewById(R.id.camera_view);
        tvCodeInfo = (TextView) findViewById(R.id.qrcode_Info);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 380).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QRScanner.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException ie) {
                    Log.e("Camera source", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int h) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { cameraSource.stop(); }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                boolean scan = true;
                if (barcodes.size() != 0 && scan == true) {
                    String qrContent = barcodes.valueAt(0).displayValue;
                    compareQRCodeDate(qrContent);
                    scan = false;
                    // redeemTicket(qrContent);
                }
            }
        });
    }

    public void addDummyRecord() {
        //to save jpg to database
        //insert to database
        db.open();
        db.insertRecord("JJ Lim, Dan, CAT 1, 67, Oct_30_2016");
        db.insertRecord("JJ Lim, Dan, CAT 1, 67, Oct_31_2016");
        db.insertRecord("Jay Chou, Emma, CAT2, 103, Oct_31_2016");
        db.insertRecord("S.H.E, Luke, CAT3, 200, Nov_1_2016");
        db.close();
    }

    /*
    public void redeemTicket(String content) {
        System.out.println("Print content = " + content);
        Intent myIntent = new Intent(this, RedeemTicket.class);
        myIntent.putExtra("content", content);
        startActivity(myIntent);
    } */

    //check date validity of scanned qr code with database
    public void compareQRCodeDate(String content) {
        //return date of qrcode scanned
        int comma = 0;
        String event="", name="", cat="", seat="", date="";
        int temp1=0, temp2=0, temp3=0, counter1=0, counter2=0, counter3=0;

        for (int i = 0; i < content.length(); i++) {
            if (comma == 4) { //JJ lim, dan, Cat1, 67, Oct_29_2016
                date = content.substring(i + 1);
                seat = content.substring(temp3, i - 1);
                break;
            } else if (comma == 1 && counter1 == 0) {
                event = content.substring(0, i - 1); // JJ Lim
                counter1++;
                temp1 = i + 1;
            } else if (comma == 2 && counter2 == 0) {
                name = content.substring(temp1, i - 1);
                counter2++;
                temp2 = i + 1;
            } else if (comma == 3 && counter3 == 0) {
                cat = content.substring(temp2, i - 1);
                counter3++;
                temp3 = i + 1;
            } else if (content.charAt(i) == ',') {
                comma++;
            }
        }

        //get all qrcode contents from database
        db.open();
        Cursor c = db.getAllRecords();
        ArrayList<String[]> records = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(1)};
                records.add(record);
            } while (c.moveToNext());
        }
        db.close();

        int comma1 = 0;
        String eventDB="", nameDB="", catDB="", seatDB="", dateDB="";
        int temp4=0, temp5=0, temp6=0, counter4=0, counter5=0, counter6=0;
        //compare record from database and scanned qr code
        String qrCodeContent = "";
        int num = 0;
        for (int i = 0; i < records.size(); i++) { //go through all the records
            String[] info = records.get(i); //in the form event, name, cat, seat, date
            qrCodeContent = info[0];

            for (int j = 0; j < qrCodeContent.length(); j++) {
                if (comma1 == 4) {
                    dateDB = qrCodeContent.substring(j + 1);
                    seatDB = qrCodeContent.substring(temp6, j - 1);
                    break;
                } else if (comma1 == 1 && counter4 == 0) {
                    eventDB = qrCodeContent.substring(0, j - 1); // JJ Lim
                    counter4++;
                    temp4 = j + 1;
                } else if (comma1 == 2 && counter5 == 0) {
                    nameDB = qrCodeContent.substring(temp4, j - 1);
                    counter5++;
                    temp5 = j + 1;
                } else if (comma1 == 3 && counter6 == 0) {
                    catDB = qrCodeContent.substring(temp5, j - 1);
                    counter6++;
                    temp6 = j + 1;
                } else if (qrCodeContent.charAt(j) == ',') {
                    comma1++;
                }
            }
            comma1=0; temp4=0; temp5 =0; temp6=0; counter4=0; counter5=0; counter6=0;

            if (event.equals(eventDB) && name.equals(nameDB) &&
                    cat.equals(catDB) && seat.equals(seatDB) && date.equals(dateDB)) {
                final String validity = "VALID. Your Category: " + cat + ", Seat No. " + seat;
                DateFormat dateFormat = new SimpleDateFormat("MMM_dd_yyyy");

                try {
                    Date dbDate = dateFormat.parse(date);
                    Date todayDate = dateFormat.parse(dateFormat.format(new Date()));

                    if (dbDate.equals(todayDate)) {
                        num = 1;
                        tvCodeInfo.post(new Runnable() {
                            @Override
                            public void run() {
                                tvCodeInfo.setText(validity);
                            }
                        });
                    } else {
                        tvCodeInfo.post(new Runnable() {
                            @Override
                            public void run() {
                                tvCodeInfo.setText("INVALID");
                            }
                        });
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if (num == 0) {
            tvCodeInfo.post(new Runnable() {
                @Override
                public void run() {
                    tvCodeInfo.setText("INVALID");
                }
            });
        }
    }
}
