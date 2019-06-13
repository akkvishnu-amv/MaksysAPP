package com.maksystechnologies.maksys.Models;

public class CurrentTicket
{
    String scheduledtime;
    String ticketid;
    String address;
    String device;
    String ticketcode;
    String branchAddress;
    String ticketAssignID;
    String startTime;
    String custssetid;
    String status;
    String Schedule_id;
    String custname;
    String devicename;

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getSchedule_id() {
        return Schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        Schedule_id = schedule_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustssetid() {
        return custssetid;
    }

    public void setCustssetid(String custssetid) {
        this.custssetid = custssetid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTicketAssignID() {
        return ticketAssignID;
    }

    public void setTicketAssignID(String ticketAssignID) {
        this.ticketAssignID = ticketAssignID;
    }

    public String getTicketMobile() {
        return ticketMobile;
    }

    public void setTicketMobile(String ticketMobile) {
        this.ticketMobile = ticketMobile;
    }

    String ticketMobile;

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public CurrentTicket() {
    }

    public CurrentTicket(String scheduledtime, String ticketid, String address, String device, String ticketcode) {
        this.scheduledtime = scheduledtime;
        this.ticketid = ticketid;
        this.address = address;
        this.device = device;
        this.ticketcode = ticketcode;
    }

    public String getScheduledtime() {
        return scheduledtime;
    }

    public void setScheduledtime(String scheduledtime) {
        this.scheduledtime = scheduledtime;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTicketcode() {
        return ticketcode;
    }

    public void setTicketcode(String ticketcode) {
        this.ticketcode = ticketcode;
    }
}
