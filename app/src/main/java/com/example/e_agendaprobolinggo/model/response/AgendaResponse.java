package com.example.e_agendaprobolinggo.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AgendaResponse implements Parcelable {

	@SerializedName("data")
	private List<DataAgenda> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	protected AgendaResponse(Parcel in) {
		message = in.readString();
		status = in.readByte() != 0;
	}

	public static final Creator<AgendaResponse> CREATOR = new Creator<AgendaResponse>() {
		@Override
		public AgendaResponse createFromParcel(Parcel in) {
			return new AgendaResponse(in);
		}

		@Override
		public AgendaResponse[] newArray(int size) {
			return new AgendaResponse[size];
		}
	};

	public List<DataAgenda> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(message);
		parcel.writeByte((byte) (status ? 1 : 0));
	}
}