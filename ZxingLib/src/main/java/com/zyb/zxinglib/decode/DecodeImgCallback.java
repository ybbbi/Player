package com.zyb.zxinglib.decode;

import com.google.zxing.Result;

/**
 * @author ybbbi
 * @date 2020-04-23
 * 解析图片的回调
 */

public interface DecodeImgCallback {
    public void onImageDecodeSuccess(Result result);

    public void onImageDecodeFailed();
}
