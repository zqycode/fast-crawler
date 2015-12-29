package org.fast.crawler.demo.model;

import java.util.List;

/**
 * Created by xp017734 on 10/10/15.
 */
public class PageData<T> {

    private Integer count;

    private Long total;

    private List<T> datas;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
