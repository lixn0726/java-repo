package indl.lixn.lx7xl.jvm;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author listen
 **/
public class ObjectContainer {
    
    private final ContainerField field;
    
    public ObjectContainer(ContainerField field) {
        this.field = field;
    }
    
    public ContainerField showFieldAndCopy() {
        System.out.println(field.getFieldName() + ":" + field.getFieldValue());
        ContainerField copy = new ContainerField();
        copy.setFieldName(field.getFieldName());
        copy.setFieldValue("Other Value");
        return copy;
    }
    
    @Data
    private static class ContainerField {
        private String fieldName;
        private String fieldValue;
    }
    
}
