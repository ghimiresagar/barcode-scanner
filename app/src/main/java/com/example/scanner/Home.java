package com.example.scanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {
    // get current time with Calendar library
    static Date currentTime = Calendar.getInstance().getTime();
    // set up variables
    public static String date = currentTime.toString();
    public static int scanCount = 0;    // count the number of barcodes scanned
    public static double PRICE = 0.1;   // constant price per bottle
    public static double total = scanCount * PRICE; // formula to calculate the total price
    // string information
    public static String customerName = "";
    public static String employeeName = "";
    public static String emailAddress = "";
    // array to hold all the eligible barcodes, we have barcodes for water, around 1000
    public static String[] barcodes = {"10001022", "12036458", "22000255", "22000279", "26080925", "26085869", "26085876", "26139388", "26139395", "26159331", "26160368", "26160993", "26254104", "26308319", "26308326", "56004083", "86932318", "86939515", "93230971", "93252386", "93260466", "93260640", "93264990", "93282376", "93282383", "93288545", "93337250", "93407649", "93407656", "93473262", "93473279", "93494830", "93494847", "93518000", "93542173", "93553322", "93556958", "93556965", "93556972", "93556989", "93556996", "93557009", "93557016", "93557023", "93563062", "93624312", "93633000", "5980799200", "70510517855", "70510517855", "73580282022", "80687502097", "85589100002", "90478410616", "96619285211", "96619300907", "96619363858", "334028000010", "338954008003", "680569362735", "680569362742", "680569427519", "680569427526", "680569575074", "682430400102", "682430611737", "682430611744", "682430611751", "682430611768", "690135105521", "705105242798", "735850282008", "735850282015", "735850464169", "735850747606", "735850803401", "735850825373", "735850825380", "735850826974", "741414527850", "744109319037", "746160011271", "746160011288", "746160027746", "746160028002", "746935213206", "765531065316", "793591050751", "793618230043", "793618276140", "793618276157", "797776005994", "797776039081", "797776940073", "799600585467", "800227023583", "806809018136", "806809018143", "806809661028", "806809661035", "806809668119", "806809676237", "832423000189", "840368082016", "840368082115", "840368082207", "840368082214", "855891006026", "898667001009", "898667001061", "898667001078", "921348003501", "921348006007", "932672000097", "933242801123", "933295400250", "934918600253", "2532697156091", "2940002591619", "3179730010041", "3179730010515", "3179730013158", "3179732348913", "3830000622325", "4088700007761", "4088700075524", "4088700120545", "4088700166864", "4088700166871", "4630011231207", "4630011231214", "4860019001346", "4860019001414", "4891028700203", "4891028706816", "4891028709190", "4897050175033", "5018730000004", "5018730000196", "5200120690012", "5200120690036", "5201258100008", "5201258100336", "5201258500044", "5201258502406", "5304000315009", "5304000315016", "5304000315023", "5304000315030", "5304000315047", "5304000315054", "5304000315207", "5304000315214", "5304000315221", "5304000315238", "5310064000034", "5319990673275", "5601045105009", "5601045105306", "5601045105900", "5601163100313", "5601195100091", "5601314223205", "5601314225209", "5690351230147", "5690351230161", "5690351230185", "5690351230208", "5690351230376", "5690351232004", "5690351232042", "5690351232103", "5690351232110", "5690351232349", "5942262000631", "5942262000655", "5999885610310", "5999885610327", "5999885610334", "5999885610341", "7613034088765", "7613034965035", "7613039036099", "7613287002235", "8001477200018", "8001477200025", "8001477200032", "8001477201015", "8001477201022", "8001477201039", "8001620001905", "8002270000188", "8002270000232", "8002270002045", "8002270011023", "8002270011054", "8002270015083", "8002270015090", "8002270015373", "8002270015373", "8002270015991", "8002270017063", "8002270018220", "8002270018237", "8002270018602", "8002270049941", "8002270096563", "8002270286674", "8004192101004", "8004192101103", "8004192102100", "8004192102209", "8004192104104", "8004192104203", "8004192105101", "8004192105200", "8004192106108", "8004192106207", "8004192201001", "8004192202008", "8004192204002", "8004192206006", "8004192301008", "8004192302005", "8004192414203", "8005970321065", "8005970341063", "8005970341070", "8007885043148", "8007885043155", "8007885101015", "8007885101022", "8007885106942", "8007885106959", "8007885400408", "8007885400415", "8007885420420", "8007885420437", "8009193221058", "8009193221157", "8009193221256", "8009193221263", "8009193221751", "8009193221768", "8011087136362", "8033028740018", "8033028740025", "8033028740032", "8033028740209", "8594001020416", "8594001021642", "8594001021758", "8594001021796", "8594001023738", "8594001028832", "8600037000312", "8600169600022", "8600169600046", "8690723193503", "8690723591422", "8691058143164", "8691058143225", "8692121995024", "8801056055226", "8801128945615", "8801128945639", "8808244201045", "8850999133003", "8850999220000", "9300601259373", "9300601259380", "9300601415922", "9300624003489", "9300624005377", "9300624012580", "9300624013082", "9300624014126", "9300624014140", "9300624014164", "9300624015642", "9300624017523", "9300624017585", "9300624018650", "9300624019022", "9300624019626", "9300624030720", "9300624031604", "9300624031628", "9300624031642", "9300624033318", "9300624033356", "9300624033431", "9300624034100", "9300624364061", "9300624407614", "9300624467618", "9300633025649", "9300633325213", "9300633325220", "9300633325244", "9300633351038", "9300633394172", "9300633394189", "9300633417871", "9300633417888", "9300633417895", "9300633443955", "9300633616175", "9300633629755", "9300633680497", "9300633683320", "9300675016902", "9300675016919", "9300675018142", "9300675051859", "9300675051873", "9300675073653", "9300675082389", "9300675085977", "9300675085991", "9300675087964", "9300757000164", "9300796079206", "9300796079213", "9310077204258", "9310077231735", "9310077301711", "9310077301728", "9310113159139", "9310113160159", "9310246045835", "9310246045866", "9310246046115", "9310246046504", "9310246047891", "9310246049512", "9310246051270", "9310246051300", "9310272006336", "9310272006343", "9310272006541", "9310272006558", "9310631006700", "9310631006717", "9310631006731", "9310631006748", "9310631600250", "9310631615117", "9310631623846", "9310631624253", "9310631624260", "9310631624420", "9310631624437", "9310631625694", "9310631625700", "9310631626335", "9310631626359", "9310631629022", "9310645116334", "9310645181868", "9310645183046", "9310645247403", "9310645260921", "9310645263885", "9310645269436", "9310645269443", "9310645320328", "9310645342153", "9310711803977", "9310868003527", "9310868004043", "9310980005546", "9310980007328", "9311336000581", "9311336000901", "9311336006002", "9311336007504", "9311357000010", "9311545000006", "9311545119913", "9311545555667", "9311545887744", "9311755200289", "9311755200296", "9311755200302", "9311755300385", "9311945060402", "9311945125392", "9311945600301", "9311945750228", "9314104103507", "9314104106003", "9314104106010", "9314104110000", "9314104115005", "9314597100199", "9314597100434", "9314597100533", "9314597100700", "9314666000177", "9314666757743", "9314666757750", "9314666757767", "9314666757842", "9314666757880", "9314666757897", "9314666757941", "9314904000006", "9314904000020", "9314904000037", "9314904000105", "9314904000112", "9314904000129", "9314904000181", "9314904000525", "9314904000532", "9314904000600", "9314904001997", "9315303000055", "9315303000062", "9315303000079", "9315303000086", "9315355000195", "9315596004976", "9315596007144", "9315596007168", "9315596007557", "9315596302102", "9315596302119", "9315596302126", "9315626000008", "9315626000046", "9315626000114", "9315626000268", "9315626000275", "9315626000336", "9315626006642", "9315626008042", "9315626999715", "9315858520008", "9315858520015", "9315927061005", "9316131509611", "9316131509628", "9316254881113", "9316555001005", "9316555002101", "9316555002200", "9316555002507", "9316555003009", "9316555003016", "9316682015012", "9316682060012", "9316765000003", "9316765150005", "9316765600005", "9316784601007", "9316913011844", "9316913011851", "9318076000178", "9318076000352", "9318168000000", "9318168000017", "9318775819415", "9318775919955", "9318775919962", "9318775919993", "9320000504263", "9320123003001", "9320123003506", "9320123006002", "9320123010009", "9320123015004", "9320123030007", "9320123060004", "9321014213240", "9321014213318", "9321014213417", "9321014213486", "9321275100006", "9321275100013", "9321275100020", "9321275100037", "9321275100044", "9321275100051", "9321275100068", "9321275100075", "9321275100082", "9321275100099", "9321275100105", "9321275100112", "9321275100129", "9321275100136", "9321275100143", "9321275605037", "9321275888836", "9321275888850", "9321460000012", "9321460000036", "9321460000043", "9321460000050", "9321460000074", "9321460000081", "9321460000111", "9321460000128", "9321460000159", "9321460000180", "9321460000210", "9321460000227", "9321460000234", "9321460000258", "9321460000265", "9321460000272", "9321460000289", "9321460000340", "9321460000357", "9321460000364", "9321460000371", "9321460000388", "9321460090013", "9321460090020", "9321709000049", "9321709000056", "9321709000100", "9321832000022", "9321832000039", "9321890080028", "9321890080035", "9321890080042", "9321960000024", "9321960000031", "9321960000048", "9322033000439", "9322033000446", "9322533000151", "9322533000601", "9322533150009", "9322533600009", "9324096000036", "9324096000067", "9324380001169", "9324480000055", "9326180000601", "9326512012197", "9326720000030", "9326720000047", "9326720000368", "9326720000641", "9326720000771", "9326720000948", "9326720001013", "9326720001020", "9326720001204", "9326720001273", "9326720001426", "9326720001440", "9326720001464", "9326720001556", "9326898000450", "9326898001372", "9326898002270", "9326898002393", "9326898002782", "9327695005594", "9327695005600", "9327881000006", "9327881000013", "9327881000020", "9327881000617", "9328121055497", "9329320004378", "9329320004385", "9329320300982", "9329320306939", "9329425000022", "9329425000077", "9329425000084", "9329425000206", "9329425111179", "9329425222004", "9329425222028", "9329425222035", "9329425222042", "9329425222066", "9329425222073", "9329425222080", "9329425222097", "9329425222110", "9329425222127", "9329425222134", "9329425222158", "9329538016613", "9330171010821", "9332428002887", "9332428005406", "9332428010806", "9332428012176", "9332428012190", "9332954001606", "9332954001668", "9332954002504", "9332954005086", "9332954010509", "9333022017697", "9333022020529", "9333022024619", "9333022024916", "9333022026651", "9333022026798", "9333022027030", "9333022027542", "9333022028426", "9333195000922", "9333195010457", "9334049006008", "9334566100036", "9334566100159", "9334708002211", "9335388000023", "9335388000054", "9335388000092", "9335388000108", "9335388000269", "9335388000283", "9335388000290", "9335388000306", "9335388000313", "9335388001105", "9335388001198", "9335388001211", "9335388001273", "9335388001280", "9335388001303", "9335388001310", "9335388001433", "9335388001464", "9335388001471", "9335388001563", "9335388001716", "9335388001846", "9335388001860", "9335388001907", "9335388001914", "9335388002010", "9335388002270", "9335388002294", "9335527000037", "9335552501288", "9336221000033", "9336221000040", "9336243000509", "9336243003616", "9336243003623", "9336320000019", "9336320000040", "9336320000507", "9336320000514", "9336375000903", "9336375000927", "9336375000958", "9336375000996", "9336375002013", "9336375002037", "9336375002099", "9337314000008", "9337314000015", "9337314000022", "9337314000039", "9337314000121", "9337314000138", "9337314000145", "9337314000152", "9337666000015", "9337666000022", "9337666000039", "9337666000084", "9337666000091", "9337666000107", "9337666000169", "9337666000176", "9337666000183", "9338010000002", "9338010000101", "9338297000306", "9338297003307", "9338297003314", "9338297007756", "9338297007763", "9338972002793", "9338972002809", "9338972002816", "9338972004391", "9338972005503", "9338972005596", "9338972005671", "9339161004017", "9339496000005", "9339496000012", "9339496000029", "9339496000036", "9339496000043", "9339496000050", "9339496000081", "9339496000098", "9339496000166", "9339496000173", "9339496000203", "9339496000210", "9339678000083", "9339678000090", "9339687056453", "9339687056453", "9339687162031", "9339687209248", "9339687219421", "9339687219438", "9339687219469", "9339687219704", "9339687219711", "9339687219742", "9339948000027", "9339948000201", "9339948000218", "9339948000225", "9339948000232", "9339948000249", "9339948000348", "9340784003714", "9340784003738", "9340784005176", "9340784005183", "9340784005237", "9341325001169", "9341591001931", "9341591001948", "9341743003462", "9341743003691", "9341743003707", "9341743011016", "9341743011092", "9341743011108", "9341743011818", "9341749000748", "9341749000755", "9342113000029", "9342653000343", "9342653000350", "9342698000780", "9342698000797", "9342698006614", "9343186001496", "9343394000074", "9343394002009", "9343394002016", "9343394002023", "9343394002054", "9343394002078", "9343394002085", "9343394002108", "9343752000005", "9343752000067", "9343752000074", "9344352000082", "9344352000174", "9344960000016", "9345178001239", "9345178001253", "9345544000026", "9345544000606", "9345905002416", "9345905004427", "9345905004557", "9345905006391", "9346410000041", "9346801000001", "9346801000278", "9346801000285", "9346801009103", "9346801009110", "9346801009240", "9346801009257", "9346801009264", "9346801009271", "9346801009530", "9346801009806", "9346867000120", "9346867000151", "9346867000175", "9347331000011", "9347331000028", "9347331000035", "9347331000042", "9347331000097", "9347331000103", "9347331000110", "9347331000127", "9348007007778", "9348145099222", "9348145099239", "9348145099246", "9348834000218", "9348834000485", "9348900000012", "9348900000029", "9349186000093", "9349186000222", "9349186000260", "9349186000482", "9349186000499", "9349186000802", "9349186000819", "9349186000826", "9349186000833", "9349186000840", "9349186000864", "9349186001489", "9349186001816", "9349186001922", "9349186001939", "9349186001946", "9349186001953", "9349186001960", "9349249000107", "9349249000343", "9349249000367", "9349249002019", "9349249002071", "9349272483298", "9349277001015", "9349366000110", "9349366000127", "9349366000158", "9349366000165", "9349366000172", "9349366000189", "9349367000232", "9349509000014", "9349509000021", "9349853000012", "9349853000029", "9351593000016", "9351593000054", "9351593000061", "9351769000017", "9351769000024", "9352271000014", "9352271000021", "9352271000038", "9352271000045", "9352271000106", "9352855520563", "9353231000006", "9353231000013", "9353231000020", "9353699270010", "9353699676898", "9353699676935", "9353699676942", "9353699676973", "9353699676980", "9353699676997", "9353699677000", "9353699677017", "9353699677024", "9353949000121", "9353949000138", "9353949000145", "9353949000152", "9353972000242", "9354233291058", "9354233291065", "9354233295575", "9354233295599", "9354686000085", "9354686008036", "9354686008098", "9354686008104", "9354686526592", "9354686526615", "9354686526639", "9354686526653", "9354686526714", "9354686526738", "9355507617659", "9355507617666", "9355507617673", "9355507727129", "9355551000018", "9355551000025", "9355551000032", "9355551000049", "9355551000056", "9355551000094", "9355551000124", "9355551000131", "9355603010019", "9356067052638", "9356067052676", "9356067052683", "9356067349660", "9356067349677", "9356067563363", "9369998008803", "9369998016525", "9369998032846", "9369998036691", "9369998043859", "9369998062058", "9369998063581", "9369998093960", "9369998116584", "9369998122417", "9369998214228", "9369999010201", "9369999020897", "9369999020910", "9369999021788", "9369999021955", "9369999025069", "9369999026790", "9369999028817", "9369999030414", "9369999037239", "9369999045708", "9369999057947", "9369999298838", "9369999300678", "9369999308162", "9369999600808", "9413000008494", "9414789938309", "9417574000007", "9417574000021", "9417574000045", "9417574000519", "9417574001424", "9421006042336", "9421017790097", "9421017790103", "9421017790110", "9421017790127", "9421902594458", "9421903099372", "9421903099396", "9421903099464", "9421903099471", "9421903099488", "9421903519061", "9421904072152"};
    // set up variables for input and display
    Button scanButton;  // button on the mobile screen
    Button emailButton; // button on the mobile screen
    Button clearButton; // button on the mobile screen
    TextView dateLabel; // the place where date appears on the screen
    TextView itemsScannedLabel; // place to put scanCount on the mobile screen
    TextView totalEarnedLabel;  // place to put total price on the mobile screen
    EditText customerNameEditText;  // place where customer name can be edited
    EditText employeeNameEditText;  // place where employee name can be edited
    EditText emailEditText;         // place where we can input the email address

    @Override
    // method that runs on start or on create of the activity, this is where it begins
    protected void onCreate(Bundle savedInstanceState) {
        // default function
        super.onCreate(savedInstanceState);
        // set the layout of the Home page
        setContentView(R.layout.activity_home);

        // set up text view label
        dateLabel = findViewById(R.id.dateLabel);       // find the date lable on the screen
        dateLabel.setText(date);                        // put the date value on the found lable on the screen
        itemsScannedLabel = findViewById(R.id.itemsScannedLabel);
        itemsScannedLabel.setText(String.valueOf(scanCount));
        totalEarnedLabel = findViewById(R.id.totalEarnedLabel);
        totalEarnedLabel.setText(String.format("$ %.2f", total));
        // edit text
        emailEditText = findViewById(R.id.editTextEmailAddress);
        emailEditText.setText(emailAddress);
        customerNameEditText = findViewById(R.id.editTextCustomerName);
        customerNameEditText.setText(customerName);
        employeeNameEditText = findViewById(R.id.editTextEmployeeName);
        employeeNameEditText.setText(employeeName);
        // set up buttons to call their respective on click functions
        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this::onClickScan);
        emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(this::onClickEmail);
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this::onClickClear);
    }

    // set up scan function so it calls the actual function that scans the barcode
    public void onClickScan(View v) {
        scanCode();
    }

    public void onClickEmail(View v) {
        sendEmail();
    }

    public void onClickClear(View v) {
        clear();
    }

    // clears all the data given by the user
    private void clear() {
        customerName = "";
        employeeName = "";
        // clear all the values
        dateLabel.setText(Calendar.getInstance().getTime().toString());
        customerNameEditText.setText("");
        employeeNameEditText.setText("");
        scanCount = 0;
        total = PRICE * scanCount;
        emailEditText.setText("");

        // we can't count any more bottles, go back to home, toast a warning to close out
        Intent backToHome = new Intent(Home.this, Home.class);
        Home.this.startActivity(backToHome);
        Home.this.finish();
        // if nothing has been scanned, and user goes back
        Toast.makeText(this, "Cleared all data!", Toast.LENGTH_LONG).show();
    }

    // this method will take user to the CaptureAct view and let them scan the barcode
    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        // display how many bottles have been scanned so far
        integrator.setPrompt("Total items scanned: "+scanCount);
        integrator.initiateScan();
    }

    // this method will let the user send email to a provided email address
    private void sendEmail() {
        // if we are sending email, check if the email to send is provided
        if (scanCount == 0) {
            // if null send a error message
            Toast.makeText(Home.this, "Nothing scanned to pay!", Toast.LENGTH_SHORT).show();
        } else if (customerNameEditText.getText().toString().equals("")) {
            // if null send a error message
            Toast.makeText(Home.this, "Empty customer name provided.", Toast.LENGTH_SHORT).show();
        } else if (employeeNameEditText.getText().toString().equals("")) {
            // if null send a error message
            Toast.makeText(Home.this, "Empty employee name provided.", Toast.LENGTH_SHORT).show();
        } else if (emailEditText.getText().toString().equals("")) {
            // if null send a error message
            Toast.makeText(Home.this, "Empty email provided.", Toast.LENGTH_SHORT).show();
        } else {
            String to = emailEditText.getText().toString();
            String subject="Ashfield North News - Total Returned";
            String message="Receipt time: " + date;
            message += "\n\nCustomer Name: " + customerNameEditText.getText().toString();
            message += "\nEmployee Name: " + employeeNameEditText.getText().toString();
            message += "\nItems Scanned: " + scanCount;
            message += "\nTotal Earned: $" + total;
            message += "\n\nPlease pay the customer $"+total+" with a smile!";

            // this will open email provider
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to });
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get the values of what we scanned, mainly the barcode
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if we scanned something and have a result
        if (result != null) {
            // if the result's content is not null and we have a barcode
            if (result.getContents() != null) {
                // we have a scanned item, we need to see if this barcode we got is eligible for return
                Log.d("Received Barcode: ", result.getContents());
                // get the barcode we scanned
                String receivedBarcode = result.getContents();
                // set a flag variable to be true
                boolean flag = true;
                // go through all the barcodes
                for (int idx = 0; idx < barcodes.length; idx++) {
                    if (receivedBarcode.equals(barcodes[idx])) {
                        flag = false;
                        break;
                    }
                }

                // if flag is true, barcode is not eligible
                if (flag) {
                    // show a alert to the user saying this was already scanned
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Barcode not Eligible!");
                    builder.setMessage("Barcode is not eligible for return. Barcode: " + receivedBarcode);
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user want's to scan again
                            scanCode();
                        }
                    }).setNegativeButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user is done
                            Intent backToHome = new Intent(Home.this, Home.class);
                            Home.this.startActivity(backToHome);
                            Home.this.finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                // if flag is false, we found the correct barcode and it's eligible
                else {
                    // see how many we have counted
                    // setting these variables up will also help us retain all the information which can be lost
                    scanCount++;
                    total = scanCount * PRICE;
                    emailAddress = emailEditText.getText().toString();
                    customerName = customerNameEditText.getText().toString();
                    employeeName = employeeNameEditText.getText().toString();
                    // show a alert to see if the user want's to continue or is done
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Continue scanning?");
                    builder.setMessage("Barcode scanned successfully." + receivedBarcode);
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user want's to scan again
                            scanCode();
                        }
                    }).setNegativeButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user is done
                            Intent backToHome = new Intent(Home.this, Home.class);
                            Home.this.startActivity(backToHome);
                            Home.this.finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } else {
                // if nothing has been scanned, and user goes back
                if (scanCount == 0)
                    Toast.makeText(this, "No Results!", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
