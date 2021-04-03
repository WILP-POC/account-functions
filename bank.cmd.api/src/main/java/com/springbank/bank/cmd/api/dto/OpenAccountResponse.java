package com.springbank.bank.cmd.api.dto;

import com.springbank.bank.core.api.dto.BaseResponse;

public class OpenAccountResponse extends BaseResponse {
    private String id;
    public OpenAccountResponse(String message,String id) {
        super(message);
        this.id=id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return this.id;
    }
}
