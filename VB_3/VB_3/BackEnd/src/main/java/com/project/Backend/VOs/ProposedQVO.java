package com.project.Backend.VOs;

/**
 * Helper class to pass a status of a request or error and reason to frontEnd
 */
public class ProposedQVO {
    private boolean status;
    private String error;

    public ProposedQVO() {
    }

    public ProposedQVO(boolean status, String error) {
        this.status = status;
        this.error = error;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
