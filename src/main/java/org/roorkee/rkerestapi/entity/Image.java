package org.roorkee.rkerestapi.entity;

import lombok.Data;

@Data
public class Image extends AbstractEntity {

    @GStoreAttr
    private String fileName;

	@Override
	public void mockObj() {
		super.mockObj();
		fileName = "file.jpeg";
	}
}
