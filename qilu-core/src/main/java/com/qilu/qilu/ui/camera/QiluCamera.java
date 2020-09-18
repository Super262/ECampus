package com.qilu.qilu.ui.camera;

import android.net.Uri;

import com.qilu.qilu.delegates.PermissionCheckerDelegate;
import com.qilu.qilu.util.file.FileUtil;

//照相机调用类
public class QiluCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
