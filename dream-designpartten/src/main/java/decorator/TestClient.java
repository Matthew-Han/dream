package decorator;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2021/1/8 17:41
 **/
public class TestClient {

    public static void main(String[] args) {
        // 川普支持者
        ConcreteClient.MaGaAmerican maGaAmerican = new ConcreteClient.MaGaAmerican();
        // 白左,深蓝患者
        ConcreteClient.LiberalAmerican liberalAmerican = new ConcreteClient.LiberalAmerican();

        ConcreteDecoratorClient.MaGaAmericanDecorator maGeDecorator = new ConcreteDecoratorClient.MaGaAmericanDecorator(maGaAmerican);
        maGeDecorator.attackOfTheWhiteHouse();



        ConcreteDecoratorClient.LiberalAmericanDecorator liberalDecorator = new ConcreteDecoratorClient.LiberalAmericanDecorator(liberalAmerican);
        liberalDecorator.supportSleepJoe();
    }
}
