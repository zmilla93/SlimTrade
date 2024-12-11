package github.zmilla93.core.ninja.responses;

public class NinjaPayment {

    public final int pay_currency_id;
    public final int get_currency_id;
    public final int count;
    public final float value;
    public final int listing_count;

    public NinjaPayment(int pay_currency_id, int get_currency_id, int count, float value, int listing_count) {
        this.pay_currency_id = pay_currency_id;
        this.get_currency_id = get_currency_id;
        this.count = count;
        this.value = value;
        this.listing_count = listing_count;
    }

}
