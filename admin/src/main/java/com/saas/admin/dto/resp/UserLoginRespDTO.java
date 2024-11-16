package com.saas.admin.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginRespDTO {
    /**
     * 用户Token
     */
    private String token;
}
