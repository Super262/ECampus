package com.qilu.qilu.ec.main.index.list;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.qilu.qilu.delegates.QiluDelegate;

import java.util.HashMap;

public class OrderListBean implements MultiItemEntity {
    private int item_type;
    private String pro_id;
    private String pro_type;
    private String pro_content;
    private String pro_pos;
    private String provider_phone_num;
    private String provider_name;
    private String isAccepted;
    private String acceptedPhoneNum;
    private String acceptedName;
    private String acceptedType;
    private String isAssessed;
    private String assessStar;
    private String assessContent;
    private String proPhotoNum;
    private String assessPhotoNum;
    private String proAudioNum;
    private QiluDelegate delegate;
    private HashMap<String,String> proValue=new HashMap<String,String>();

    public OrderListBean(int item_type, String pro_id, String pro_type, String pro_content, String pro_pos, String provider_phone_num, String provider_name,
                         String isAccepted,
                         String isAssessed,
                         String proPhotoNum, String proAudioNum,QiluDelegate delegate) {
        this.item_type = item_type;
        this.pro_id = pro_id;
        this.pro_type = pro_type;
        this.pro_content = pro_content;
        this.pro_pos = pro_pos;
        this.provider_phone_num = provider_phone_num;
        this.provider_name = provider_name;
        this.isAccepted = isAccepted;
        this.isAssessed = isAssessed;
        this.proPhotoNum = proPhotoNum;
        this.proAudioNum = proAudioNum;
        this.delegate=delegate;
        proValue.put("pro_id",pro_id);
        proValue.put("pro_type",pro_type);
        proValue.put("pro_content",pro_content);
        proValue.put("pro_pos",pro_pos);
        proValue.put("provider_phone_num",provider_phone_num);
        proValue.put("provider_name",provider_name);
        proValue.put("isAccepted",isAccepted);
        proValue.put("isAssessed",isAssessed);
        proValue.put("proPhotoNum",proPhotoNum);
        proValue.put("proAudioNum",proAudioNum);
    }

    public OrderListBean(int item_type, String pro_id, String pro_type, String pro_content, String pro_pos, String provider_phone_num, String provider_name,
                         String isAccepted, String acceptedPhoneNum, String acceptedName, String acceptedType,
                         String isAssessed,
                         String proPhotoNum, String proAudioNum,QiluDelegate delegate) {
        this.item_type = item_type;
        this.pro_id = pro_id;
        this.pro_type = pro_type;
        this.pro_content = pro_content;
        this.pro_pos = pro_pos;
        this.provider_phone_num = provider_phone_num;
        this.provider_name = provider_name;
        this.isAccepted = isAccepted;
        this.acceptedPhoneNum = acceptedPhoneNum;
        this.acceptedName = acceptedName;
        this.acceptedType = acceptedType;
        this.isAssessed = isAssessed;
        this.proPhotoNum = proPhotoNum;
        this.proAudioNum = proAudioNum;
        this.delegate=delegate;
        proValue.put("pro_id",pro_id);
        proValue.put("pro_type",pro_type);
        proValue.put("pro_content",pro_content);
        proValue.put("pro_pos",pro_pos);
        proValue.put("provider_phone_num",provider_phone_num);
        proValue.put("provider_name",provider_name);
        proValue.put("isAccepted",isAccepted);
        proValue.put("acceptedPhoneNum",acceptedPhoneNum);
        proValue.put("acceptedName",acceptedName);
        proValue.put("acceptedType",acceptedType);
        proValue.put("isAssessed",isAssessed);
        proValue.put("proPhotoNum",proPhotoNum);
        proValue.put("proAudioNum",proAudioNum);
    }

    public OrderListBean(int item_type, String pro_id, String pro_type, String pro_content, String pro_pos, String provider_phone_num, String provider_name,
                         String isAccepted, String acceptedPhoneNum, String acceptedName, String acceptedType,
                         String isAssessed, String assessStar, String assessContent,
                         String proPhotoNum, String assessPhotoNum, String proAudioNum,QiluDelegate delegate) {
        this.item_type = item_type;
        this.pro_id = pro_id;
        this.pro_type = pro_type;
        this.pro_content = pro_content;
        this.pro_pos = pro_pos;
        this.provider_phone_num = provider_phone_num;
        this.provider_name = provider_name;
        this.isAccepted = isAccepted;
        this.acceptedPhoneNum = acceptedPhoneNum;
        this.acceptedName = acceptedName;
        this.acceptedType = acceptedType;
        this.isAssessed = isAssessed;
        this.assessStar = assessStar;
        this.assessContent = assessContent;
        this.proPhotoNum = proPhotoNum;
        this.assessPhotoNum = assessPhotoNum;
        this.proAudioNum = proAudioNum;
        this.delegate=delegate;
        proValue.put("pro_id",pro_id);
        proValue.put("pro_type",pro_type);
        proValue.put("pro_content",pro_content);
        proValue.put("pro_pos",pro_pos);
        proValue.put("provider_phone_num",provider_phone_num);
        proValue.put("provider_name",provider_name);
        proValue.put("isAccepted",isAccepted);
        proValue.put("acceptedPhoneNum",acceptedPhoneNum);
        proValue.put("acceptedName",acceptedName);
        proValue.put("acceptedType",acceptedType);
        proValue.put("isAssessed",isAssessed);
        proValue.put("assessStar",assessStar);
        proValue.put("assessContent",assessContent);
        proValue.put("proPhotoNum",proPhotoNum);
        proValue.put("assessPhotoNum",assessPhotoNum);
        proValue.put("proAudioNum",proAudioNum);
    }

    public String getPro_id() {
        return pro_id;
    }

    public String getPro_type() {
        return pro_type;
    }

    public String getPro_content() {
        return pro_content;
    }

    public String getPro_pos() {
        return pro_pos;
    }

    public String getProvider_phone_num() {
        return provider_phone_num;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public String getAcceptedPhoneNum() {
        return acceptedPhoneNum;
    }

    public String getAcceptedName() {
        return acceptedName;
    }

    public String getAcceptedType() {
        return acceptedType;
    }

    public String getIsAssessed() {
        return isAssessed;
    }

    public String getAssessStar() {
        return assessStar;
    }

    public String getAssessContent() {
        return assessContent;
    }

    public String getProPhotoNum() {
        return proPhotoNum;
    }

    public String getAssessPhotoNum() {
        return assessPhotoNum;
    }

    public String getProAudioNum() {
        return proAudioNum;
    }

    public QiluDelegate getDelegate() {
        return delegate;
    }
    public HashMap<String,String> getProValue(){
        return proValue;
    }

    @Override
    public int getItemType() {
        return item_type ;
    }
    public static final class Builder{
        private int item_type;
        private String pro_id;
        private String pro_type;
        private String pro_content;
        private String pro_pos;
        private String provider_phone_num;
        private String provider_name;
        private String isAccepted;
        private String acceptedPhoneNum;
        private String acceptedName;
        private String acceptedType;
        private String isAssessed;
        private String assessStar;
        private String assessContent;
        private String proPhotoNum;
        private String assessPhotoNum;
        private String proAudioNum;
        private QiluDelegate delegate;

        public Builder setItem_type(int item_type) {
            this.item_type = item_type;
            return this;
        }

        public Builder setPro_id(String pro_id) {
            this.pro_id = pro_id;
            return this;
        }

        public Builder setPro_type(String pro_type) {
            this.pro_type = pro_type;
            return this;
        }

        public Builder setPro_content(String pro_content) {
            this.pro_content = pro_content;
            return this;
        }

        public Builder setPro_pos(String pro_pos) {
            this.pro_pos = pro_pos;
            return this;
        }

        public Builder setProvider_phone_num(String provider_phone_num) {
            this.provider_phone_num = provider_phone_num;
            return this;
        }

        public Builder setProvider_name(String provider_name) {
            this.provider_name = provider_name;
            return this;
        }

        public Builder setIsAccepted(String isAccepted) {
            this.isAccepted = isAccepted;
            return this;
        }

        public Builder setAcceptedPhoneNum(String acceptedPhoneNum) {
            this.acceptedPhoneNum = acceptedPhoneNum;
            return this;
        }

        public Builder setAcceptedName(String acceptedName) {
            this.acceptedName = acceptedName;
            return this;
        }

        public Builder setAcceptedType(String acceptedType) {
            this.acceptedType = acceptedType;
            return this;
        }

        public Builder setIsAssessed(String isAssessed) {
            this.isAssessed = isAssessed;
            return this;
        }

        public Builder setAssessStar(String assessStar) {
            this.assessStar = assessStar;
            return this;
        }

        public Builder setAssessContent(String assessContent) {
            this.assessContent = assessContent;
            return this;
        }

        public Builder setProPhotoNum(String proPhotoNum) {
            this.proPhotoNum = proPhotoNum;
            return this;
        }

        public Builder setAssessPhotoNum(String assessPhotoNum) {
            this.assessPhotoNum = assessPhotoNum;
            return this;
        }

        public Builder setProAudioNum(String proAudioNum) {
            this.proAudioNum = proAudioNum;
            return this;
        }

        public Builder setDelegate(QiluDelegate delegate) {
            this.delegate = delegate;
            return this;
        }
        public OrderListBean build() {
            if(isAccepted.equals("false")){
                return new OrderListBean(item_type, pro_id, pro_type, pro_content, pro_pos, provider_phone_num, provider_name,
                        isAccepted,
                        isAssessed,
                        proPhotoNum,  proAudioNum, delegate);
            }
            else{
                if(isAssessed.equals("false")){
                    return new OrderListBean(item_type, pro_id, pro_type,pro_content, pro_pos,provider_phone_num, provider_name,
                            isAccepted, acceptedPhoneNum,acceptedName,acceptedType,
                            isAssessed,
                            proPhotoNum,proAudioNum, delegate);
                }
                else{
                    return new OrderListBean(item_type,pro_id,pro_type,pro_content,pro_pos,provider_phone_num, provider_name,
                            isAccepted, acceptedPhoneNum,acceptedName,acceptedType,
                            isAssessed,assessStar, assessContent,
                            proPhotoNum,assessPhotoNum,proAudioNum,delegate);
                }
            }
        }
    }
}

