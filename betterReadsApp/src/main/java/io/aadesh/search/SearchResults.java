package io.aadesh.search;

import java.util.List;

public class SearchResults {
    private int numFound;
    private List<SearchResultBook> docs;

    public int getNumFound() {
        return numFound;
    }

    public List<SearchResultBook> getDocs() {
        return docs;
    }

    public void setDocs(List<SearchResultBook> docs) {
        this.docs = docs;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }
}
