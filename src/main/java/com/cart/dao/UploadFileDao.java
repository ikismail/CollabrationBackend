package com.cart.dao;

import com.cart.model.UploadFile;

public interface UploadFileDao {

	void save(UploadFile uploadFile);

	UploadFile getFile(String userName);
}
