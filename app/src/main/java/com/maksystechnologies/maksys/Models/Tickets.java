package com.maksystechnologies.maksys.Models;

public class Tickets {
    String ticketId;
    String proceed_status;
    String device;
    String givenStatus;
    String upload;
    String description;
    String id;
    String ticket_assign_status;
    String Status;
    String branchname;
    String date;
    String devicename;
    String Custname,complaints,serviceprogress,assignedon;

    public String getCustname() {
        return Custname;
    }

    public void setCustname(String custname) {
        Custname = custname;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getServiceprogress() {
        return serviceprogress;
    }

    public void setServiceprogress(String serviceprogress) {
        this.serviceprogress = serviceprogress;
    }

    public String getAssignedon() {
        return assignedon;
    }

    public void setAssignedon(String assignedon) {
        this.assignedon = assignedon;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProceed_status() {
        return proceed_status;
    }

    public void setProceed_status(String proceed_status) {
        this.proceed_status = proceed_status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTicket_assign_status() {
        return ticket_assign_status;
    }

    public void setTicket_assign_status(String ticket_assign_status) {
        this.ticket_assign_status = ticket_assign_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    String created;

    public Tickets() {
    }

    public Tickets(String ticketId, String status, String device, String givenStatus, String upload, String description,String created,String id,String ticket_assign_status,String status1) {

        this.ticketId = ticketId;
        this.proceed_status = status;
        this.device = device;
        this.givenStatus = givenStatus;
        this.upload = upload;
        this.description = description;
        this.created=created;
        this.id=id;
        this.Status=status1;
        this.ticket_assign_status=ticket_assign_status;
    }

    public String getTicketId() {

        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getproceed_status() {
        return proceed_status;
    }

    public void setproceed_status(String status) {
        this.proceed_status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getGivenStatus() {
        return givenStatus;
    }

    public void setGivenStatus(String givenStatus) {
        this.givenStatus = givenStatus;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
