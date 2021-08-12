package decorator;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2021/1/8 17:32
 **/
public class ConcreteDecoratorClient {


    public static class MaGaAmericanDecorator extends AmericanDecorator implements American {

        public MaGaAmericanDecorator(American american) {
            super(american);
        }

        /**
         * 支持川普
         */
        public void attackOfTheWhiteHouse() {
            System.out.println("占领白宫");
        }
    }

    public static class LiberalAmericanDecorator extends AmericanDecorator {

        public LiberalAmericanDecorator(American american) {
            super(american);
        }

        /**
         * 支持拜登
         */
        public void supportSleepJoe() {
            System.out.println("支持 sleep Joe");
        }
    }
}
