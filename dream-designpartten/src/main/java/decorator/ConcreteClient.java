package decorator;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2021/1/8 17:22
 **/
public class ConcreteClient {


    public static class MaGaAmerican implements American {

        /**
         * 为了自由
         */
        @Override
        public void sayFreedom() {
            System.out.println("M G A G!!!");
        }
    }

    public static class LiberalAmerican implements American {

        /**
         * 为了自由
         */
        @Override
        public void sayFreedom() {
            System.out.println("Fuck 川普!!!");
        }
    }

}
