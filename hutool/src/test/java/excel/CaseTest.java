package excel;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.xlasers.hutool.excel.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * test: 测试excel相关api
 * </p>
 *
 * @package: excel
 * @author: Elijah.D
 * @time: CreateAt 2018/11/16 && 13:33
 * @description: excel单元测试
 * @copyright: Copyright © 2018 xlasers
 * @version: V1.0
 * @modified: Elijah.D
 */
@Slf4j
public class CaseTest {
    /**
     * <p>1.sax读取excel数据 {@link CaseTest#testSax()}
     *
     * <p>2.lambada表达式变形 {@link CaseTest#testTotalCount()} (o1, o2, o3) -> count.getAndIncrement()
     *
     * @return RowHandler
     */
    private static RowHandler createRowHandler() {
        AtomicInteger count = new AtomicInteger();
        return (o1, o2, o3) -> Console.log("[{}], [{}], [{}], {}", count.getAndIncrement(), o1, o2, o3);
    }

    /**
     * 测试,读取beans写入excel
     */
    @Test
    public void testWriter() {

        ExcelWriter writer = ExcelUtil.getWriter("C:/Users/Solor/Desktop/Code/future/bravo-demos/hutool/demo" + System.currentTimeMillis() + ".xlsx");

        writer.setOrCreateSheet("Db");
        writer.write(CollUtil.newArrayList(new DbInfoDTO())).setRowHeight(-1, 20).setColumnWidth(-1, 15);

        writer.setOrCreateSheet("Table");
        writer.resetRow().write(CollUtil.newArrayList(new TableInfoDTO())).setRowHeight(-1, 20).setColumnWidth(-1, 15);

        writer.setOrCreateSheet("View");
        writer.resetRow().write(CollUtil.newArrayList(new ViewInfoDTO())).setRowHeight(-1, 20).setColumnWidth(-1, 15);

        writer.setOrCreateSheet("Column");
        writer.resetRow().write(CollUtil.newArrayList(new ColumnInfoDTO())).setRowHeight(-1, 20).setColumnWidth(-1, 15);

        writer.close();
    }

    /**
     * 测试,解析excel到bean
     */
    @Test
    public void testReader() {

        List<BaseNeoDTO> data = CollUtil.newArrayList();

        ExcelReader reader = ExcelUtil.getReader("C:/Users/Solor/Desktop/Code/future/bravo-demos/hutool/Import_Model.xlsx");
        log.info("【获取excel下sheet名字】:{}", reader.getSheetNames());

        List<DbInfoDTO> dbs = reader.setSheet("Db").read(0, 1, DbInfoDTO.class);
        List<TableInfoDTO> tables = reader.setSheet("Table").read(0, 1, TableInfoDTO.class);
        List<ViewInfoDTO> views = reader.setSheet("View").read(0, 1, ViewInfoDTO.class);
        List<ColumnInfoDTO> columns = reader.setSheet("Column").read(0, 1, ColumnInfoDTO.class);

        data.addAll(dbs);
        data.addAll(tables);
        data.addAll(views);
        data.addAll(columns);
        log.info("【解析成JavaBean】:{}", data);

        log.info("【解析成Json】:{}", JSONUtil.parseArray(data));
    }

    /**
     * 测试大批量数据读取
     */
    @Test
    public void testSax() {
        Excel07SaxReader reader2 = new Excel07SaxReader(createRowHandler());
        reader2.read("C:/Users/Solor/Desktop/Code/future/bravo-demos/hutool/Import_Model.xlsx", -1);
    }

    /**
     * 获取excel总条数
     */
    @Test
    public void testTotalCount() {
        AtomicLong count = new AtomicLong();
        Excel07SaxReader reader2 = new Excel07SaxReader((o1, o2, o3) -> count.getAndIncrement());
        reader2.read("C:/Users/Solor/Desktop/Code/future/bravo-demos/hutool/Import_Model.xlsx", -1);
        log.info("【获取excel总条数】: {}", count.get() - 4);
    }
}
