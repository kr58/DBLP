/**
 *  This class define how a dblp record is represented
 */

import java.util.ArrayList;

public class Record
{
    private String title,pages,volume,journal,booktitle,url;
    private ArrayList<String> author;
    private int year;

    public ArrayList<String> getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public int getYear() {
        return year;
    }
    public String getPages() {
        return pages;
    }
    public String getVolume() {
        return volume;
    }
    public String getUrl() {
        return url;
    }
    public String getJournal() {
        return journal;
    }
    public String getBooktitle() {
        return booktitle;
    }

    public void setAuthors(ArrayList<String> author) {
        this.author = author;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPages(String pages) {
        this.pages = pages;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public void setJournal(String journal) {
        this.journal = journal;
    }
    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setYear(int year){ this.year = year; }
}