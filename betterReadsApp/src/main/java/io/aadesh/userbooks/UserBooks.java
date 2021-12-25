package io.aadesh.userbooks;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "book_by_user_and_bookid")
public class UserBooks {

    @PrimaryKey
    private UserbooksPrimaryKey key;

    @Column("reading_status")
    @CassandraType(type = Name.TEXT)
    private String readingStatus;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;

    @Column("start_date")
    @CassandraType(type = Name.DATE)
    private LocalDate startDate;

    public UserbooksPrimaryKey getKey() {
        return key;
    }

    public void setKey(UserbooksPrimaryKey key) {
        this.key = key;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCompletedData() {
        return completedData;
    }

    public void setCompletedData(LocalDate completedData) {
        this.completedData = completedData;
    }

    @Column("completed_date")
    @CassandraType(type = Name.DATE)
    private LocalDate completedData;

}
