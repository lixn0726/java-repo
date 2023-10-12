package indl.lixn.lx7xl.juc;

import lombok.Data;
import lombok.Getter;

/**
 * @author listen
 **/
@Getter
public enum MyEnum {

    A("a"),

    ;

    private String lowerCase;

    MyEnum(String lowerCase) {
        this.lowerCase = lowerCase;
    }
}
