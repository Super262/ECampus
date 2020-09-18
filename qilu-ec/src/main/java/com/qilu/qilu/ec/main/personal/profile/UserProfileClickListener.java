package com.qilu.qilu.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.ec.main.personal.list.PersonalListBean;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.callback.CallbackManager;
import com.qilu.qilu.util.callback.CallbackType;
import com.qilu.qilu.util.callback.IGlobalCallback;
import com.qilu.qilu.util.storage.QiluPreference;
import java.io.File;

public class UserProfileClickListener extends SimpleClickListener {
    private final String[] mTypes=new String[]{"学生","其他教工","教室管理员","设备维护负责人"};
    private final UserProfileDelegate DELEGATE;
    public UserProfileClickListener(UserProfileDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final PersonalListBean bean = (PersonalListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id){
            case 1:
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        final String phone= QiluPreference.getCustomAppProfile("phone");
                        final File file=new File(args.getPath());
                        System.out.println("Phone is "+phone);
                        RestClient.builder()
                                .url("http://123.207.13.112:8080/qilu/AboutUser/uploadImg")
                                .file(file)
                                .loader(DELEGATE.getContext())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        System.out.println("更新成功");
                                        DELEGATE.initData();
                                    }
                                })
                                .build()
                                .postFile();
                    }
                });
                DELEGATE.startCameraWithCheck(400,400);
                //打开照相机或选择图片
                break;
            case 2:
                final QiluDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().startWithPop(nameDelegate);
                break;
            case 3:
                final QiluDelegate nickDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().startWithPop(nickDelegate);
                break;
            case 4:
                final QiluDelegate phoneDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().startWithPop(phoneDelegate);
                break;
            case 5:
                final QiluDelegate codeDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().startWithPop(codeDelegate);
                break;
            case 6:
                getTypeDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        final String type_original=QiluPreference.getCustomAppProfile("type");
                        final String phone=QiluPreference.getCustomAppProfile("phone");
                        if(!(which+"").equals(type_original)){
                            RestClient.builder()
                                    .url("AboutUser/updateUserInfo")
                                    .params("columnName","userType")
                                    .params("columnValue",which+"")
                                    .params("referredName","phoneNum")
                                    .params("referredValue",phone)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            Toast.makeText(DELEGATE.getContext(),"更新成功",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .build()
                                    .post();
                            textView.setText(mTypes[which]);
                        }
                        else{
                            Toast.makeText(DELEGATE.getContext(),"类型重复",Toast.LENGTH_LONG).show();
                        }
                        dialog.cancel();
                    }
                });
                break;
            default:
                break;
        }
    }
    private void getTypeDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mTypes,0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
