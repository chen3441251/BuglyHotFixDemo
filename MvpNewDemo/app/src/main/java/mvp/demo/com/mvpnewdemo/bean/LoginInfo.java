package mvp.demo.com.mvpnewdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:31
 * @ Description :     MvpNewDemo
 */
public class LoginInfo implements Parcelable {
    private String name;
    private int age;
    private String address;
    private String sex;

    public LoginInfo() {

    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    protected LoginInfo(Parcel in) {
        name = in.readString();
        age = in.readInt();
        address = in.readString();
        sex = in.readString();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel in) {
            return new LoginInfo(in);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(address);
        dest.writeString(sex);
    }
}
