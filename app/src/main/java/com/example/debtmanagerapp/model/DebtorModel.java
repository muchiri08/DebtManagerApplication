package com.example.debtmanagerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class DebtorModel implements Parcelable {
    private int id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private int amount;
    private LocalDate dateGiven;
    private LocalDate deadlineDate;

    public DebtorModel(int id, String firstName, String lastName, int phoneNumber, int amount, LocalDate dateGiven, LocalDate deadlineDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.dateGiven = dateGiven;
        this.deadlineDate = deadlineDate;
    }

    public DebtorModel(String lastName, String firstName, int amount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
    }

    protected DebtorModel(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readInt();
        amount = in.readInt();
    }

    public static final Creator<DebtorModel> CREATOR = new Creator<DebtorModel>() {
        @Override
        public DebtorModel createFromParcel(Parcel in) {
            return new DebtorModel(in);
        }

        @Override
        public DebtorModel[] newArray(int size) {
            return new DebtorModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(LocalDate dateGiven) {
        this.dateGiven = dateGiven;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    @Override
    public String toString() {
        return "DebtorModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", amount=" + amount +
                ", dateGiven=" + dateGiven +
                ", deadlineDate=" + deadlineDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(phoneNumber);
        dest.writeInt(amount);
    }
}
