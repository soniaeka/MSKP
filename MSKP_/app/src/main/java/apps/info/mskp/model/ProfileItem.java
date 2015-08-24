package apps.info.mskp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 11/08/2015.
 */
public class ProfileItem implements Parcelable{


    private String id_member;
    private String nama;
    private String alamat;
    private String kota;
    private String email;
    private String hp;

    private String rek_nama;
    private String rek_bank;
    private String rek_no;
    private String rek_cab;

    private String id_upline;
    private String posisi;
    private String id_sponsor;

    private String waris_nama;
    private String waris_hub;

    private String tanggal_daftar;
    private String lama;

    private String password;

    public ProfileItem(){

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void setId_member(String id_member) {
        this.id_member = id_member;
    }

    public String getId_member() {
        return id_member;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKota() {
        return kota;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getHp() {
        return hp;
    }

    public void setRek_nama(String rek_nama) {
        this.rek_nama = rek_nama;
    }

    public String getRek_nama() {
        return rek_nama;
    }

    public void setRek_bank(String rek_bank) {
        this.rek_bank = rek_bank;
    }

    public String getRek_bank() {
        return rek_bank;
    }

    public String getRek_no() {
        return rek_no;
    }

    public void setRek_no(String rek_no) {
        this.rek_no = rek_no;
    }


    public void setRek_cab(String rek_cab) {
        this.rek_cab = rek_cab;
    }

    public String getRek_cab() {
        return rek_cab;
    }

    public void setId_upline(String id_upline) {
        this.id_upline = id_upline;
    }

    public String getId_upline() {
        return id_upline;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setId_sponsor(String id_sponsor) {
        this.id_sponsor = id_sponsor;
    }

    public String getId_sponsor() {
        return id_sponsor;
    }

    public void setWaris_hub(String waris_hub) {
        this.waris_hub = waris_hub;
    }

    public String getWaris_hub() {
        return waris_hub;
    }

    public void setWaris_nama(String waris_nama) {
        this.waris_nama = waris_nama;
    }

    public String getWaris_nama() {
        return waris_nama;
    }

    public void setTanggal_daftar(String tanggal_daftar) {
        this.tanggal_daftar = tanggal_daftar;
    }

    public String getTanggal_daftar() {
        return tanggal_daftar;
    }

    public void setLama(String lama) {
        this.lama = lama;
    }

    public String getLama() {
        return lama;
    }


    public static final Creator<ProfileItem> CREATOR = new Creator<ProfileItem>() {
        @Override
        public ProfileItem createFromParcel(Parcel source) {

            ProfileItem mMenu = new ProfileItem();
            mMenu.id_member = source.readString();
            mMenu.nama = source.readString();
            mMenu.alamat = source.readString();
            mMenu.kota = source.readString();
            mMenu.email=source.readString();
            mMenu.hp=source.readString();
            mMenu.rek_bank=source.readString();
            mMenu.rek_nama=source.readString();
            mMenu.rek_no=source.readString();
            mMenu.rek_cab=source.readString();
            mMenu.id_upline=source.readString();
            mMenu.posisi=source.readString();
            mMenu.id_sponsor=source.readString();
            mMenu.waris_nama=source.readString();
            mMenu.waris_hub=source.readString();
            mMenu.tanggal_daftar=source.readString();
            mMenu.lama=source.readString();


            return mMenu;
        }

        @Override
        public ProfileItem[] newArray(int size) {
            return new ProfileItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_member);
        parcel.writeString(nama);
        parcel.writeString(alamat);
        parcel.writeString(kota);
        parcel.writeString(email);
        parcel.writeString(hp);
        parcel.writeString(rek_nama);
        parcel.writeString(rek_bank);
        parcel.writeString(rek_no);
        parcel.writeString(rek_cab);
        parcel.writeString(id_upline);
        parcel.writeString(posisi);
        parcel.writeString(id_sponsor);
        parcel.writeString(waris_nama);
        parcel.writeString(waris_hub);
        parcel.writeString(tanggal_daftar);
        parcel.writeString(lama);
    }

}
