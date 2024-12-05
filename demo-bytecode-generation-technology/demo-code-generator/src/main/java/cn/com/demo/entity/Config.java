package cn.com.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Author
 * @since 2024-06-20
 */
@TableName("sys_config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId("variable")
      private String variable;

    private String value;

    private LocalDateTime setTime;

    private String setBy;
    
    public String getVariable() {
        return variable;
    }

      public void setVariable(String variable) {
          this.variable = variable;
      }
    
    public String getValue() {
        return value;
    }

      public void setValue(String value) {
          this.value = value;
      }
    
    public LocalDateTime getSetTime() {
        return setTime;
    }

      public void setSetTime(LocalDateTime setTime) {
          this.setTime = setTime;
      }
    
    public String getSetBy() {
        return setBy;
    }

      public void setSetBy(String setBy) {
          this.setBy = setBy;
      }

    @Override
    public String toString() {
        return "Config{" +
              "variable = " + variable +
                  ", value = " + value +
                  ", setTime = " + setTime +
                  ", setBy = " + setBy +
              "}";
    }
}
