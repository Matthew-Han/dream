package decorator;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2021/1/8 17:30
 **/
public class AmericanDecorator implements American {

    public American americanDecorator;

    public AmericanDecorator(American american) {
        this.americanDecorator = american;
        sayFreedom();

    }

    @Override
    public void sayFreedom() {
        americanDecorator.sayFreedom();
    }


}
