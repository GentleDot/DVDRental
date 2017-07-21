package net.gentledot.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Gentledot on 2017-05-05.
 */
public class Pagination {
    private int pageSize; // 게시 글 수
    private int firstPageNo; // 첫 번째 페이지 번호
    private int finalPageNo; // 마지막 페이지 번호
    private int startPageNo; // 시작 페이지 (페이징 네비 기준)
    private int endPageNo; // 끝 페이지 (페이징 네비 기준)
    private int prevPageNo; // 이전 페이지 번호
    private int nextPageNo; // 다음 페이지 번호
    private int pageNo; // 페이지 번호
    private int pageScope; // 페이징 범위 설정
    private int totalCount; // 게시 글 전체 수

    public Pagination(int pageSize, int pageNo, int pageScope, int totalCount){
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.pageScope = pageScope;
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstPageNo() {
        return firstPageNo;
    }

    public void setFirstPageNo(int firstPageNo) {
        this.firstPageNo = firstPageNo;
    }

    public int getPrevPageNo() {
        return prevPageNo;
    }

    public void setPrevPageNo(int prevPageNo) {
        this.prevPageNo = prevPageNo;
    }

    public int getStartPageNo() {
        return startPageNo;
    }

    public void setStartPageNo(int startPageNo) {
        this.startPageNo = startPageNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getEndPageNo() {
        return endPageNo;
    }

    public void setEndPageNo(int endPageNo) {
        this.endPageNo = endPageNo;
    }

    public int getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public int getFinalPageNo() {
        return finalPageNo;
    }

    public void setFinalPageNo(int finalPageNo) {
        this.finalPageNo = finalPageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageScope() {
        return pageScope;
    }

    public void setPageScope(int pageScope) {
        this.pageScope = pageScope;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 페이징 생성
     */
    public void setPaging(){
       if (this.totalCount == 0){ // 게시글 수 없는 경우
           return;
       }
       if(this.pageNo == 0 ){ // 기본 페이지 값 설정
           setPageNo(1);
       }

       if (this.pageSize == 0){ // 기본 페이지 당 목록 수 설정
           setPageSize(10);
       }
       if(this.pageScope == 0){ // 기본 범위 설정
           setPageScope(10);
       }

       // 마지막 페이지 설정
       int finalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;

       if(pageNo > finalPage){ // 기본 값 설정
          setPageNo(finalPage);
       } else if(pageNo < 0 || pageNo > finalPage){
           setPageNo(1);
       }

        int startPage = ((pageNo - 1) / pageScope) * pageScope + 1; // 시작 페이지 (페이징 네비 기준)
        int lastPage = startPage + pageScope - 1; // 끝 페이지 (페이징 네비 기준)

        if(lastPage > finalPage){ // 마지막 페이지가 끝 페이지보다 큰 경우 (제한 설정)
            lastPage = finalPage;
        }

        setStartPageNo(startPage);
        setEndPageNo(lastPage);

        boolean isNowFirst = pageNo == 1 ? true : false; // 시작페이지 (전체) 확인
        boolean isNowFinal = pageNo == finalPage ? true : false; // 마지막페이지 (전체) 확인

        setFirstPageNo(1);
        setFinalPageNo(finalPage);

        if(isNowFirst){ // 이전 페이지 번호 설정
            setPrevPageNo(1);
        } else {
            setPrevPageNo(pageNo - 1 < 1 ? 1 : pageNo - 1); // 이전 페이지 번호
        }

        if (isNowFinal){ // 다음 페이지 번호 설정
            setNextPageNo(finalPage);
        } else {
            setNextPageNo(pageNo + 1 > finalPage ? finalPage : pageNo + 1);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}