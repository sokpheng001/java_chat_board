package view;

import model.dto.ResponseUserDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class UserViewTable {
    public  static void getTable(List<ResponseUserDto> responseUserDtos){
        Table table = new Table(2, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        String [] columns = {"NAME","EMAIL"};
        for(String col: columns){
            table.addCell(col, new CellStyle(CellStyle.HorizontalAlign.CENTER));
        }
        for (int i=0;i<columns.length;i++){
            table.setColumnWidth(i,10,50);
        }
        // adding value to each table column
        for(ResponseUserDto responseUserDto: responseUserDtos){
            table.addCell(responseUserDto.name(),new CellStyle(CellStyle.HorizontalAlign.CENTER),0);
            table.addCell(responseUserDto.email(),new CellStyle(CellStyle.HorizontalAlign.CENTER),0);
        }
        System.out.println(table.render());
    }
}
