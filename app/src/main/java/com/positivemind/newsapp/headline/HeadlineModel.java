package com.positivemind.newsapp.headline;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.positivemind.newsapp.utils.Utils;

/**
 * Created by Rajeev Tomar on 21,December,2019
 */
@Entity(tableName = "headline")
public class HeadlineModel implements Parcelable {


    public HeadlineModel() {
    }

    @PrimaryKey(autoGenerate = true)
    private int key;


    @Embedded
    private Source source;
    @ColumnInfo
    private  String author;
    @ColumnInfo
    private  String title;
    @ColumnInfo
    private  String description;
    @ColumnInfo
    private String url;
    @ColumnInfo
    private  String urlToImage;
    @ColumnInfo
    private  String publishedAt;
    @ColumnInfo
    private  String content;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedDate() {
        return Utils.getFormattedDate(Utils.YMDMT_FORMAT,
                Utils.YMD_FORMAT,publishedAt);
    }

    public String getPublishedAt() {
        return publishedAt;
    }



    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public static class Source implements Parcelable{

        private String id;
        private  String name;

        public Source() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
        }

        public static final Parcelable.Creator<Source> CREATOR
                = new Parcelable.Creator<Source>() {
            public Source createFromParcel(Parcel in) {
                return new Source(in);
            }

            public Source[] newArray(int size) {
                return new Source[size];
            }
        };

        private Source(Parcel in) {
            id = in.readString();
            name = in.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(source,flags);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<HeadlineModel> CREATOR
            = new Parcelable.Creator<HeadlineModel>() {
        public HeadlineModel createFromParcel(Parcel in) {
            return new HeadlineModel(in);
        }

        public HeadlineModel[] newArray(int size) {
            return new HeadlineModel[size];
        }
    };

    private HeadlineModel(Parcel in) {
        source = in.readParcelable(getClass().getClassLoader());
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        content = in.readString();
    }


}
