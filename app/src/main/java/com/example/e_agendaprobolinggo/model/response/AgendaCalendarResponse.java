package com.example.e_agendaprobolinggo.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class AgendaCalendarResponse implements Parcelable {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	protected AgendaCalendarResponse(Parcel in) {
		message = in.readString();
		status = in.readByte() != 0;
	}

	public static final Creator<AgendaCalendarResponse> CREATOR = new Creator<AgendaCalendarResponse>() {
		@Override
		public AgendaCalendarResponse createFromParcel(Parcel in) {
			return new AgendaCalendarResponse(in);
		}

		@Override
		public AgendaCalendarResponse[] newArray(int size) {
			return new AgendaCalendarResponse[size];
		}
	};

	public List<DataItem> getData(){
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
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(message);
		dest.writeByte((byte) (status ? 1 : 0));
	}
}