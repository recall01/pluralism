package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Response;

public interface IUserService {
    Response login(String userPhone, String userPassword);
}
