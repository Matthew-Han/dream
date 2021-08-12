package decorator;

import lombok.Data;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2021/1/8 17:21
 **/
public interface American {

    /**
     * 为了自由
     */
    default void sayFreedom() {
        System.out.println("freedom!!!");
    }

}
