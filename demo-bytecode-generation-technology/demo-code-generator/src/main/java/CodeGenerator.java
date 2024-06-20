import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/sys", "root", "123456")
                .build();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .outputDir("C:\\Users\\79283\\IdeaProjects\\spring-boot-demo\\demo-bytecode-generation-technology\\demo-code-generator\\src\\main\\java")
                .author("Author") // 作者
                .build();

        // 包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("cn.com") // 父包名
                .moduleName("demo") // 模块名
                .build();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude("sys_config") // 需要生成的表
                .addTablePrefix("sys_") // 表前缀
                .entityBuilder()
                .naming(NamingStrategy.underline_to_camel) // 表名生成策略
                .build();

        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig)
                .global(globalConfig)
                .packageInfo(packageConfig)
                .strategy(strategyConfig);

        autoGenerator.execute(); // 执行生成代码
    }
}