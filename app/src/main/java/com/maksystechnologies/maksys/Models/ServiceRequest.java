package com.maksystechnologies.maksys.Models;

public class ServiceRequest {
    String id;
    String ticketId;
    String engineer_id;
    String accepted_date;
    String accepted_location;
    String completed_date;
    String status;
    String reason;
    String started_time;
    String branch_address;
    String branch_name;
    String mobile;
    String issue;
    String procced_status;
    String custAssetId;
    String ticketcode;
    String tktMIssue;
    String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTktMIssue() {
        return tktMIssue;
    }

    public void setTktMIssue(String tktMIssue) {
        this.tktMIssue = tktMIssue;
    }

    public String getTicketcode() {
        return ticketcode;
    }

    public void setTicketcode(String ticketcode) {
        this.ticketcode = ticketcode;
    }

    public String getCustAssetId() {
        return custAssetId;
    }

    public void setCustAssetId(String custAssetId) {
        this.custAssetId = custAssetId;
    }

    public String getProcced_status() {
        return procced_status;
    }

    public void setProcced_status(String procced_status) {
        this.procced_status = procced_status;
    }

    public String getUpload() {
        return upload;
    }

    String upload;

    public ServiceRequest(String id, String ticketId, String engineer_id, String accepted_date, String accepted_location, String completed_date, String status, String reason,String started_time,String branch_name,String branch_address,String mobile,String issue) {
        this.id = id;
        this.ticketId = ticketId;
        this.engineer_id = engineer_id;
        this.accepted_date = accepted_date;
        this.accepted_location = accepted_location;
        this.completed_date = completed_date;
        this.status = status;
        this.reason = reason;
        this.branch_address=branch_address;
        this.branch_name=branch_name;
        this.started_time=started_time;
        this.mobile=mobile;
        this.issue=issue;
    }

    public ServiceRequest() {
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBranch_address() {
        return branch_address;
    }

    public void setBranch_address(String branch_address) {
        this.branch_address = branch_address;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getStarted_time() {
        return started_time;
    }

    public void setStarted_time(String started_time) {
        this.started_time = started_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(String engineer_id) {
        this.engineer_id = engineer_id;
    }

    public String getAccepted_date() {
        return accepted_date;
    }

    public void setAccepted_date(String accepted_date) {
        this.accepted_date = accepted_date;
    }

    public String getAccepted_location() {
        return accepted_location;
    }

    public void setAccepted_location(String accepted_location) {
        this.accepted_location = accepted_location;
    }

    public String getCompleted_date() {
        return completed_date;
    }

    public void setCompleted_date(String completed_date) {
        this.completed_date = completed_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setUpload(String upload) {
        this.upload=upload;
    }
}
