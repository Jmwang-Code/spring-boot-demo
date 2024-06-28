package processor.datasource.pojo;

import processor.datasource.enums.DatabaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseEntity {
    private DatabaseEnum databaseEnum;
    private String databaseName;
    private List<TableEntity> tables;

}
