package org.roorkee.rkerestapi.entity;

import lombok.Data;

@Data
public class User extends AbstractEntity {
    
    @GStoreAttr
    private String email;

    @GStoreAttr
    private String gId;

    @GStoreAttr
    private String name;
    
    @GStoreAttr
    private String imageURL;
    
    @GStoreAttr
    private String indicator;

	@Override
	public void mockObj() {
		super.mockObj();
		email = "abc@def.com";
		name = "Test Name";
		imageURL = "/abc/def/a.jpg";
		gId = "113510510750160541112";
	}
}
