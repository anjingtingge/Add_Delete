package com.hmct.add_delete;

import static android.R.attr.description;

/**
 * Created by wangyajie on 2018/9/13.
 */

public class FindBean {
    //R.drawable....
    private String url;


    public FindBean(String url){
        this.url = url;

    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}