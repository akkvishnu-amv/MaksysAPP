package com.maksystechnologies.maksys.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.maksystechnologies.maksys.Activities.LoginCustomer;
import com.maksystechnologies.maksys.Models.Customer;
import com.maksystechnologies.maksys.Models.Engineer;

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "maksysharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";

    private static final String KEY_Customer_ID = "keycustomerid";
    private static final String KEY_Customer_Username = "keycustomerusername";
    private static final String KEY_Customer_Name = "keycustomername";
    private static final String KEY_Customer_Email = "keycustomeremail";
    private static final String KEY_Customer_BranchID = "keycustomerbranchid";
    private static final String KEY_Customer_BranchName = "keycustomerbranchname";
    private static final String KEY_Customer_Pin_Number = "keycustomerpinnumber";
    private static final String KEY_Customer_Contact_person = "keycustomercontactperson";
    private static final String KEY_Customer_Password = "keycustomerpassword";
    private static final String KEY_Customer_Mobile = "keycustomermobile";

    private static final String KEY_Engineer_ID = "keyengineerid";
    private static final String KEY_Engineer_Password = "keyengineerpassword";
    private static final String KEY_Engineer_Username = "keyengineerusername";
    private static final String KEY_Engineer_Name = "keyengineername";
    private static final String KEY_Engineer_Pin_Number = "keyengineerpinnumber";
    private static final String KEY_Engineer_Email = "keyengineeremail";
    private static final String KEY_Engineer_Mobile = "keyengineermobile";

    private static final String KEY_Sign_Up_ID = "keysignupid";
    private static final String KEY_Login_AS_Guest_ID = "keyloginasguestid";
    private static final String KEY_Engineer_EXIT = "keyengineerexit";

    private static final String KEY_Engineer_AttendanceID="keyengineerattendanceid";
    private static final String KEY_Engineer_AttendanceToday="keyengineerattendancetoday";
    private static final String KEY_Engineer_AttendanceTodayCount="keyengineerattendancetodaycount";

    private static final String KEY_Engineer_Token="keyengineertoken";
    private static final String KEY_Customer_Token="keycustomertoken";
    private static final String KEY_Latitude="keylatitude";
    private static final String KEY_Longitude="keylongitude";


    private static final String KEY_Pending_RequestName="keypendingrequestname";
    private static final String KEY_Pending_RequestIssue="keypendingrequestissue";
    private static final String KEY_Pending_RequestAddress="keypendingrequestaddress";
    private static final String KEY_Pending_RequestContact="keypendingrequestcontact";
    private static final String KEY_Pending_RequestId="keypendingrequestid";
    private static final String KEY_Pending_RequestTicketId="keypendingrequestticketid";
    private static final String KEY_Pending_Requeststatus="keypendingrequeststatus";

    private static final String KEY_Current_Ticket_Progress="keycurrentticketprogrss";

    private static final String KEY_Current_Ticket_id="keycurrentticketid";

    private static final String KEY_Current_Ticket_ticket="keycurrentticketticket";


    private static final String KEY_Customer_Login_Count = "keycustomerlogincount";


    private static SharedPrefManager mInstance;
    private static Context mCtx;


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void customerLogin(Customer customer) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Customer_ID, customer.getCustomerId());
        editor.putString(KEY_Customer_Name, customer.getUsername());
        editor.putString(KEY_Customer_Email, customer.getEmail());
        editor.putString(KEY_Customer_Username, customer.getUsername());
        editor.putString(KEY_Customer_BranchID, customer.getBranchid());
        editor.putString(KEY_Customer_BranchName, customer.getBranchname());
        editor.putString(KEY_Customer_Contact_person, customer.getContactPerson());
        editor.putString(KEY_Customer_Pin_Number, customer.getPin());
        editor.putString(KEY_Customer_Password, customer.getPassword());
        editor.putString(KEY_Customer_Mobile, customer.getMobile());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Customer getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Customer(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null),

                sharedPreferences.getString(KEY_GENDER, null)

        );
    }

    //this method will logout the user
    public void logoutCustomer() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginCustomer.class));
    }


    public void customerPinSet(String pin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Customer_Pin_Number, pin);
        editor.apply();
    }
    public boolean isCustomerLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_ID, null) != null;
    }

    public boolean isCustomerSetPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_Pin_Number, null) != null;
    }

    public boolean isKEY_Pending_Requeststatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_Requeststatus, null) != null;
    }
    public void EngineerLogin(Engineer engineer) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_ID, engineer.getEmp_id());
        editor.putString(KEY_Engineer_Name, engineer.getName());
        editor.putString(KEY_Engineer_Email, engineer.getEmail());
        editor.putString(KEY_Engineer_Username, engineer.getUsername());
        editor.putString(KEY_Engineer_Mobile, engineer.getContactno());


        editor.apply();
    }


    public void logoutEngineer() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginCustomer.class));
    }


    public void engineerPinSet(String pin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_Pin_Number, pin);
        editor.apply();
    }
    public boolean isEngineerLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_ID, null) != null;
    }

    public boolean isEngineerSetPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Pin_Number, null) != null;
    }

    public String getEngineerPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Pin_Number, null) ;
    }
    public String getEngineerName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Name, null) ;
    }
    public String getEngineerMobile() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Mobile, null) ;
    }
    public String getCustomerPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_Pin_Number, null) ;
    }
    public String getKEY_Sign_Up_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_BranchID, null) ;
    }
    public void setKEY_Sign_Up_ID(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Customer_BranchID, id);
        editor.apply();
    }
    public String getKEY_Login_AS_Guest_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Login_AS_Guest_ID, null) ;
    }
    public void setKEY_Login_AS_Guest_ID(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Login_AS_Guest_ID, id);
        editor.apply();
    }
    public void clearLoginAsGuestKey(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_Login_AS_Guest_ID);
        editor.commit();
    }
    public boolean isLoginAsGuestSetPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Login_AS_Guest_ID, null) != null;
    }
    public boolean isSignUpSetPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_BranchID, null) != null;
    }

    public String getKEY_Customer_BranchID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_BranchID, null) ;
    }
    public String getKEY_Customer_BranchName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_BranchName, null) ;
    }

    public String getKEY_Customer_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_ID, null) ;
    }
    public String getKEY_Customer_Password() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_Password, null) ;
    }
    public String getKEY_Customer_Mobile(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_Mobile, null) ;
    }
    public String getKEY_Customer_Email(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_Email, null) ;
    }

    public void setKEY_Customer_Password(String password){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Customer_Password, password);
        editor.apply();
    }
    public void setKEY_Engineer_AttendanceID(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_AttendanceID, id);
        editor.apply();
    }

    public String getKEY_Engineer_AttendanceID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_AttendanceID, null) ;
    }
    public String getKEY_Engineer_AttendanceTodayCount(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_AttendanceTodayCount, null) ;
    }

    public void setKEY_Engineer_AttendanceToday(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_AttendanceToday, id);
        editor.apply();
    }

    public void setKEY_Engineer_AttendanceTodayCount(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_AttendanceTodayCount, id);
        editor.apply();
    }

    public boolean isEngineerAttendedToday() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_AttendanceToday, null) != null;
    }
    public boolean isEngineerAttendedTodayCount() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_AttendanceTodayCount, null) != null;
    }
    public String getKEY_Engineer_ID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_ID, null) ;
    }

    public void clearEngineerAttendedToday(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_Engineer_AttendanceToday);
        editor.commit();
    }
    public void clearEngineerAttendedTodayCount(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_Engineer_AttendanceTodayCount);
        editor.commit();
    }

    public String getKEY_Engineer_AttendanceToday() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_AttendanceToday, null) ;
    }
    public void setKEY_Engineer_Token(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_Token, id);
        editor.apply();
    }
    public void setKEY_Customer_Token(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Customer_Token, id);
        editor.apply();
    }
    public void setKEY_Engineer_ID(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_ID, id);
        editor.apply();
    }
    public void setKEY_Latitude(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Latitude, id);
        editor.apply();
    }
    public void setKEY_Longitudee(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Longitude, id);
        editor.apply();
    }
    public String getKEY_Engineer_Token() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Token, null) ;
    }
    public String getKEY_Customet_Token() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Customer_Token, null) ;
    }
    public String getKEY_Longitude() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Longitude, null) ;
    }
    public String getKEY_Latitude() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Latitude, null) ;
    }
    public boolean isEngineerTokenKey() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Token, null) != null;
    }

    public void setPendingRequest(String id,String name,String issue,String address,String contact,String ticketid){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Pending_RequestName, name);
        editor.putString(KEY_Pending_RequestAddress, address);
        editor.putString(KEY_Pending_RequestContact,contact );
        editor.putString(KEY_Pending_RequestIssue, issue);
        editor.putString(KEY_Pending_RequestTicketId, ticketid);
        editor.putString(KEY_Pending_RequestId, id);

        editor.apply();
    }
    public boolean isKEYPendingRequest() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestId, null) != null;
    }

    public String getKEY_Pending_RequestName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestName, null) ;
    }
    public String getKEY_Pending_RequestIssue() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestIssue, null) ;
    }
    public String getKEY_Pending_RequestAddress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestAddress, null) ;
    }
    public String getKEY_Pending_RequestContact() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestContact, null) ;
    }
    public String getKEY_Pending_RequestId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestId, null) ;
    }
    public String getKEY_Pending_RequestTicketId (){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_RequestTicketId, null) ;
    }
    public String getKEY_Pending_Requeststatus (){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_Requeststatus, null) ;
    }
    public void setKEY_Pending_Requeststatus(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Pending_Requeststatus, id);
        editor.apply();
    }
    public void clearPendingRequest(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_Pending_RequestName);
        editor.remove(KEY_Pending_RequestAddress);
        editor.remove(KEY_Pending_RequestContact);
        editor.remove(KEY_Pending_RequestIssue);
        editor.remove(KEY_Pending_RequestTicketId);
        editor.remove(KEY_Pending_RequestId);
        editor.remove(KEY_Pending_Requeststatus);
        editor.commit();
    }
    public boolean isKEYPendingRequestStatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Pending_Requeststatus, null) != null;
    }



    public void setKEY_Customer_Branch_ID(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Customer_BranchID, id);
        editor.apply();
    }

    public void setKEY_Engineer_Password(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Engineer_Password, id);
        editor.apply();
    }
    public void setKEY_Current_Ticket_Progress(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Current_Ticket_Progress, id);
        editor.apply();
    }
    public String getKEY_Current_Ticket_Progress(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Current_Ticket_Progress, null) ;
    }
    public void clearKEY_Current_Ticket_Progress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_Current_Ticket_Progress);
        editor.remove(KEY_Current_Ticket_ticket);
        editor.remove(KEY_Current_Ticket_id);
        editor.apply();
    }
    public  String getKEY_Current_Ticket_id() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Current_Ticket_id, null) ;

    }

    public  String getKEY_Current_Ticket_ticket() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Current_Ticket_ticket, null) ;
    }
    public  String getKEY_Engineer_Password() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Engineer_Password, null) ;
    }
    public void setKEY_Current_Ticket_ticket(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Current_Ticket_ticket, id);
        editor.apply();
    }
    public void setKEY_Current_Ticket_id(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Current_Ticket_id, id);
        editor.apply();
    }
}
