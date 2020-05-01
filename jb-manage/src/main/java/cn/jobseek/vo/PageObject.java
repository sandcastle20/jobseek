package cn.jobseek.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageObject<T> {

    private Integer rowCount;   //总记录数
    private Integer pageCount;   //总页数

    private List<T> records ;    //记录
    private Integer pageSize;   //单位页的记录数
    private Integer pageCurrent;    //当前页

    /**
     * 封装页面数据
     * @param rowCount   总记录数
     * @param records   记录
     * @param pageSize  页面数据数
     * @param pageCurrent   当前页
     */
    public PageObject(Integer rowCount, List<T> records, Integer pageSize, Integer pageCurrent) {
        this.rowCount = rowCount;   //总记录数
        this.records = records;     //记录
        this.pageSize = pageSize;   //单位页显示的记录数
        this.pageCurrent = pageCurrent; //当前页码值

        this.pageCount = (rowCount-1)/pageSize+1;   //总页面数
    }






}
