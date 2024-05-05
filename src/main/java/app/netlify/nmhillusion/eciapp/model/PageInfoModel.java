package app.netlify.nmhillusion.eciapp.model;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class PageInfoModel extends Stringeable {
    private String totalPages;
    private String currentPageNumber;

    public String getTotalPages() {
        return totalPages;
    }

    public PageInfoModel setTotalPages(String totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public String getCurrentPageNumber() {
        return currentPageNumber;
    }

    public PageInfoModel setCurrentPageNumber(String currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
        return this;
    }
}
