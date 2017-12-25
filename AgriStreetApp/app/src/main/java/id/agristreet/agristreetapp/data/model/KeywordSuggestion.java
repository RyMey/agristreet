package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class KeywordSuggestion implements SearchSuggestion {

    private String keyword;
    private boolean history;

    public KeywordSuggestion(String keyword) {
        this.keyword = keyword;
    }

    public KeywordSuggestion(Parcel source) {
        this.keyword = source.readString();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    @Override
    public String getBody() {
        return keyword;
    }

    public static final Creator<KeywordSuggestion> CREATOR = new Creator<KeywordSuggestion>() {
        @Override
        public KeywordSuggestion createFromParcel(Parcel in) {
            return new KeywordSuggestion(in);
        }

        @Override
        public KeywordSuggestion[] newArray(int size) {
            return new KeywordSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyword);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof KeywordSuggestion && keyword.equals(((KeywordSuggestion) o).keyword);
    }
}