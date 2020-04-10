package com.xiayiye.jetpackstudy.savestatejava;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

/*
 * Copyright (c) 2020, smuyyh@gmail.com All Rights Reserved.
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */

/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2020/4/10 13:41
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.savestatejava
 * 文件说明：
 */
public class SaveStateJavaViewModel extends ViewModel {
    //    private MutableLiveData<Integer> number;
    private SavedStateHandle handle;

    public SaveStateJavaViewModel(SavedStateHandle handle) {
        this.handle = handle;
    }

    /* public MutableLiveData<Integer> getNumber() {
         if (number == null) {
             number = new MutableLiveData<>();
             number.setValue(0);
         }
         return number;
     }*/
    public MutableLiveData<Integer> getNumber() {
        if (!handle.contains("number")) {
            handle.set("number", 0);
        }
        return handle.getLiveData("number");
    }

   /* public void add(int num) {
        number.setValue(number.getValue() + num);
    } */

    public void add(int num) {
        getNumber().setValue(getNumber().getValue() + num);
    }
}
